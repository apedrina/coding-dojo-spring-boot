package com.alissonpedrina.it;

import com.alissonpedrina.commons.TestUtils;
import com.alissonpedrina.java17.domain.Query;
import com.alissonpedrina.java17.domain.TypeQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QueriesTest extends BaseIT {

    private static final String JSON_PATH_FILE = "classpath:json/weather.json";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Query query;

    @BeforeAll
    void setUp() throws IOException {
        var jsonFile = TestUtils.getResourceFileFromJson(JSON_PATH_FILE);
        jdbcTemplate.update("INSERT INTO weather(info) VALUES (?::JSON)",
                (preparedStatement) -> preparedStatement.setString(1, jsonFile));

    }

    @ParameterizedTest
    @ValueSource(strings = {"London,uk", "Noordwijk,nl", "São Paulo,br"})
    void should_not_throw_any_exception_when_call_weather_by_city(String city) {
        assertDoesNotThrow(() -> query.run(TypeQuery.CITY, city));

    }

    @ParameterizedTest
    @ValueSource(strings = {"2172797", "2172797"})
    void should_not_throw_any_exception_when_call_weather_by_id(String id) {
        assertDoesNotThrow(() -> query.run(TypeQuery.ID, id));

    }

    @ParameterizedTest
    @ValueSource(strings = {"12,32,15,37,10"})
    void should_not_throw_any_exception_when_call_weather_by_box(String bbox) {
        assertDoesNotThrow(() -> query.run(TypeQuery.BBOX, bbox));

    }

    @ParameterizedTest
    @ValueSource(strings = {"35,139"})
    void should_not_throw_any_exception_when_call_weather_by_lat_lon(String latLong) {
        var coord = latLong.split(",");
        assertDoesNotThrow(() -> query.run(TypeQuery.LAT_LON, coord[0], coord[1]));

    }

    @ParameterizedTest
    @ValueSource(strings = {"94040,us", "2204,nl"})
    void should_not_throw_any_exception_when_call_weather_by_zip_code(String zip) {
        assertDoesNotThrow(() -> query.run(TypeQuery.ZIP, zip));

    }

    @ParameterizedTest
    @ValueSource(strings = {"/weather/queries"})
    public void should_not_throw_any_exception_when_all_queries(String url) throws Exception {
        assertDoesNotThrow(() -> query.run(TypeQuery.QUERIES, null));

    }

    @ParameterizedTest
    @ValueSource(strings = {"London", "Noordwijk", "São Paulo"})
    public void get_cities(String city) throws Exception {
        assertDoesNotThrow(() -> query.run(TypeQuery.CITIES, city));

    }

}
