package com.business.common.util;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @Author:GaoLu
 * @Date:2023-10-20 23:03
 * @Description:
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor(){

        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(-1L);
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOptimizeJoin(true);
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor() ;
        mybatisPlusInterceptor.setInterceptors(Collections.singletonList(paginationInnerInterceptor()));
        return mybatisPlusInterceptor;
    }
}

