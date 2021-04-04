package com.alissonpedrina.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class JmsTests extends BaseIT {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    void should_receive_fake_message() {
        this.jmsTemplate.convertAndSend("weather", "{}");
        this.jmsTemplate.setReceiveTimeout(10_000);
        assertThat(this.jmsTemplate.receiveAndConvert("weather")).isEqualTo("{}");

    }

}
