package com.ioc.authorize.main;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.ioc.authorize")
@MapperScan("com.ioc.authorize.dao")
@EnableSwagger2
public class AuthorizeUserServiceApplication {

    private static Logger logger =  Logger.getLogger( AuthorizeUserServiceApplication.class);

    public static void main(String[] args) {

        SpringApplication.run( AuthorizeUserServiceApplication.class, args );
        logger.info( "=============== 授权服务启动成功 ==================" );
    }

}
