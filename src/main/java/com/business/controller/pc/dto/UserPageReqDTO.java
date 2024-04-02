package com.business.controller.pc.dto;

import com.business.model.base.PageDaoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户分页列表查询请求 DTO")
@Data
public class UserPageReqDTO extends PageDaoEntity {

    @Schema(title = "用户ID", description = "用户ID", type = "Long")
    private Long id;

    @Schema(title = "用户账号", description = "用户账号，模糊查询", type = "String")
    private String userName;

}
