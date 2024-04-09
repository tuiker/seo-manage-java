package com.business.controller.pc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "替换并生成Excel请求 DTO", description = "替换并生成Excel请求 DTO")
public class ReplaceAndGenerateReqDTO {

    @Schema(title = "模板ID", description = "模板ID", name = "templateId")
    private Long templateId;

    @Schema(title = "关键词表格地址", description = "关键词表格地址", name = "keywordExcelPath")
    private String keywordExcelPath;

}
