package com.alissonpedrina.dojo.jms;

import com.alissonpedrina.dojo.api.error.DojoErrorException;
import com.alissonpedrina.dojo.api.model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
@Slf4j
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(final WeatherResponse weatherResponse) {
        var queueName = "weatherQuery";
        try {
            var messageStr = objectMapper.writeValueAsString(weatherResponse);
            log.info("Sending message: {}", messageStr);
            jmsTemplate.send(queueName, new MessageCreator() {
                public TextMessage createMessage(Session session) throws JMSException {
                    TextMessage message = session.createTextMessage();
                    message.setText(messageStr);
                    return message;

                }
            });
            log.info("Sent message successfully.");

        } catch (JsonProcessingException e) {
            log.error("Error sending message to weather queue.");
            new DojoErrorException(e.getMessage(), e);

        }
    }

}