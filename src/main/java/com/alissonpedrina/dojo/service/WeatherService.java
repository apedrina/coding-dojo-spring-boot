package com.alissonpedrina.dojo.service;

import com.alissonpedrina.dojo.api.client.WeatherClientEnum;
import com.alissonpedrina.dojo.api.client.WeatherRestClient;
import com.alissonpedrina.dojo.api.model.WeatherQuery;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.alissonpedrina.dojo.repositories.JdbcWeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Slf4j
@Component
public class WeatherService {

    @Autowired
    private JdbcWeatherRepository jdbcWeatherRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherRestClient weatherRestClient;

    public WeatherResponse getWeatherByCity(String city) throws JsonProcessingException {
        log.info("retrieving weather by city: {}", city);
        var params = new HashMap<String, String>();
        params.put(WeatherClientEnum.PARAM_Q.toString(), city);
        return objectMapper.readValue(callWeatherEndpoint(WeatherClientEnum.WEATHER_ENDPOINT.toString(), params), WeatherResponse.class);

    }

    public WeatherResponse getWeatherByLatLong(String lat, String longi) throws JsonProcessingException {
        log.info("retrieving weather by lat & long: {},{}", lat, longi);
        var params = new HashMap<String, String>();
        params.put(WeatherClientEnum.PARAM_LAT.toString(), lat);
        params.put(WeatherClientEnum.PARAM_LONG.toString(), longi);
        return objectMapper.readValue(callWeatherEndpoint(WeatherClientEnum.WEATHER_ENDPOINT.toString(), params), WeatherResponse.class);

    }

    public WeatherResponse getWeatherByBbox(String bbox) throws JsonProcessingException {
        log.info("retrieving weather by bbox: {}", bbox);
        var params = new HashMap<String, String>();
        params.put(WeatherClientEnum.PARAM_BBOX.toString(), bbox);
        return objectMapper.readValue(callWeatherEndpoint(WeatherClientEnum.WEATHER_ENDPOINT_BOX_CITY.toString(), params), WeatherResponse.class);

    }

    public WeatherResponse getWeatherByZipCode(String zip) throws JsonProcessingException {
        log.info("retrieving weather by zip code: {}", zip);
        var params = new HashMap<String, String>();
        params.put(WeatherClientEnum.PARAM_ZIP.toString(), zip);
        return objectMapper.readValue(callWeatherEndpoint(WeatherClientEnum.WEATHER_ENDPOINT.toString(), params), WeatherResponse.class);

    }

    public WeatherResponse getWeatherById(String id) throws JsonProcessingException {
        log.info("retrieving weather by id: {}", id);
        var params = new HashMap<String, String>();
        params.put(WeatherClientEnum.PARAM_ID.toString(), id);
        return objectMapper.readValue(callWeatherEndpoint(WeatherClientEnum.WEATHER_ENDPOINT.toString(), params), WeatherResponse.class);

    }

    public void saveQuery(WeatherResponse weatherResponse) throws JsonProcessingException {
        var weatherResponseStr = objectMapper.writeValueAsString(weatherResponse);
        log.info("saving query, weather info: {}", weatherResponseStr);
        var query = WeatherQuery.builder()
                .date(new Date())
                .info(weatherResponseStr)
                .build();
        jdbcWeatherRepository.saveWeatherQuery(query);
        log.info("weather saved successfully.");

    }

    public List<WeatherQuery> getAllWeatherQueries() {
        log.info("retrieving all weather queries.");
        return jdbcWeatherRepository.getAllWeatherQueries();

    }

    public List<WeatherQuery> getWeatherQueries(String city) {
        log.info("retrieving weather queries by city: {}", city);
        return jdbcWeatherRepository.getWeatherQueries(city);

    }

    private String callWeatherEndpoint(String endpoint, HashMap<String, String> params) {
        return weatherRestClient.exchange(endpoint, params).getBody();

    }

}
