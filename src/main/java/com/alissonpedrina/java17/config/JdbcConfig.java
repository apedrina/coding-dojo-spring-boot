package com.alissonpedrina.java17.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Slf4j
@Profile("!test")
@Configuration
public class JdbcConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        var jdbcTemplate = new JdbcTemplate(postgresDataSource());
        jdbcTemplate.execute(ConfigEnum.POSTGRESQL_SCRIPT.toString());

        return jdbcTemplate;

    }

    @Bean
    public DataSource postgresDataSource() {
        log.info("creating DataSource: ({},{},{})", dbDriver, dbUrl, dbUsername);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

}
