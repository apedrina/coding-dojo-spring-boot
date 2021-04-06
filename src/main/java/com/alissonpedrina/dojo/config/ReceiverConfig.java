package com.alissonpedrina.dojo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.Session;
import java.util.List;

@Slf4j
@Configuration
@EnableJms
public class ReceiverConfig {

    private static final List<String> TRUSTED_PACKAGES = List.of(
            "com.alissonpedrina",
            "java");

    @Value("${spring.activemq.broker-url}")
    String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        log.info("Configuring ActiveMQ Receiver Connection Factory.");
        log.info("broker URL: {}.", brokerUrl);
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setTrustedPackages(TRUSTED_PACKAGES);
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setSendAcksAsync(true);
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(receiverActiveMQConnectionFactory());
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }

}
