package com.alissonpedrina.dojo.jms;

import com.alissonpedrina.dojo.api.error.DojoErrorException;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.alissonpedrina.dojo.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Slf4j
@Component
public class WeatherListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherService weatherService;

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
            weatherService.saveQuery(weatherResponse);

        } catch (JsonProcessingException | JMSException e) {
            log.error("Error receiving message: {}.", ((TextMessage) jsonMessage).getText());
            new DojoErrorException(e.getMessage(), e);

        }
    }

}
