package org.acowzon.backend.config;

import org.acowzon.backend.config.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final String[] path = new String[]{"/user/**", "/order/**", "/shop/**"};
    private final String[] excludePath = new String[]{"/user/add", "/user/verify", "/search/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns(path)
                .excludePathPatterns(excludePath);
    }
}