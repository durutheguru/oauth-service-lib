package com.julianduru.oauthservicelib;

import com.github.javafaker.Faker;
import com.julianduru.oauthservicelib.config.TestContainersConfig;
import com.julianduru.oauthservicelib.config.TestDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

/**
 * created by julian on 26/04/2022
 */
@Slf4j
@Testcontainers
@ContextConfiguration(
    classes = {
        TestContainersConfig.class,
        TestDatabaseConfig.class,
        OauthServiceLibAutoConfiguration.class,
    }
)
@ExtendWith({SpringExtension.class})
@EnableConfigurationProperties({DataSourceProperties.class})
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class OAuthServiceLibIntegrationTest {


    protected Faker faker = new Faker();


    @Container
    private static DockerComposeContainer dockerComposeContainer = new DockerComposeContainer<>(
        new File("src/test/resources/docker-compose.yml")
    ).withExposedService(
        "oauth-service_1", 10101,
        Wait.forHttp("/")
            .forStatusCodeMatching(code -> code >= 200 && code <= 500)
            .withStartupTimeout(Duration.ofSeconds(600))
    );


    @DynamicPropertySource
    protected static void setProperties(
        DynamicPropertyRegistry registry
    ) {
        setOauthServerProperties(registry);
        setOauthServerDbProperties(registry);
    }


    private static void setOauthServerDbProperties(DynamicPropertyRegistry registry) {
        var oauthServiceDbHost = dockerComposeContainer.getServiceHost("mysqldb_1", 33080);
        var oauthServiceDbPort = dockerComposeContainer.getServicePort("mysqldb_1", 33080);

        registry.add(
            "code.config.oauth2.authorization-server.db.ip",
            () -> oauthServiceDbHost
        );
        registry.add(
            "code.config.oauth2.authorization-server.db.port",
            () -> oauthServiceDbPort
        );
    }


    private static void setOauthServerProperties(DynamicPropertyRegistry registry) {
        var oauthServiceHost = dockerComposeContainer.getServiceHost("oauth-service_1", 10101);
        var oauthServicePort = dockerComposeContainer.getServicePort("oauth-service_1", 10101);

        var oauthServiceUrl = String.format("%s:%d", oauthServiceHost, oauthServicePort);

        registry.add(
            "code.config.oauth2.authorization-server.base-url",
            () -> oauthServiceUrl
        );
        registry.add(
            "code.config.oauth2.authorization-server.gql-base-url",
            () -> String.format("%s/graphql", oauthServiceUrl)
        );
    }


    @BeforeAll
    public void beforeAll() {
        log.info("Starting Docker Compose...");
    }


    @AfterAll
    public void afterAll() {
        log.info("Stopping Docker Compose...");
//        dockerComposeContainer.stop();
    }


}

