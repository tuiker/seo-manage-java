package com.business.controller.pc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户新增请求 DTO
 * @Author yxf
 */
@Data
@Schema(title = "用户新增请求 DTO", description = "用户新增请求 DTO")
public class SysUserAddReqDTO {

    @Schema(title = "用户账号", description = "用户账号", name = "userName")
    private String userName;

    @Schema(title = "密码", description = "密码", name = "password")
    private String password;

    @Schema(title = "头像", description = "用户头像", name = "userImg")
    private String userImg;

    @Schema(title = "角色ID", description = "角色ID", name = "roleId")
    private Integer roleId;

}
