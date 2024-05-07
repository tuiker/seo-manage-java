package com.business.controller.pc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.business.common.response.ResultVO;
import com.business.common.util.EasyExcelUtil;
import com.business.common.util.SecurityUtils;
import com.business.common.vo.PageResult;
import com.business.controller.pc.dto.*;
import com.business.controller.pc.vo.BlogData;
import com.business.controller.pc.vo.BlogGenerationRecordVO;
import com.business.model.pojo.BlogGenerationRecord;
import com.business.model.redis.AiAccessTokenRedisDAO;
import com.business.model.redis.ProgressRedisDAO;
import com.business.service.IBlogGenerationRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;


@Slf4j
@RestController
@RequestMapping("/pc/blog")
@Tag(name = "admin端 - 博客生成记录控制层")
public class BlogGenerationRecordController {

    @Value("${spring.profiles.active:}")
    private String active;
    @Value("${lanBo.file.path:}")
    private String filePath;
    @Value("${lanBo.mobile.path:}")
    private String mobilePath;

    @Value("${ai.grantType}")
    private String grantType;
    @Value("${ai.clientId}")
    private String clientId;
    @Value("${ai.clientSecret}")
    private String clientSecret;
    @Value("${ai.getAccessToken}")
    private String getAccessTokenUrl;
    @Value("${ai.chat-ERNIE-Speed-8K}")
    private String chatERNIESpeed8KUrl;

    @Resource
    private AiAccessTokenRedisDAO aiAccessTokenRedisDAO;
    @Resource
    private ProgressRedisDAO progressRedisDAO;

    @Resource
    private IBlogGenerationRecordService blogGenerationRecordService;

