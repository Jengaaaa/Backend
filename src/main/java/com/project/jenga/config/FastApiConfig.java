package com.project.jenga.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FastApiConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // FastAPI 주소
    public static final String FASTAPI_BASE_URL = "http://localhost:8000";
}
