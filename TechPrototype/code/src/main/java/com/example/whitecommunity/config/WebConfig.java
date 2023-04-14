package com.example.whitecommunity.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/image/*").addResourceLocations("file:image/");
        registry.addResourceHandler("/api/media/*").addResourceLocations("file:media/");
    }
}
