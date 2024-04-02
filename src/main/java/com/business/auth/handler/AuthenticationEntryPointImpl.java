package com.business.auth.handler;

import com.business.common.response.ResultVO;
import com.business.common.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.business.common.enums.ResultCode.UNAUTHORIZED;

/**
 * 访问一个需要认证的 URL 资源，但是此时自己尚未认证（登录）的情况下，返回 {@link GlobalErrorCodeConstants#UNAUTHORIZED} 错误码，从而使前端重定向到登录页
 *
 * 补充：Spring Security 通过 {@link ExceptionTranslationFilter#sendStartAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, AuthenticationException)} 方法，调用当前类
 *
 * @author yxf
 */
@Slf4j
@SuppressWarnings("JavadocReference") // 忽略文档引用报错
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), e);
        // 返回 401
        //写入错误码401至response响应
        ServletUtils.writeJSON(response, ResultVO.error(UNAUTHORIZED));
    }
}
