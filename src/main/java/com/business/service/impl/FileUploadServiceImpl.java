package com.business.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.business.service.IFileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: GaoLu
 * @Date: 2023-10-24 14:09
 * @Description: 上传服务实现类
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements IFileUploadService {

    @Value("${spring.profiles.active:}")
    private String active;

    @Value("${lanBo.mobile.path:}")
    private String mobilePath;

    @Value("${lanBo.file.path:}")
    private String filePath;

    @Override
    public List<String> upload(MultipartFile[] files, String gameName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String saveDBPath = "";
            if (file != null && file.getSize() > 0) {
                String fileName = file.getOriginalFilename();
                log.info("上传文件名：*********>>>>>>>>>>>" + fileName);
                //根据文件类型储存在不同的文件夹下
                String extension = getSuffix(fileName);//文件格式
                if ((".pjpeg").equals(extension)) {//图片扩展名=转换
                    extension = ".jpeg";
                }
                if (StringUtils.isBlank(extension)) {
                    urlList.add("上传失败,未获取到文件名!");
                    return urlList;
                }
                //文件存储目录
                String datePath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());//根据日期创建不同目录
                String folderPath = "";
                if (isImage(extension)) {
                    folderPath = "image/" + datePath;
                } else {
                    folderPath = "file/" + datePath;
                }
                String path = filePath + folderPath;
                //文件名 普通地址 命名方式:时间-数字-描述
                String newFileName = sdf.format(new Date()) + "-" + (i + 1) + extension;
                if (StringUtils.isNotBlank(gameName)) {
                    newFileName = gameName+ extension;
                }
                File targetFile = new File(path, newFileName);
                if (targetFile.exists()) {//已存在，进行删除，解决问题：同一个名称的APK包重复上传报错：Cannot write uploaded file to disk!
                    targetFile.delete();
                }else {//不存在
                    targetFile.mkdirs();
                }
                try {
                    file.transferTo(targetFile.getAbsoluteFile()); //getPath 是个坑，一定要用绝对路径 getAbsoluteFile
                    log.info("文件实际保存的路径：>>>>>>>>>>>{}" + targetFile.getAbsoluteFile());
                    saveDBPath = targetFile.getAbsoluteFile().getAbsolutePath();
                    //储存数据库文件名
                    if (active != null && !active.equals("dev")) {//非开发则替换路径
                        saveDBPath = saveDBPath.replace("\\", "/")
                                .replaceAll(filePath, mobilePath);
                    }
                    urlList.add(saveDBPath);
                } catch (Exception e) {
                    log.info("文件上传出错：>>>>>>>>>>>{}" + e);
                    e.printStackTrace();
                }
            }
        }
        return urlList;
    }


    /**
     * 获取文件后缀
     *
     * @param filename
     * @return
     * @Author Smily
     * @DateTime 2020年7月15日
     */
    private String getSuffix(String filename) {
        if (StringUtils.isNotBlank(filename)) {
            String suffix = "";
            int pos = filename.lastIndexOf('.');
            if (pos > 0 && pos < filename.length() - 1) {
                suffix = filename.substring(pos);
            }
            return suffix.toLowerCase();
        }
        return "";
    }

    /**
     * 判断文件类型
     *
     * @param suffix
     * @return
     * @Author Smily
     * @DateTime 2020年7月15日
     */
    public static boolean isImage(String suffix) {
        suffix = suffix.toUpperCase();
        if (".PNG".equals(suffix)
                || ".JPG".equals(suffix)
                || ".JEPG".equals(suffix)
                || ".JPEG".equals(suffix)
                || ".BMP".equals(suffix)
                || ".HEIC".equals(suffix)) {
            return true;
        }
        return false;
    }
}
