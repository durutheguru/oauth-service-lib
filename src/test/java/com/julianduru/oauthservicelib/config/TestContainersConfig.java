package com.julianduru.oauthservicelib.config;

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
            Wait.forListeningPort()
                .withStartupTimeout(Duration.ofSeconds(300))
        );
        container.start();

        return container;
    }


    @Bean
    @ConditionalOnProperty(name = "testcontainers.enabled", havingValue = "true")
    public WebClient oauthServerWebClient(DockerComposeContainer dockerComposeContainer) {
        var containerStateOptional = dockerComposeContainer.getContainerByServiceName("oauth-service_1");
        if (containerStateOptional.isPresent()) {
            var containerState = (ContainerState) containerStateOptional.get();
            var oauthServiceIp = containerState.getContainerIpAddress();
            var portBinding = containerState.getPortBindings().get(0);
            var oauthServicePort = Integer.parseInt(portBinding.substring(0, portBinding.indexOf(":")));

            return WebClient.builder().baseUrl(
                    String.format("http://%s:%d/graphql", oauthServiceIp, oauthServicePort)
                )
                .build();
        }

        throw new IllegalStateException("Cannot find docker compose service name: oauth-service");
    }


}

