package com.julianduru.oauthservicelib.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julianduru.oauthservicelib.dto.OAuthAccessToken;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

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
    public WebClient oauthServerWebClient(
        @Value("${code.config.oauth2.authorization-server.base-url}") String oauthServerUrl,
        WebClientOAuthConfigurer webClientOAuthConfigurer
    ) {
        return webClientOAuthConfigurer.configureWebClient(oauthServerUrl);
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
    public GraphQLWebClient oauthServerGraphQLClient(WebClient oauthServerGQLWebClient) {
        return GraphQLWebClient.newInstance(oauthServerGQLWebClient, new ObjectMapper());
    }


    @Bean
    public WebClient.Builder oauthServerWebClientBuilder(
        @Value("${code.config.oauth2.authorization-server.base-url}") String oauthServerUrl
    ) {
        return WebClient.builder()
            .baseUrl(oauthServerUrl)
            .clientConnector(
                new ReactorClientHttpConnector(
                    HttpClient.create()
                        .wiretap(true)
                        .wiretap(
                            "reactor.netty.http.client.HttpClient",
                            LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL
                        )
                )
            );
    }


}


