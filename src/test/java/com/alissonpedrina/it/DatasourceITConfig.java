package com.alissonpedrina.it;

import com.alissonpedrina.dojo.config.ConfigEnum;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

@TestConfiguration
public class DatasourceITConfig {

    @Bean
    public JdbcTemplate jdbcTemplate() throws IOException {
        BaseIT.init();
        var jdbcTemplate = new JdbcTemplate(BaseIT.dataSource);
        jdbcTemplate.execute(ConfigEnum.POSTGRESQL_SCRIPT.toString());

        return jdbcTemplate;

    }
}
