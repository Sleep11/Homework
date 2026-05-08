package com.bwie.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author THINKAPD
 */
@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {


    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //拦截所有请求
                .addPathPatterns("/**")
                //不拦截资源
                .excludePathPatterns("/auth/**","/error","/upload/**","/**/export*","/doc.html","/webjars/**","/swagger-resources/**", "/v2/api-docs");
    }
}