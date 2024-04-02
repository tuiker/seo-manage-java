package com.example.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement //开启事务
@SpringBootApplication
@MapperScan("com.business.model.dao")  //Mapper映射，Mybatis-plus
@ComponentScan("com.business")   //扫描组件
public class SEOManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SEOManageApplication.class, args);
    }

}
