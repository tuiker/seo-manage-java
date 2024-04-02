package com.business.common.enums;

import lombok.Getter;

/**
 * @Author: GaoLu
 * @Date: 2023-10-18 15:03
 * @Description: 响应枚举类
 */
@Getter
public enum ResultCode {
    SUCCESS(1000, "操作成功"),
    SUCCESS_SEND(1010, "发送成功"),
    FAILED(1001, "响应失败"),
    VALIDATE_FAILED(1002, "参数校验失败"),
    ERROR(5000, "未知错误"),
    UNAUTHORIZED(401, "未认证（签名错误）"),
    FORBIDDEN(1004, "禁止访问"),
    NOT_FOUND(1005, "验证码错误"),
    AUTH_ERROR(1006, "鉴权登陆失败，请重新登录！"),
    EXIST_USER(1007, "用户已存在！"),
    ERROR_USER_OR_PASSWORD(1008, "用户名或密码错误！");


    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
