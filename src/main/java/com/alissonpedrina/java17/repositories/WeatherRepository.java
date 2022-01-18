package com.alissonpedrina.java17.repositories;

import com.alissonpedrina.java17.api.model.WeatherQuery;

import java.util.List;

public interface WeatherRepository {

    void saveWeatherQuery(WeatherQuery query);

    List<WeatherQuery> getWeatherQueries(String city);

    List<WeatherQuery> getAllWeatherQueries();

}
