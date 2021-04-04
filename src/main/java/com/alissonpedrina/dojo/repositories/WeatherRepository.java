package com.alissonpedrina.dojo.repositories;

import com.alissonpedrina.dojo.api.model.WeatherQuery;

import java.util.List;

public interface WeatherRepository {

    void saveWeatherQuery(WeatherQuery query);

    List<WeatherQuery> getWeatherQueries(String city);

    List<WeatherQuery> getAllWeatherQueries();

}
