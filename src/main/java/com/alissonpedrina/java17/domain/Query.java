package com.alissonpedrina.java17.domain;

import com.alissonpedrina.java17.api.client.WeatherRestClient;
import com.alissonpedrina.java17.domain.query.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class Query {

    @Autowired
    public WeatherRestClient weatherRestClient;

    public ResponseEntity<?> run(TypeQuery typeQuery, String... args) throws JsonProcessingException {
        log.info("routing for: {}", typeQuery.toString());
        switch (typeQuery) {
            case QUERIES:
                return ResponseEntity.ok(ByQueries.INSTANCE.process(args));
            case CITIES:
                return ResponseEntity.ok(ByCities.INSTANCE.process(args));
            case CITY:
                return ResponseEntity.ok(ByCity.INSTANCE.process(args));
            case BBOX:
                return ResponseEntity.ok(ByBox.INSTANCE.process(args));
            case ID:
                return ResponseEntity.ok(ById.INSTANCE.process(args));
            case ZIP:
                return ResponseEntity.ok(ByZip.INSTANCE.process(args));
            case LAT_LON:
                return ResponseEntity.ok(ByLatLon.INSTANCE.process(args));

        }
        log.info("routing NOT FOUND for: {}", typeQuery.toString());
        return ResponseEntity.ok(HttpStatus.NOT_FOUND);

    }

    public String callWeatherEndpoint(String endpoint, HashMap<String, String> params) {
        return weatherRestClient.exchange(endpoint, params).getBody();

    }

}

