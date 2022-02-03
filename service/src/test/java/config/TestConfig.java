package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:test_db.properties")
public class TestConfig {
    @Bean("testDataSource")
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:scripts/bd_create.sql")
                .addScript("classpath:scripts/bd_init.sql")
                .build();
    }

    @Bean("testJdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(embeddedDataSource());
    }
}
