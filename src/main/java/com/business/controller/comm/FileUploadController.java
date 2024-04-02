package com.business.controller.comm;

import cn.hutool.core.collection.CollectionUtil;
import com.business.common.enums.ResultCode;
import com.business.common.response.ResultVO;
import com.business.service.IFileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: GaoLu
 * @Date: 2023-10-24 14:03
 * @Description: 图片上传controller
 */
@Controller
@RequestMapping("/comm/fileUpload")
@Tag(name = "文件上传")
@Slf4j
public class FileUploadController {

    @Resource
    private IFileUploadService fileUploadService;

    @ResponseBody
    @Operation(summary = "文件上传")
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO<Object> uploadFile(@RequestPart("files") MultipartFile[] files, String gameName) //1返回图片相对路径 2返回图片绝对路径
    {
        if (files == null || (files.length == 1 && files[0].getSize() == 0)) {
            log.info("文件上传失败**************");
            return ResultVO.error(ResultCode.FAILED.getCode(), "文件为空");
        }
        log.info("文件上传开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<String> map = fileUploadService.upload(files, gameName);
        log.info("文件上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //System.out.println(map);
        return CollectionUtil.isNotEmpty(map) ? ResultVO.success(map) : ResultVO.error(ResultCode.FAILED.getCode(), "上传失败");

    }
}
