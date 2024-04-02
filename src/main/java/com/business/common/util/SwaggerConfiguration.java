package com.business.common.util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: GaoLu
 * @Date: 2023-10-20 11:23
 * @Description: Swagger配置类
 */
@Configuration
public class SwaggerConfiguration {


    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("后端接口文档").version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi httpApi() {
        return GroupedOpenApi.builder()
                .group("http")
                .pathsToMatch("/**")
                .build();
    }

}
