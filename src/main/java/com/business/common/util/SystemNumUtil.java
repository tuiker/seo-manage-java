package com.business.common.util;

/**
 * @Author: GaoLu
 * @Date: 2023-11-06 14:48
 * @Description: 随机生成指定位数ID
 */
public class SystemNumUtil {
    public static String getRandomNumberByNum(int num) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < num; i++) {

            long randomNum = Math.round(Math.floor(Math.random() * 10.0D));

            sb.append(randomNum);

        }

        return sb.toString();

    }
}
