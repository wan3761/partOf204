package com.partof204.partof204website.config;

import com.partof204.partof204website.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoadConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration registration = registry.addInterceptor(new UserInterceptor());
        registration.addPathPatterns("/**"); //所有路径都被拦截
        registration.excludePathPatterns(    //添加不拦截路径
                "/",
                "/index",
                "redirect:/index",
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.gif",
                "/login",                    //登录路径
                "/**/*.html",                //html静态资源
                "/**/*.js",                  //js静态资源
                "/**/*.css"                  //css静态资源
        );

    }
}
