package com.alissonpedrina.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class BeansIT {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();

    }

}
