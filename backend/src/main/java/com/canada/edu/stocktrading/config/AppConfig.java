package com.canada.edu.stocktrading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://fidcoin-stocktrading.s3-website.us-east-2.amazonaws.com")
                        .allowedOriginPatterns("http://localhost:4200")
                        .exposedHeaders("Authorization")
                        .allowedMethods("GET","POST","PUT","DELETE");
                registry.addMapping("/ws/**")
                        .allowedOriginPatterns("http://fidcoin-stocktrading.s3-website.us-east-2.amazonaws.com")
                        .allowedOriginPatterns("http://localhost:4200")
                        .allowCredentials(true);
            }
        };
    }
}
