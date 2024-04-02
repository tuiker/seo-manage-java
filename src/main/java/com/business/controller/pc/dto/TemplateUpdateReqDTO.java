package com.business.controller.pc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "模板信息修改请求 DTO", description = "模板信息修改请求 DTO")
public class TemplateUpdateReqDTO {

    @Schema(title = "模板ID", description = "模板ID", name = "id")
    private Long id;

    @Schema(title = "模板标题", description = "模板标题", name = "templateTitle")
    private String templateTitle;

    @Schema(title = "模板描述", description = "模板描述", name = "templateDesc")
    private String templateDesc;

    @Schema(title = "模板关键词", description = "模板关键词", name = "templateKeyword")
    private String templateKeyword;
}
