package com.alissonpedrina.dojo.domain.query;

import com.alissonpedrina.dojo.api.model.WeatherQuery;
import com.alissonpedrina.dojo.domain.WeatherStrategy;
import com.alissonpedrina.dojo.repositories.JdbcWeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ByQueries implements WeatherStrategy<List<WeatherQuery>> {

    public static ByQueries INSTANCE;
    @Autowired
    private JdbcWeatherRepository jdbcWeatherRepository;

    public List<WeatherQuery> process(String... city) throws JsonProcessingException {
        log.info("retrieving all weather queries.");
        return jdbcWeatherRepository.getAllWeatherQueries();

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;

    }

}