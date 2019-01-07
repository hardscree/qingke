package com.qingke.apiplatform.core.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class QingkeResourceConfig implements WebMvcConfigurer {
    @Value("${qingke.filePath}")
    private String filePath;
    @Value("${qingke.fileMapName}")
    private String fileMapName;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String staticMapping="/"+ fileMapName + "/**";
        String localDirectory = "file:"+filePath;
        registry.addResourceHandler(staticMapping).addResourceLocations(localDirectory);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}

