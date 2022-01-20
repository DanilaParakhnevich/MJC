package by.parakhnevich.logic.config;

import by.parakhnevich.logic.dao.exception.DAOException;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan("by.parakhnevich.logic")
public class SpringConfig {
    private static final String PROP_FILE_NAME = "db.properties";

    @Bean
    public DataSource dataSource () throws DAOException {
        HikariDataSource dataSource = new HikariDataSource();
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
            properties.load(inputStream);
            properties.getProperty("user");
            properties.getProperty("password");
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setJdbcUrl(properties.getProperty("url"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setUsername(properties.getProperty("user"));
            return dataSource;
        } catch (IOException e) {
            throw new DAOException(e);
        }
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) throws DAOException {
        return new JdbcTemplate(dataSource);
    }
}
