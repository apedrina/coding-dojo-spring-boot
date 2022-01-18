package com.alissonpedrina.java17.domain.query;

import com.alissonpedrina.java17.api.model.WeatherQuery;
import com.alissonpedrina.java17.domain.WeatherStrategy;
import com.alissonpedrina.java17.jms.Producer;
import com.alissonpedrina.java17.repositories.JdbcWeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ByCities implements WeatherStrategy<List<WeatherQuery>> {

    public static ByCities INSTANCE;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Producer producer;
    @Autowired
    private JdbcWeatherRepository jdbcWeatherRepository;

    public List<WeatherQuery> process(String... city) throws JsonProcessingException {
        log.info("retrieving weather queries by city: {}", city[0]);
        return jdbcWeatherRepository.getWeatherQueries(city[0]);

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;

    }

}