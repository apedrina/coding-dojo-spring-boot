package com.alissonpedrina.it;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.dojo.api.model.WeatherQuery;
import com.alissonpedrina.dojo.repositories.JdbcWeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JdbcRepositoryTest extends BaseIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcWeatherRepository jdbcWeatherRepository;

    @BeforeAll
    void setUp() throws IOException {
        var jsonFile = TestUtils.getResourceFileFromJson("classpath:json/weather.json");
        var weatherQuery = WeatherQuery.builder()
                .date(new Date())
                .info(jsonFile)
                .build();

        jdbcWeatherRepository.saveWeatherQuery(weatherQuery);

    }

    @Test
    void should_get_the_city_name() {
        int expectedSize = 1;
        var cityName = "London";

        var weathers = jdbcWeatherRepository.getWeatherQueries(cityName);

        assertEquals(expectedSize, weathers.size());

    }

    @Test
    void should_get_all_queries() {

        Assertions.assertDoesNotThrow(() -> jdbcWeatherRepository.getAllWeatherQueries());

    }

}
