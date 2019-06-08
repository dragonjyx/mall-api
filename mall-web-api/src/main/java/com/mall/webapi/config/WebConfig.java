package com.mall.webapi.config;

import com.mall.webapi.filter.CrossFilter;
import com.mall.webapi.interceptor.LoginIntercepter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 配置
 */
@Configuration
@MapperScan(basePackages = "com.mall.mapper")
@ComponentScan(basePackages = "com.mall.service")
@ComponentScan(basePackages = "com.mall.dao")
@ImportResource(locations = "classpath:spring/spring-context.xml")
public class WebConfig extends WebMvcConfigurationSupport {

    @Value("${spring.profiles.active}")
    private String env;


    //注册拦截器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( new LoginIntercepter(env))
                .addPathPatterns("/**")
                .excludePathPatterns("/error")//一定要加上去
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/wechat/mp/**")
                .excludePathPatterns("/user/check-login")
                .excludePathPatterns("/member/check-login");
        super.addInterceptors(registry);
    }


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    //注册filter
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> crossFilter(){
        FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<DelegatingFilterProxy>();
        registration.setFilter(new DelegatingFilterProxy(new CrossFilter()));
        registration.addUrlPatterns("/*");
        return registration;
    }









}
