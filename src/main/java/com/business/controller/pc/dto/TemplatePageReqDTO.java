package com.business.controller.pc.dto;

import com.business.model.base.PageDaoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "模板信息分页列表查询请求 DTO")
@Data
public class TemplatePageReqDTO extends PageDaoEntity {

    @Schema(title = "模板标题", description = "模板标题，模糊查询", type = "String")
    private String templateTitle;

}
