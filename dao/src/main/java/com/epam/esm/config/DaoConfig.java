package com.epam.esm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Spring config class for dao layer.
 */
@Configuration
@ComponentScan("com.epam.esm")
public class DaoConfig {
    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.epam.esm.entity");
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    EntityManagerFactory entityManagerFactory (LocalContainerEntityManagerFactoryBean managerFactoryBean) {
        return managerFactoryBean.getObject();
    }

    @Bean
    JpaTransactionManager transactionManager (LocalContainerEntityManagerFactoryBean factoryBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factoryBean.getObject());
        return transactionManager;
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

    private Properties getHibernateProperties() {
        try {
            Properties hibernateProperties = new Properties();
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("properties/hibernate.properties");
            hibernateProperties.load(is);
            return hibernateProperties;

        } catch (IOException e) {
            throw new IllegalArgumentException("Can't find 'hibernate.properties' in classpath");
        }
    }

}
