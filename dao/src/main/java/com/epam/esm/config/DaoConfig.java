package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Spring config class for dao layer.
 */
@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:db.properties")
public class DaoConfig {
    /**
     * Data source for dev profile.
     *
     * @param environment the environment
     * @return the data source
     */
    @Bean
    public DataSource dataSource (Environment environment) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("jdbcUrl"));
        config.setPassword(environment.getProperty("password"));
        config.setDriverClassName(environment.getProperty("driverClassName"));
        config.setUsername(environment.getProperty("user"));
        config.setMaximumPoolSize(Integer.parseInt(environment.getProperty("maximumPoolSize")));
        return new HikariDataSource(config);
    }

    /**
     * Jdbc template for prod profile.
     *
     * @param dataSource the data source
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
