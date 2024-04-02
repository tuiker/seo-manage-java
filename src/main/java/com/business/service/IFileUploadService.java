package com.business.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: GaoLu
 * @Date: 2023-10-24 14:07
 * @Description: 图片上传服务
 */
@Service
public interface IFileUploadService {
    /**
     * @Description 文件上传
     * @Author GaoLu
     * @Date 2023/10/24
     * @Return
     * @Param files 文件
     * @Param describe 类型
     * @Param type 描述
     **/
    List<String> upload(MultipartFile[] files, String gameName);
}
