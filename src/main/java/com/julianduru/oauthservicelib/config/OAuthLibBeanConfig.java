package com.julianduru.oauthservicelib.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
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
    @ConditionalOnMissingBean(name = {"oauthServerWebClient"})
    public WebClient oauthServerWebClient(
        @Value("${code.config.oauth2.authorization-server.gql-base-url}") String oauthServerUrl,
        @Qualifier("authorizedClientManager") final ReactiveOAuth2AuthorizedClientManager manager
    ) {
        var exchangeStrategies =
            ExchangeStrategies.builder()
                .codecs(ClientCodecConfigurer::defaultCodecs)
                .build();
        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(manager);
        oauth.setDefaultClientRegistrationId("oauth_service");

        return WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .baseUrl(oauthServerUrl)
            .filter(oauth)
            .build();
    }


//    @Bean
//    @ConditionalOnMissingBean(name = {"oauthServerWebClient"})
//    public WebClient oauthServerWebClient(
//        @Value("${code.config.oauth2.authorization-server.gql-base-url}") String oauthServerUrl
//    ) {
//        return WebClient.builder()
//            .baseUrl(oauthServerUrl)
//            .build();
//    }


    @Bean
    public GraphQLWebClient oauthServerGraphQLClient(WebClient oauthServerWebClient) {
        return GraphQLWebClient.newInstance(oauthServerWebClient, new ObjectMapper());
    }


}


