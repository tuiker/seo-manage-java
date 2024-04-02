package com.business.common.enums;

import lombok.Getter;

/**
 * @Author: Sam
 * @Date: 2023-10-25
 * @Description: app首页枚举类
 */
@Getter
public enum HomeEnums {

    RECENT(1, "近期"),

    RECOMMEND(2, "广告推荐"),

    PERSONAL(3, "个性化");

    private int code;
    private String msg;

    HomeEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValue(int code) {
        for (HomeEnums ele : values()) {
            if (ele.getCode() == code) return ele.getMsg();
        }
        return null;
    }
}
