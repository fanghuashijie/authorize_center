package com.ioc.authorize.integration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.validation.constraints.NotNull;


/**
* @Description: swagger 配置类
* @Author: DeYi Peng
* @CreateDate: 2019/7/3 19:32
* @Version: 1.0
*/
@Component
public class SwaggerConfig {

    // 是否开启swagger
    @Value( "${swagger.enable}" )
    @NotNull
    private Boolean enable;

    @Bean
    public Docket createRestApi(){
        return new Docket( DocumentationType.SWAGGER_2 ).apiInfo( apiInfo() )
                // 是否开启
                .enable( enable ).select()
                // 扫描的路径包
                .apis( RequestHandlerSelectors.basePackage( "com.ioc.authorize.controller" ) )
                // 指定路径处理PathSelectors.any()代表所有路径
                .paths( PathSelectors.any() ).build().pathMapping( "/" );
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title( "权限 API文档" )
                .description( "权限系统" )
                .termsOfServiceUrl( "http://www.dragcom.com" )
                .version( "1.0" )
                .build();
    }

}
