package com.backend.APIRest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "checadorEntityManagerFactory",
        transactionManagerRef = "checadorTransactionManager",
        basePackages = { "com.backend.APIRest.repository.checador" }
)
public class ChecadorDatabaseConfig {

    @Autowired
    private Environment environment;
    @Bean
    @Primary
    @ConfigurationProperties("checador.datasource")
    public DataSourceProperties checadorDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource checadorDataSource() {
        return checadorDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "checadorEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean checadorEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(checadorDataSource())
                .packages("com.backend.APIRest.model.entidades.checador")
                .persistenceUnit("checador")
                .properties(hibernateProperties())
                .build();
    }

    private Map<String, Object> hibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("checador.jpa.hibernate.ddl-auto"));
        return properties;
    }
    @Primary
    @Bean
    public PlatformTransactionManager checadorTransactionManager(
            final @Qualifier("checadorEntityManagerFactory") LocalContainerEntityManagerFactoryBean checadorEntityManagerFactory) {
        return new JpaTransactionManager(checadorEntityManagerFactory.getObject());
    }
}