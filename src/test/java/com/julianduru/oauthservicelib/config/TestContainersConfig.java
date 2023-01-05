package com.julianduru.oauthservicelib.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * created by julian on 09/05/2022
 */
@Slf4j
@TestConfiguration
@ConditionalOnProperty(name = "testcontainers.enabled", havingValue = "true")
public class TestContainersConfig {


//    @Bean
//    public DockerComposeContainer dockerComposeContainer() {
//        var container = new DockerComposeContainer<>(
//            new File("src/test/resources/docker-compose.yml")
//        )
//        .withExposedService(
//            "oauth-service_1", 10101,
//            Wait.forHttp("/")
//                .forStatusCodeMatching(code -> code >= 200 && code <= 500)
//                .withStartupTimeout(Duration.ofSeconds(600))
//        );
//        container.start();
//
//        return container;
//    }
//
//
//    @Bean
//    public WebClient oauthServerGQLWebClient(
//        DockerComposeContainer dockerComposeContainer
//        , WebClientOAuthConfigurer webClientOAuthConfigurer
//    ) {
//        String oauthServiceUrl =
//            String.format(
//                "%s:%s/graphql",
//                dockerComposeContainer.getServiceHost("oauth-service_1", 10101),
//                dockerComposeContainer.getServicePort("oauth-service_1", 10101)
//            );
//
//        log.info("OAuth Service URL: {}", oauthServiceUrl);
//
//        return webClientOAuthConfigurer.configureWebClient(oauthServiceUrl);
//    }


//    @Bean
//    public DataSource dataSource(DockerComposeContainer dockerComposeContainer) {
//        if (dockerComposeContainer == null) {
//            log.warn("Docker Compose Container not found.");
//            return null;
//        }
//
//        var containerStateOptional = dockerComposeContainer.getContainerByServiceName("mysqldb_1");
//        if (containerStateOptional.isPresent()) {
//            var containerState = (ContainerState) containerStateOptional.get();
//            var oauthServiceIp = containerState.getContainerIpAddress();
//            var portBinding = containerState.getPortBindings().get(0);
//            var oauthServicePort = Integer.parseInt(portBinding.substring(0, portBinding.indexOf(":")));
//
//            var dataSource = new MysqlDataSource();
//            dataSource.setURL(
//                String.format(
//                    "jdbc:mysql://%s:%d/oauth_service?createDatabaseIfNotExist=true",
//                    oauthServiceIp, oauthServicePort
//                )
//            );
//            dataSource.setUser("root");
//            dataSource.setPassword("1234567890");
//
//            return dataSource;
//        }
//
//        throw new IllegalStateException("Cannot find docker compose service name: mysqldb");
//    }

    @Bean
    public DataSource dataSource(
        @Value("${code.config.oauth2.authorization-server.db.ip:0.0.0.0}") String oauthDbIp,
        @Value("${code.config.oauth2.authorization-server.db.port:1111}") Integer oauthDbPort
    ) {
        var dataSource = new MysqlDataSource();
        dataSource.setURL(
            String.format(
                "jdbc:mysql://%s:%d/oauth_service?createDatabaseIfNotExist=true",
                oauthDbIp, 33080
            )
        );
        dataSource.setUser("root");
        dataSource.setPassword("1234567890");

        log.info("Database Connection Properties: {}, {}", oauthDbIp, oauthDbPort);

        return dataSource;
    }


}



