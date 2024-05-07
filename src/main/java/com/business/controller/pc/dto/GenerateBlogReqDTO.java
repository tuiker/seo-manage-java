package com.business.controller.pc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "根据上传的关键词Excel生成对应的博客内容请求 DTO", description = "根据上传的关键词Excel生成对应的博客内容请求 DTO")
public class GenerateBlogReqDTO {

    @Schema(title = "生成描述", description = "生成描述", name = "generateDesc")
    private String generateDesc;

    @Schema(title = "关键词表格地址", description = "关键词表格地址", name = "keywordExcelPath")
    private String keywordExcelPath;

}
