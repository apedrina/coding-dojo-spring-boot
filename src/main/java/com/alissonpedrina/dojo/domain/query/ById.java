package com.alissonpedrina.dojo.domain.query;

import com.alissonpedrina.dojo.api.client.WeatherClientEnum;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.alissonpedrina.dojo.domain.Query;
import com.alissonpedrina.dojo.domain.WeatherStrategy;
import com.alissonpedrina.dojo.jms.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class ById implements WeatherStrategy<WeatherResponse> {

    public static ById INSTANCE;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Producer producer;
    @Autowired
    private Query query;

    public WeatherResponse process(String... id) throws JsonProcessingException {
        log.info("retrieving weather by id: {}", id[0]);
        var params = new HashMap<String, String>() {{
            put(WeatherClientEnum.PARAM_ID.toString(), id[0]);
        }};
        var weatherResponseStr = query.callWeatherEndpoint(WeatherClientEnum.WEATHER_ENDPOINT.toString(), params);
        var weatherResponse = objectMapper.readValue(weatherResponseStr, WeatherResponse.class);

        producer.sendMessage(weatherResponse);

        return weatherResponse;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;

    }
}