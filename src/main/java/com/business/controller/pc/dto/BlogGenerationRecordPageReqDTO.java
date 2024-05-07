package com.business.controller.pc.dto;

import com.business.model.base.PageDaoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "博客生成记录分页列表查询请求 DTO")
@Data
public class BlogGenerationRecordPageReqDTO extends PageDaoEntity {

}
