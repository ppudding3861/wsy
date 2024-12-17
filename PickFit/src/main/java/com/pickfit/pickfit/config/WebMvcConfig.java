package com.pickfit.pickfit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", "https://yourdomain.com") // 허용할 프론트엔드 도메인 추가
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 필요한 HTTP 메서드만 허용
                        .allowedHeaders("*") // 모든 헤더 허용
                        .allowCredentials(true); // 인증정보 허용 (쿠키, Authorization)
            }
        };
    }
}
