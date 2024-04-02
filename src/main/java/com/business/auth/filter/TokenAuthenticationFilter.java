package com.business.auth.filter;

import cn.hutool.core.util.StrUtil;
import com.business.auth.entity.LoginUser;
import com.business.common.response.ResultVO;
import com.business.common.util.SecurityUtils;
import com.business.common.util.ServletUtils;
import com.business.model.redis.LoginUserRedisDAO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.business.common.enums.ResultCode.UNAUTHORIZED;


/**
 * Token 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 *
 * @author yxf
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final LoginUserRedisDAO loginUserRedisDAO;

    public TokenAuthenticationFilter(LoginUserRedisDAO loginUserRedisDAO) {
        this.loginUserRedisDAO = loginUserRedisDAO;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取认证信息
        String token = SecurityUtils.obtainAuthorization(request);
        if(StrUtil.isNotBlank(token)) { //为空则放开进行其他过滤验证

            // redis获取用户信息
            LoginUser loginUser = loginUserRedisDAO.get(token);

            if(Objects.isNull(loginUser)){//未获取到用户信息：无效token
                //写入错误码401至response响应
                ServletUtils.writeJSON(response, ResultVO.error(UNAUTHORIZED));
                return;
            }

            // 存入securitycontextholder
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        // 放行
        filterChain.doFilter(request,response);
    }
}
