package com.julianduru.oauthservicelib.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * created by julian on 26/04/2022
 */
@Configuration
public class BeanConfig {


    @Bean
    public WebClient oauthServerWebClient(
        @Value("${code.config.oauth2.authorization-server.gql-base-url}") String oauthServerUrl
    ) {
        return WebClient.builder().baseUrl(oauthServerUrl)
            .build();
    }


    @Bean
    public GraphQLWebClient oauthServerGraphQLClient(WebClient oauthServerWebClient) {
        return GraphQLWebClient.newInstance(oauthServerWebClient, new ObjectMapper());
    }


}

