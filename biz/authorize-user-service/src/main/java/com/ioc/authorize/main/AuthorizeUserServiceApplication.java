package com.ioc.authorize.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ioc.authorize")
@MapperScan("com.ioc.authorize.dao")
public class AuthorizeUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run( AuthorizeUserServiceApplication.class, args );
    }

}
