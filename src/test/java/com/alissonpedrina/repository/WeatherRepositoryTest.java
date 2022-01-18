package com.alissonpedrina.repository;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.java17.api.model.WeatherQuery;
import com.alissonpedrina.java17.repositories.JdbcWeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.Date;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WeatherRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcWeatherRepository weatherRepository;

    private WeatherQuery weatherQuery;

    @BeforeEach
    void setUp() throws IOException {
        var date = new Date();
        var jsonFileContent = TestUtils.getResourceFileFromJson("classpath:json/weather.json");
        weatherQuery = WeatherQuery.builder()
                .info(jsonFileContent)
                .date(date)
                .build();

    }

    @Test
    void should_call_save_query_method() throws IOException {
        weatherRepository.saveWeatherQuery(weatherQuery);

        verify(jdbcTemplate, times(1)).update("insert into weather(info) values(?::JSON)",
                weatherQuery.getInfo());

    }

}
