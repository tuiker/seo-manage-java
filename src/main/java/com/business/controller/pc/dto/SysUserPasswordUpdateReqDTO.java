package com.business.controller.pc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户密码修改请求 DTO
 * @Author yxf
 */
@Data
@Schema(title = "用户密码修改请求 DTO", description = "用户密码修改请求 DTO")
public class SysUserPasswordUpdateReqDTO {

    @Schema(title = "用户ID", description = "用户ID", name = "id")
    private Long id;

    @Schema(title = "用户密码", description = "用户密码", name = "password")
    private String password;
}
