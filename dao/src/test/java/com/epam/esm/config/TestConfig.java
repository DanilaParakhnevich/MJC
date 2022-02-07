package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Spring config class for testing
 */
@Configuration
public class TestConfig extends DaoConfig{
    @Bean
    @Override
    public DataSource dataSource(Environment environment) {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:scripts/bd_create.sql")
                .addScript("classpath:scripts/bd_init.sql")
                .build();
    }
}
