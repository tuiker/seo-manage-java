package com.business.common.util;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.Writer;

/**
 * 客户端工具类
 * @Author yxf
 */
public class ServletUtils {

    /**
     * 返回 JSON 字符串
     *
     * @param response 响应
     * @param object 对象，会序列化成 JSON 字符串
     */
    @SuppressWarnings("deprecation") // 必须使用 APPLICATION_JSON_UTF8_VALUE，否则会乱码
    public static void writeJSON(HttpServletResponse response, Object object) {
        String content = JSON.toJSONString(object);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Writer writer = null;
        try {
            writer = response.getWriter();
            writer.write(content);
            writer.flush();
        } catch (IOException var8) {
            throw new UtilException(var8);
        } finally {
            IoUtil.close(writer);
        }
    }

}