    @Operation(summary = "根据上传的关键词Excel生成对应的博客内容")
    @PostMapping("/generateBlog")
    public ResultVO<Boolean> generateBlog(@RequestBody GenerateBlogReqDTO reqDTO){
        String keywordExcelPath = reqDTO.getKeywordExcelPath();
        if(!active.equals("dev")){//将网址替换为磁盘路径
            keywordExcelPath = keywordExcelPath.replace(mobilePath, filePath);
        }
        if(!FileUtil.exist(keywordExcelPath)){
            return ResultVO.error("文件不存在");
        }

        List<String> keywordList = EasyExcelUtil.readColumnValues(keywordExcelPath, 0, 0, String.class);
        if (CollectionUtil.isEmpty(keywordList)){
            return ResultVO.error("未读取到文件中的关键词");
        }

        //获取AccessToken
        String accessToken = aiAccessTokenRedisDAO.get();
        if(StrUtil.isBlank(accessToken)){
            //AccessToken已过期，重新获取
            String result = HttpUtil.post(String.format(getAccessTokenUrl, grantType, clientId, clientSecret), "");
            JSONObject resultObj = JSON.parseObject(result);
            if(StrUtil.isNotBlank(resultObj.getString("error"))){
                return ResultVO.error("获取AccessToken失败：" + resultObj.getString("error_description"));
            }
            accessToken = resultObj.getString("access_token");
            aiAccessTokenRedisDAO.set(accessToken);
        }

        String finalAccessToken = accessToken;
        Long loginUserId = SecurityUtils.getLoginUserId();
        //创建记录并保存
        BlogGenerationRecord record = new BlogGenerationRecord();
        record.setCreateId(loginUserId);
        record.setCreateTime(LocalDateTime.now());
        record.setGenerateDesc(reqDTO.getGenerateDesc());
        blogGenerationRecordService.save(record);

        //初始化博客生成进度条
        progressRedisDAO.setDenominator(keywordList.size());
        progressRedisDAO.setNumerator();

        new Thread(() -> {
            log.info(String.format("共计[%s]个关键词，根据关键词开始生成博客...", keywordList.size()));
            long start = System.currentTimeMillis();
            //核心线程数
            int corePoolSize = 6;
            // 创建多个有返回值的任务
            List<Future<List<BlogData>>> list = new ArrayList<>();
            //根据核心线程数分割集合为二元集合
            List<List<String>> splitAfterList = splitList(keywordList, corePoolSize);
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize, 6, 6, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
            for (int i = 0; i < splitAfterList.size(); i++){
                List<String> stringList = splitAfterList.get(i);
                System.out.println("关键词集合大小-----------------" + stringList.size());
                //创建线程并提交
                GenerationBlogCallable generationBlogCallable = new GenerationBlogCallable(stringList, reqDTO.getGenerateDesc(), finalAccessToken);
                Future<List<BlogData>> future = threadPool.submit(generationBlogCallable);
                list.add(future);
            }
            //关闭线程池
            threadPool.shutdown();

            try {
                List<BlogData> allBlogData = new ArrayList<>();
                // 获取所有并发任务的运行结果
                for (Future<List<BlogData>> f : list) {
                    // 从Future对象上获取任务的返回值，并输出到控制台
                    List<BlogData> blogList = f.get();
                    allBlogData.addAll(blogList);
                }
                log.info(String.format("博客生成完毕，总计[%s]篇", allBlogData.size()));
                log.info("-------共耗时：" + (System.currentTimeMillis() - start) + "ms");

                //根据日期创建不同目录
                String datePath = new SimpleDateFormat("yyyy"+ File.separator+"MM"+File.separator+"dd"+File.separator).format(new Date());
                //组装文件存储目录
                String folderPath = filePath + "file" + File.separator + datePath;
                //判断目录是否存在，不存在则创建
                if(!FileUtil.exist(folderPath)){
                    FileUtil.mkdir(folderPath);
                }
                //生成文件名
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String newFileName = sdf.format(new Date()) + ".xlsx";
                //写入数据
                String pathName = folderPath + newFileName;
                EasyExcel.write(pathName, BlogData.class)
                        .sheet("dataInfo")
                        .doWrite(allBlogData);

                //将磁盘路径替换为网址
                pathName = pathName.replace(filePath, mobilePath);

                //更新博客生成记录的博客Excel下载地址
                record.setDownloadUrl(pathName);
                blogGenerationRecordService.updateById(record);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            //移除redis中博客生成进度缓存
            progressRedisDAO.removeProgress();
        }).start();

        return ResultVO.success(true);
    }

    /**
     * 根据核心线程数分割集合为二元集合
     * @param list 要分割的集合
     * @param corePoolSize 核心线程数
     */
    public static <T> List<List<T>> splitList(List<T> list, int corePoolSize){
        List<List<T>> subLists = new ArrayList<>();
        int chunkSize = Math.max(1, list.size() / corePoolSize); // 计算每个子列表应该有的元素数量，但至少要有一个元素
        for (int i = 0; i < list.size(); i += chunkSize) {
            int end = Math.min(i + chunkSize, list.size()); // 计算子列表的结束索引
            subLists.add(list.subList(i, end)); // 添加子列表
        }
        return subLists;
    }

    /**
     * Callable线程类，用来发起根据关键词生成博客请求
     */
    class GenerationBlogCallable implements Callable<List<BlogData>> {
        /** 关键词列表 */
        private final List<String> keywordList;
        /** 生成博客要求描述 */
        private final String generateDesc;
        /** accessToken */
        private final String accessToken;

        GenerationBlogCallable(List<String> keywordList, String generateDesc, String accessToken) {
            this.keywordList = keywordList;
            this.generateDesc = generateDesc;
            this.accessToken = accessToken;
        }

        /*返回值可以是自定义的 Object*/
        public List<BlogData> call() {
            JSONObject body = new JSONObject();
            JSONArray chatList = new JSONArray();
            List<BlogData> resultList = new ArrayList<>();
            for (String keyword : keywordList) {
                //组装请求体
                JSONObject item = new JSONObject();
                item.put("role", "user");
                item.put("content", generateDesc + keyword);
                chatList.add(item);
                body.put("messages", chatList);
                //发送请求
                String result = HttpUtil.post(chatERNIESpeed8KUrl + "?access_token=" + accessToken, body.toJSONString());
                JSONObject resultObj = JSON.parseObject(result);
                if(resultObj.containsKey("error_code")){
                    log.error(String.format("关键词[%s]生成博客失败，错误码：%s，错误信息：%s",
                            keyword, resultObj.getString("error_code"), resultObj.getString("error_msg")));
                }else {
                    resultList.add(new BlogData(keyword, resultObj.getString("result")));
                }

                //博客生成进度条分子自增
                progressRedisDAO.incrNumerator();
                //清空list
                chatList.clear();
            }
            return resultList;
        }
    }

    @Operation(summary = "分页查询博客生成记录列表")
    @GetMapping("/pageList")
    public ResultVO<PageResult<BlogGenerationRecordVO>> pageList(BlogGenerationRecordPageReqDTO reqDTO){
        return ResultVO.success(blogGenerationRecordService.pageList(reqDTO));
    }

    @Operation(summary = "获取博客生成进度")
    @GetMapping("/getProgress")
    public ResultVO<Integer> getProgress(){
        String denominatorStr = progressRedisDAO.getDenominator();
        String numeratorStr = progressRedisDAO.getNumerator();
        if(StrUtil.isBlank(denominatorStr) || StrUtil.isBlank(numeratorStr)){
            return ResultVO.success(100);
        }
        //博客生成进度分母（总共需要生成多少篇博客）
        double denominator = Double.parseDouble(denominatorStr);
        //博客生成进度分子（当前已生成多少篇博客）
        double numerator = Double.parseDouble(numeratorStr);

        //百分比 = 分子 / 分母 * 100
        double progress = (int) numerator / denominator * 100;
        return ResultVO.success((int) progress);
    }

}
