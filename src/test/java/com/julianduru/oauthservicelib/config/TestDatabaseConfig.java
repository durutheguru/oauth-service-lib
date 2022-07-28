package com.julianduru.oauthservicelib.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * created by julian on 10/05/2022
 */
@Slf4j
@TestConfiguration
public class TestDatabaseConfig {


    @Bean
    public JdbcTemplate jdbcTemplate(
        @Autowired(required = false) DataSource dataSource,
        DataSourceProperties properties
    ) {
        return new JdbcTemplate(
            Objects.requireNonNullElseGet(dataSource, () -> dataSource(properties))
        );
    }


    private DataSource dataSource(DataSourceProperties properties) {
        var dataSource = new DriverManagerDataSource();

        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setDriverClassName(properties.getDriverClassName());

        return dataSource;
    }


}


