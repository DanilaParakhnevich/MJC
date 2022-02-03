package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:db.properties")
public class DaoConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource () {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("jdbcUrl"));
        config.setPassword(environment.getProperty("password"));
        config.setDriverClassName(environment.getProperty("driverClassName"));
        config.setUsername(environment.getProperty("user"));
        config.setMaximumPoolSize(Integer.parseInt(environment.getProperty("maximumPoolSize")));
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
