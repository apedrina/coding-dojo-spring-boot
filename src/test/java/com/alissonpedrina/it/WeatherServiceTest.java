package com.alissonpedrina.it;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.dojo.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class WeatherServiceTest extends BaseIT {

    private static final String JSON_PATH_FILE = "classpath:json/weather.json";

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() throws IOException {
        var jsonFile = TestUtils.getResourceFileFromJson(JSON_PATH_FILE);
        jdbcTemplate.update("INSERT INTO weather(info) VALUES (?::JSON)",
                (preparedStatement) -> preparedStatement.setString(1, jsonFile));

    }

    @Test
    void should_not_throw_any_exception_when_call_weather_by_city() {
        assertDoesNotThrow(() -> weatherService.getWeatherByCity("London"));

    }

    @Test
    void should_not_throw_any_exception_when_call_weather_by_id() {
        assertDoesNotThrow(() -> weatherService.getWeatherById("2172797"));

    }

}
