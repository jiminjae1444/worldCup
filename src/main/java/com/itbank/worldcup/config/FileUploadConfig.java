package com.itbank.worldcup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileUploadConfig {

    @Value("${file.upload.categoryDir}")
    private String categoryDir;

    @Value("${file.upload.itemsDir}")
    private String itemsDir;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // C:/upload/worldcup/categoryImg 경로를 /upload/worldcup/category/** URL 경로로 매핑
                registry.addResourceHandler("/upload/worldcup/category/**")
                        .addResourceLocations("file:" + categoryDir + "/");

                // C:/upload/worldcup/items 경로를 /upload/worldcup/items/** URL 경로로 매핑
                registry.addResourceHandler("/upload/worldcup/items/**")
                        .addResourceLocations("file:" + itemsDir + "/");
            }
        };
    }
}