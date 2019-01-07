package com.qingke.apiplatform.core.configurer;

import com.qingke.apiplatform.core.interceptors.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 蒋世芳【jiangshifang@reignwood.com】
 * @Date 2018/11/8 5:29 PM
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }


    @Bean
    public TokenInterceptor authenticationInterceptor() {
        return new TokenInterceptor();
    }
}
