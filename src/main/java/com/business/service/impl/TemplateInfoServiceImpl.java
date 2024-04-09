package com.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.business.common.response.ResultVO;
import com.business.common.util.EasyExcelUtil;
import com.business.common.util.SecurityUtils;
import com.business.common.vo.PageResult;
import com.business.controller.pc.vo.ReplaceExportData;
import com.business.controller.pc.dto.ReplaceAndGenerateReqDTO;
import com.business.controller.pc.dto.TemplateAddReqDTO;
import com.business.controller.pc.dto.TemplatePageReqDTO;
import com.business.controller.pc.dto.TemplateUpdateReqDTO;
import com.business.controller.pc.vo.ReplaceResultVO;
import com.business.controller.pc.vo.TemplateInfoVO;
import com.business.model.dao.TemplateInfoMapper;
import com.business.model.pojo.TemplateInfo;
import com.business.service.ITemplateInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 模板信息 Service接口实现
 * @Author yxf
 */
@Service
public class TemplateInfoServiceImpl extends ServiceImpl<TemplateInfoMapper, TemplateInfo> implements ITemplateInfoService {

    @Value("${spring.profiles.active:}")
    private String active;
    @Value("${lanBo.file.path:}")
    private String filePath;
    @Value("${lanBo.mobile.path:}")
    private String mobilePath;

    /**
     * 分页查询模板信息列表
     * @param reqDTO
     * @return
     */
    @Override
    public PageResult<TemplateInfoVO> pageList(TemplatePageReqDTO reqDTO) {
        if(StrUtil.isNotBlank(reqDTO.getTemplateTitle())){//模糊搜索模板标题
            reqDTO.setTemplateTitle("%" + reqDTO.getTemplateTitle() + "%");
        }
        Page<TemplateInfoVO> page = baseMapper.pageList(new Page<>(reqDTO.getPage(), reqDTO.getPageSize()), reqDTO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    /**
     * 新增模板信息
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> add(TemplateAddReqDTO reqDTO) {
        TemplateInfo templateInfo = BeanUtil.copyProperties(reqDTO, TemplateInfo.class);
        templateInfo.setCreateId(SecurityUtils.getLoginUserId());
        templateInfo.setCreateTime(LocalDateTime.now());
        this.save(templateInfo);
        return ResultVO.success(true);
    }

    /**
     * 修改模板信息
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<Boolean> update(TemplateUpdateReqDTO reqDTO) {
        TemplateInfo templateInfo = BeanUtil.copyProperties(reqDTO, TemplateInfo.class);
        templateInfo.setUpdateId(SecurityUtils.getLoginUserId());
        templateInfo.setUpdateTime(LocalDateTime.now());
        this.updateById(templateInfo);
        return ResultVO.success(true);
    }


    /**
     * 替换并生成Excel
     * @param reqDTO
     * @return
     */
    @Override
    public ResultVO<ReplaceResultVO> replaceAndGenerate(ReplaceAndGenerateReqDTO reqDTO) {
        String keywordExcelPath = reqDTO.getKeywordExcelPath();
        if(!active.equals("dev")){//将网址替换为磁盘路径
            keywordExcelPath = keywordExcelPath.replaceAll(mobilePath, filePath);
        }
        if(!FileUtil.exist(keywordExcelPath)){
            return ResultVO.error("文件不存在");
        }

        TemplateInfo templateInfo = this.getById(reqDTO.getTemplateId());
        if(null == templateInfo){
            return ResultVO.error("模板信息不存在");
        }else if(null != templateInfo.getTotal() && templateInfo.getTotal() > 0){
            return ResultVO.error("该模板已进行过替换操作");
        }

        List<String> keywordList = EasyExcelUtil.readColumnValues(keywordExcelPath, 0, 0, String.class);
        if (CollectionUtil.isEmpty(keywordList)){
            return ResultVO.error("未读取到文件中的关键词");
        }

        //组装替换关键词后的需要导出的数据
        List<ReplaceExportData> exportDataList = getReplaceExportData(keywordList, templateInfo);

        //根据日期创建不同目录
        String datePath = new SimpleDateFormat("yyyy"+File.separator+"MM"+File.separator+"dd"+File.separator).format(new Date());
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
        EasyExcel.write(pathName, ReplaceExportData.class)
                .sheet("测试")
                .doWrite(exportDataList);

        //将磁盘路径替换为网址
        pathName = pathName.replace(filePath, mobilePath);

        //更新模板信息
        templateInfo.setTotal(exportDataList.size());
        templateInfo.setFilePath(pathName);
        templateInfo.setUpdateId(SecurityUtils.getLoginUserId());
        templateInfo.setUpdateTime(LocalDateTime.now());
        this.updateById(templateInfo);

        //组装响应数据
        ReplaceResultVO replaceResultVO = new ReplaceResultVO();
        replaceResultVO.setTotal(exportDataList.size());
        replaceResultVO.setFileDownloadPath(pathName);

        return ResultVO.success(replaceResultVO);
    }

    /**
     * 组装替换关键词后的需要导出的数据
     * @param keywordList 关键词集合
     * @param templateInfo 模板信息
     * @return
     */
    private List<ReplaceExportData> getReplaceExportData(List<String> keywordList, TemplateInfo templateInfo) {
        List<ReplaceExportData> exportDataList = new ArrayList<>();
        for (String keyword : keywordList){
            String[] location = generateRandomLocation(37.55593878327446, 127.00525383750491);
            ReplaceExportData exportData = new ReplaceExportData(
                    location[0],
                    location[1],
                    templateInfo.getTemplateTitle().replaceAll(templateInfo.getTemplateKeyword(), keyword),
                    templateInfo.getTemplateDesc().replaceAll(templateInfo.getTemplateKeyword(), keyword)
            );
            exportDataList.add(exportData);
        }
        return exportDataList;
    }

    /**
     * 根据经纬度随机生成附近20公里内的坐标
     * @param latitude
     * @param longitude
     * @return
     */
    private String[] generateRandomLocation(double latitude, double longitude) {
        // 地球半径，单位米
        double EARTH_RADIUS = 6371000;
        Random random = new Random();

        // 随机偏移量
        double offset = random.nextDouble() * 2 * Math.PI;
        double distance = random.nextDouble() * 20000; // 产生0到20千米之间的随机数

        // 经度偏移
        double offsetLon = Math.cos(offset) * distance / EARTH_RADIUS;
        double newLongitude = longitude + offsetLon;

        // 纬度偏移
        double offsetLat = Math.sin(offset) * distance / EARTH_RADIUS;
        double newLatitude = latitude + offsetLat;

        return new String[]{String.valueOf(newLongitude), String.valueOf(newLatitude)};
    }

}
