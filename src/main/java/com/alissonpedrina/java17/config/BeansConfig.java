package com.alissonpedrina.java17.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();

    }

}
