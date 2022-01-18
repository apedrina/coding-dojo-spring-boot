package com.alissonpedrina.domain;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.java17.api.model.WeatherResponse;
import com.alissonpedrina.java17.domain.query.ByCities;
import com.alissonpedrina.java17.repositories.JdbcWeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ByCitiesTest {

    @Mock
    private JdbcWeatherRepository jdbcWeatherRepository;
    @InjectMocks
    private ByCities byCities;

    @Test
    void should_process_cities() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        var jsonFile = TestUtils.getResourceFileFromJson("classpath:json/weather.json");
        var weatherResponse = mapper.readValue(jsonFile, WeatherResponse.class);

        byCities.process("London");

        verify(jdbcWeatherRepository, times(1)).getWeatherQueries(anyString());

    }

}
