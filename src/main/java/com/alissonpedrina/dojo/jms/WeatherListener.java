package com.alissonpedrina.dojo.jms;

import com.alissonpedrina.dojo.api.error.DojoErrorException;
import com.alissonpedrina.dojo.api.model.WeatherQuery;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.alissonpedrina.dojo.repositories.JdbcWeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;

@Slf4j
@Component
public class WeatherListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcWeatherRepository jdbcWeatherRepository;

    @JmsListener(destination = "weatherQuery")
    public void receiveMessage(final Message jsonMessage) throws JMSException {
        try {
            var weatherResponseStr = "";
            if (jsonMessage instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) jsonMessage;
                weatherResponseStr = textMessage.getText();

            }
            log.info("consuming weatherQuery queue. Message: {}", weatherResponseStr);
            var weatherResponse = objectMapper.readValue(weatherResponseStr, WeatherResponse.class);
            saveQuery(weatherResponse);

        } catch (JsonProcessingException | JMSException e) {
            log.error("Error receiving message: {}.", ((TextMessage) jsonMessage).getText());
            new DojoErrorException(e.getMessage(), e);

        }
    }

    private void saveQuery(WeatherResponse weatherResponse) throws JsonProcessingException {
        var weatherResponseStr = objectMapper.writeValueAsString(weatherResponse);
        log.info("saving query, weather info: {}", weatherResponseStr);
        var query = WeatherQuery.builder()
                .date(new Date())
                .info(weatherResponseStr)
                .build();
        jdbcWeatherRepository.saveWeatherQuery(query);
        log.info("weather saved successfully.");

    }

}