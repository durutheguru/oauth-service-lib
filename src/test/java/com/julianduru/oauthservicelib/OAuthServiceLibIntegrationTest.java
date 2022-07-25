package com.julianduru.oauthservicelib;

import com.github.javafaker.Faker;
import com.julianduru.oauthservicelib.config.TestContainersConfig;
import com.julianduru.oauthservicelib.config.TestDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * created by julian on 26/04/2022
 */
@Slf4j
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
public abstract class OAuthServiceLibIntegrationTest {


    protected Faker faker = new Faker();


}


