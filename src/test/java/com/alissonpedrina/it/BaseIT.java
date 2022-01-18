package com.alissonpedrina.it;

import com.alissonpedrina.java17.api.client.WeatherRestClient;
import com.alissonpedrina.java17.api.resources.WeatherController;
import com.alissonpedrina.java17.config.ProducerConfig;
import com.alissonpedrina.java17.config.ReceiverConfig;
import com.alissonpedrina.java17.domain.Query;
import com.alissonpedrina.java17.domain.query.*;
import com.alissonpedrina.java17.jms.Producer;
import com.alissonpedrina.java17.repositories.JdbcWeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

@Profile("test")
@Slf4j
@ExtendWith(SpringExtension.class)
@DirtiesContext
@SpringBootTest(classes = {
        DatasourceITConfig.class, Producer.class, ReceiverConfig.class,
        ProducerConfig.class, JdbcWeatherRepository.class, WeatherController.class,
        ById.class, ByBox.class, ByCities.class, ByQueries.class,
        ByCity.class, ByLatLon.class, ByZip.class,
        Query.class, BeansIT.class, WeatherRestClient.class})
@TestPropertySource("classpath:application-integration-containers.properties")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseIT {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:9.4")
                    .waitingFor(Wait.forListeningPort());
    public static DriverManagerDataSource dataSource;
    private static String driver = "org.postgresql.Driver";

    public static void init() throws IOException {
        postgreSQLContainer.start();
        dataSourceStart();
        jdbcTemplateDefinition();

    }

    private static void dataSourceStart() {
        log.info("starting data source: {},{},{}",
                driver, postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername());
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());

    }

    private static void jdbcTemplateDefinition() {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();

        BeanDefinitionBuilder jdbcTemplateDefinition =
                BeanDefinitionBuilder.rootBeanDefinition(JdbcTemplate.class)
                        .addPropertyValue("datasource", dataSource);
        beanFactory.registerBeanDefinition("jdbcTemplate", jdbcTemplateDefinition.getBeanDefinition());

    }

}

