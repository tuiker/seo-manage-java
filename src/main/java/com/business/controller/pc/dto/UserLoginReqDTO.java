package com.business.controller.pc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 用户登录 请求 DTO
 * @Author yxf
 */
@Data
@Schema(title ="用户登录 请求 DTO")
public class UserLoginReqDTO {

    @Schema(title = "用户名")
    private String username;
    @Schema(title = "密码")
    private String password;


}
