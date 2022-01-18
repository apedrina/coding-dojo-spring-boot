package com.alissonpedrina.java17.domain.query;

import com.alissonpedrina.java17.api.client.WeatherClientEnum;
import com.alissonpedrina.java17.api.model.WeatherResponse;
import com.alissonpedrina.java17.domain.Query;
import com.alissonpedrina.java17.domain.WeatherStrategy;
import com.alissonpedrina.java17.jms.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class ByBox implements WeatherStrategy {

    public static ByBox INSTANCE;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Producer producer;
    @Autowired
    private Query query;

    public WeatherResponse process(String... bbox) throws JsonProcessingException {
        log.info("retrieving weather by bbox: {}", bbox[0]);
        var params = new HashMap<String, String>() {{
            put(WeatherClientEnum.PARAM_BBOX.toString(), bbox[0]);
        }};
        var weatherStr = query.callWeatherEndpoint(WeatherClientEnum.WEATHER_ENDPOINT_BOX_CITY.toString(), params);
        var weatherResponse = objectMapper.readValue(weatherStr, WeatherResponse.class);
        producer.sendMessage(weatherResponse);
        return weatherResponse;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;

    }

}