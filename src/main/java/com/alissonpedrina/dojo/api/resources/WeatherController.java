package com.alissonpedrina.dojo.api.resources;

import com.alissonpedrina.dojo.api.model.WeatherQuery;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.alissonpedrina.dojo.jms.Producer;
import com.alissonpedrina.dojo.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private Producer producer;

    @GetMapping("/weather/id/{id}")
    public ResponseEntity<WeatherResponse> weatherById(@PathVariable("id") String id) throws JsonProcessingException {
        var weatherResponse = weatherService.getWeatherById(id);
        producer.sendMessage(weatherResponse);
        return ResponseEntity.ok().body(weatherResponse);

    }

    @GetMapping("/weather/bbox/{bbox}")
    public ResponseEntity<WeatherResponse> weatherByLatLong(@PathVariable("bbox") String bbox) throws JsonProcessingException {
        var weatherResponse = weatherService.getWeatherByBbox(bbox);
        producer.sendMessage(weatherResponse);
        return ResponseEntity.ok().body(weatherResponse);

    }

    @GetMapping("/weather/lat/{lat}/long/{longi}")
    public ResponseEntity<WeatherResponse> weatherByLatLong(@PathVariable("lat") String lat,
                                                            @PathVariable("longi") String longi) throws JsonProcessingException {
        var weatherResponse = weatherService.getWeatherByLatLong(lat, longi);
        producer.sendMessage(weatherResponse);
        return ResponseEntity.ok().body(weatherResponse);

    }

    @GetMapping("/weather/zip/{zip}")
    public ResponseEntity<WeatherResponse> weatherByZipCode(@PathVariable("zip") String zip) throws JsonProcessingException {
        var weatherResponse = weatherService.getWeatherByZipCode(zip);
        producer.sendMessage(weatherResponse);
        return ResponseEntity.ok().body(weatherResponse);

    }

    @GetMapping("/weather/{city}")
    public ResponseEntity<WeatherResponse> weather(@PathVariable("city") String city) throws JsonProcessingException {
        var weatherResponse = weatherService.getWeatherByCity(city);
        producer.sendMessage(weatherResponse);
        return ResponseEntity.ok().body(weatherResponse);

    }

    @GetMapping("/weather/queries/{city}")
    public ResponseEntity<List<WeatherQuery>> queries(@PathVariable("city") String city) {
        var weathers = weatherService.getWeatherQueries(city);
        return ResponseEntity.ok().body(weathers);

    }

    @GetMapping("/weather/queries")
    public ResponseEntity<List<WeatherQuery>> getAllQueries() {
        var weathers = weatherService.getAllWeatherQueries();
        return ResponseEntity.ok().body(weathers);

    }

}
