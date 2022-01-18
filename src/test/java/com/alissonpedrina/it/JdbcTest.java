package com.alissonpedrina.it;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.java17.api.model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JdbcTest extends BaseIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void setUp() throws IOException {
        init();
        var jsonFile = TestUtils.getResourceFileFromJson("classpath:json/weather.json");
        jdbcTemplate.update("INSERT INTO weather(info) VALUES (?::JSON)",
                (preparedStatement) -> preparedStatement.setString(1, jsonFile));

    }

    @Test
    void should_get_weather_info() {
        int expectedSize = 1;

        var weathers = jdbcTemplate.queryForObject(
                "SELECT info FROM weather", PGobject[].class);

        assertEquals(expectedSize, weathers.length);

    }

    @Test
    void should_get_the_city_name() throws JsonProcessingException {
        var expectedName = "London";
        var expectedSize = 1;

        var weathers = jdbcTemplate.queryForObject("SELECT info FROM weather", PGobject[].class);
        var weather = objectMapper.readValue(weathers[0].getValue(), WeatherResponse.class);

        assertThat(weathers.length).isEqualTo(expectedSize);
        assertThat(weather.getName()).isEqualTo(expectedName);

    }

}
