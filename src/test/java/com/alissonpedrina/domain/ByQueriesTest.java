package com.alissonpedrina.domain;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.alissonpedrina.dojo.domain.query.ByQueries;
import com.alissonpedrina.dojo.repositories.JdbcWeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ByQueriesTest {

    @Mock
    private JdbcWeatherRepository jdbcWeatherRepository;
    @InjectMocks
    private ByQueries byQueries;

    @Test
    void should_process_queries() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        var jsonFile = TestUtils.getResourceFileFromJson("classpath:json/weather.json");
        var weatherResponse = mapper.readValue(jsonFile, WeatherResponse.class);

        byQueries.process(null);

        verify(jdbcWeatherRepository, times(1)).getAllWeatherQueries();

    }

}
