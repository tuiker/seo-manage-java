package com.business.handler;

/**
 * @Author: GaoLu
 * @Date: 2023-10-23 15:22
 * @Description: 跨域处理器
 */
//@Configuration
public class CorsConfig {
   /* @Bean
    public CorsFilter corsFilter() {
        //创建一个可添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //允许的域，若使用addAllowedOrigin("*");会导致cookie失效，要将*换成域名
        config.addAllowedOriginPattern("*");
        //是否发送cookie信息
        config.setAllowCredentials(true);
        //允许的请求方式
        config.addAllowedMethod("*");
        //允许的头信息
        config.addAllowedHeader("*");

        //添加映射路径，这儿拦截所有请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(configSource);
    }*/
}
