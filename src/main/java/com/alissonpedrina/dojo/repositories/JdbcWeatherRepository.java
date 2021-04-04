package com.alissonpedrina.dojo.repositories;

import com.alissonpedrina.dojo.api.model.WeatherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcWeatherRepository implements WeatherRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveWeatherQuery(WeatherQuery query) {
        jdbcTemplate.update(
                "insert into weather(info) values(?::JSON)",
                query.getInfo());

    }

    @Override
    public List<WeatherQuery> getWeatherQueries(String city) {
        return jdbcTemplate.query(
                String.format("select id, date, info from weather where info ->> 'name' = '%s'", city),
                (rs, rowNum) ->
                        new WeatherQuery(
                                rs.getLong("id"),
                                rs.getTimestamp("date"),
                                rs.getString("info")
                        )
        );

    }

    @Override
    public List<WeatherQuery> getAllWeatherQueries() {
        return jdbcTemplate.query(
                String.format("select id, date, info from weather"),
                (rs, rowNum) ->
                        new WeatherQuery(
                                rs.getLong("id"),
                                rs.getTimestamp("date"),
                                rs.getString("info")
                        )
        );

    }

}
