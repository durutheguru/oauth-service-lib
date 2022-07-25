package com.julianduru.oauthservicelib.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

/**
 * created by julian on 09/05/2022
 */
@Slf4j
@TestConfiguration
public class TestContainersConfig {


    @Bean
    @ConditionalOnProperty(name = "testcontainers.enabled", havingValue = "true")
    public DockerComposeContainer dockerComposeContainer() {
        var container = new DockerComposeContainer<>(
            new File("src/test/resources/docker-compose.yml")
        )
        .withExposedService(
            "oauth-service_1", 10101,
            Wait.forHttp("/")
                .forStatusCodeMatching(code -> code >= 200 && code <= 500)
                .withStartupTimeout(Duration.ofSeconds(600))
        );
        container.start();

        return container;
    }


    @Bean
    @ConditionalOnProperty(name = "testcontainers.enabled", havingValue = "true")
    public WebClient oauthServerGQLWebClient(
        DockerComposeContainer dockerComposeContainer
        , WebClientOAuthConfigurer webClientOAuthConfigurer
    ) {
        String oauthServiceUrl =
            String.format(
                "%s:%s/graphql",
                dockerComposeContainer.getServiceHost("oauth-service_1", 10101),
                dockerComposeContainer.getServicePort("oauth-service_1", 10101)
            );

        log.info("OAuth Service URL: {}", oauthServiceUrl);

        return webClientOAuthConfigurer.configureWebClient(oauthServiceUrl);
    }


}



