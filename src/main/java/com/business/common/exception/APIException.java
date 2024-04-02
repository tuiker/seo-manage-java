package com.business.common.exception;

import lombok.Getter;

/**
 * @Author:GaoLu
 * @Date:2023-10-18 15:21
 * @Description:
 */
@Getter
public class APIException extends RuntimeException {
    private int code;
    private String msg;

    public APIException() {
        this(1001, "接口错误");
    }

    public APIException(String msg) {
        this(1001, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
