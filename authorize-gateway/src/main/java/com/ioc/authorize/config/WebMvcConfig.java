package com.ioc.authorize.config;

import com.ioc.authorize.interceptor.CtokenInterceptor;
import com.ioc.authorize.interceptor.TraceIdInterceptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * 容器配置说明.
 *
 */

@Configuration
@EnableAutoConfiguration
public class WebMvcConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * TraceId设置
         */
        registry.addInterceptor(new TraceIdInterceptor());
        /**
         * Ctoken验证验证(未起作用)
         */
        registry.addInterceptor(new CtokenInterceptor() );
    }
}