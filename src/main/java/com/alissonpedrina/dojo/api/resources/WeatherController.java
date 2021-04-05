package com.alissonpedrina.dojo.api.resources;

import com.alissonpedrina.dojo.domain.Query;
import com.alissonpedrina.dojo.domain.TypeQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private Query query;

    @GetMapping("/weather/id/{id}")
    public ResponseEntity<?> weatherById(@PathVariable("id") String id) throws JsonProcessingException {
        return query.run(TypeQuery.ID, id);

    }

    @GetMapping("/weather/bbox/{bbox}")
    public ResponseEntity<?> weatherByLatLong(@PathVariable("bbox") String bbox) throws JsonProcessingException {
        return query.run(TypeQuery.BBOX, bbox);

    }

    @GetMapping("/weather/lat/{lat}/long/{longi}")
    public ResponseEntity<?> weatherByLatLong(@PathVariable("lat") String lat,
                                              @PathVariable("longi") String longi) throws JsonProcessingException {
        return query.run(TypeQuery.LAT_LON, lat, longi);

    }

    @GetMapping("/weather/zip/{zip}")
    public ResponseEntity<?> weatherByZipCode(@PathVariable("zip") String zip) throws JsonProcessingException {
        return query.run(TypeQuery.CITY, zip);

    }

    @GetMapping("/weather/{city}")
    public ResponseEntity<?> weather(@PathVariable("city") String city) throws JsonProcessingException {
        return query.run(TypeQuery.CITY, city);

    }

    @GetMapping("/weather/queries/{city}")
    public ResponseEntity<?> queries(@PathVariable("city") String city) throws JsonProcessingException {
        return query.run(TypeQuery.CITIES, city);

    }

    @GetMapping("/weather/queries")
    public ResponseEntity<?> getAllQueries() throws JsonProcessingException {
        return query.run(TypeQuery.QUERIES, null);

    }

}
