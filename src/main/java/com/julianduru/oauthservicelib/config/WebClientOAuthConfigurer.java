package com.julianduru.oauthservicelib.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * created by julian on 20/07/2022
 */
@Component
@RequiredArgsConstructor
public class WebClientOAuthConfigurer {


    final ReactiveOAuth2AuthorizedClientManager manager;


    public WebClient configureWebClient(String baseUrl) {
        var exchangeStrategies =
            ExchangeStrategies.builder()
                .codecs(ClientCodecConfigurer::defaultCodecs)
                .build();
        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(manager);
        oauth.setDefaultClientRegistrationId("oauth_service");

        return WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .baseUrl(baseUrl)
            .filter(oauth)
            .build();
    }


}


