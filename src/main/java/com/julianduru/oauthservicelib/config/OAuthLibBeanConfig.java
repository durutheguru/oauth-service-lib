package com.julianduru.oauthservicelib.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * created by julian on 26/04/2022
 */
@Configuration
public class OAuthLibBeanConfig {


    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
        final ReactiveClientRegistrationRepository clientRegistrationRepository,
        final ReactiveOAuth2AuthorizedClientService authorizedClientService
    ) {
        var authorizedClientProvider =
            ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        var authorizedClientManager =
            new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientService
            );

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }


    @Bean
    @ConditionalOnMissingBean(name = {"oauthServerGQLWebClient"})
    public WebClient oauthServerGQLWebClient(
        @Value("${code.config.oauth2.authorization-server.gql-base-url}") String oauthServerGQLUrl,
        WebClientOAuthConfigurer webClientOAuthConfigurer
    ) {
        return webClientOAuthConfigurer.configureWebClient(oauthServerGQLUrl);
    }


    @Bean
    public GraphQLWebClient oauthServerGraphQLClient(WebClient oauthServerWebClient) {
        return GraphQLWebClient.newInstance(oauthServerWebClient, new ObjectMapper());
    }


}


