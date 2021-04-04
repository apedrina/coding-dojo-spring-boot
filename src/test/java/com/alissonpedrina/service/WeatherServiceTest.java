package com.alissonpedrina.service;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.dojo.api.client.WeatherRestClient;
import com.alissonpedrina.dojo.api.error.DojoErrorException;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.alissonpedrina.dojo.repositories.JdbcWeatherRepository;
import com.alissonpedrina.dojo.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private WeatherRestClient weatherRestClient;

    @Mock
    private JdbcWeatherRepository jdbcWeatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    private String jsonFileContent;

    private WeatherResponse weatherResponse;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() throws IOException {
        mapper = new ObjectMapper();
        jsonFileContent = TestUtils.getResourceFileFromJson("classpath:json/weather.json");
        weatherResponse = mapper.readValue(jsonFileContent, WeatherResponse.class);

        when(weatherRestClient.exchange(anyString(), any(HashMap.class)))
                .thenReturn(new HttpEntity(jsonFileContent));
        when(objectMapper.writeValueAsString(weatherResponse))
                .thenReturn(jsonFileContent);
        when(objectMapper.readValue(jsonFileContent, WeatherResponse.class))
                .thenReturn(weatherResponse);

    }

    @Test
    void should_query_weather_by_city() throws IOException {
        var weatherResponseResult = weatherService.getWeatherByCity("London");

        assertThat(weatherResponseResult).isEqualTo(mapper.readValue(jsonFileContent, WeatherResponse.class));

    }

    @Test
    void should_save_a_weather_query() throws IOException {
        weatherService.saveQuery(weatherResponse);

        Mockito.verify(jdbcWeatherRepository, Mockito.times(1)).saveWeatherQuery(any());

    }

    @Test
    void should_throw_a_json_exception_when_save_weather_query() throws IOException {
        when(objectMapper.writeValueAsString(weatherResponse))
                .thenThrow(JsonProcessingException.class);

        Assertions.assertThrows(JsonProcessingException.class, () -> weatherService.saveQuery(weatherResponse));

    }

    @Test
    void should_throw_a_json_exception_when_try_get_weather_by_city() throws IOException {
        when(objectMapper.writeValueAsString(weatherResponse))
                .thenThrow(JsonProcessingException.class);

        Assertions.assertThrows(JsonProcessingException.class, () -> weatherService.getWeatherByCity("London"));

    }

}
