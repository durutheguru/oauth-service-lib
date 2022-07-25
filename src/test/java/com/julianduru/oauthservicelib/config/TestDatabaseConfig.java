package com.julianduru.oauthservicelib.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * created by julian on 10/05/2022
 */
@Slf4j
@TestConfiguration
public class TestDatabaseConfig {


    @Bean
    @ConditionalOnProperty(name = "testcontainers.enabled", havingValue = "true")
    public DataSource dataSource(DockerComposeContainer dockerComposeContainer) {
        if (dockerComposeContainer == null) {
            log.warn("Docker Compose Container not found.");
            return null;
        }

        var containerStateOptional = dockerComposeContainer.getContainerByServiceName("mysqldb_1");
        if (containerStateOptional.isPresent()) {
            var containerState = (ContainerState) containerStateOptional.get();
            var oauthServiceIp = containerState.getContainerIpAddress();
            var portBinding = containerState.getPortBindings().get(0);
            var oauthServicePort = Integer.parseInt(portBinding.substring(0, portBinding.indexOf(":")));

            var dataSource = new MysqlDataSource();
            dataSource.setURL(
                String.format(
                    "jdbc:mysql://%s:%d/oauth_service?createDatabaseIfNotExist=true",
                    oauthServiceIp, oauthServicePort
                )
            );
            dataSource.setUser("root");
            dataSource.setPassword("1234567890");

            return dataSource;
        }

        throw new IllegalStateException("Cannot find docker compose service name: mysqldb");
    }


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


