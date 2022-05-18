package com.julianduru.oauthservicelib;

import com.github.javafaker.Faker;
import com.julianduru.oauthservicelib.config.TestContainersConfig;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;

import javax.sql.DataSource;

/**
 * created by julian on 26/04/2022
 */
@Slf4j
@ContextConfiguration(
    classes = {
        TestContainersConfig.class,
        OauthServiceLibAutoConfiguration.class,
    }
)
@ExtendWith({SpringExtension.class})
@SpringBootTest
public abstract class OAuthServiceLibIntegrationTest {


    protected Faker faker = new Faker();



}



