package com.julianduru.oauthservicelib.modules.authflow;

import com.julianduru.oauthservicelib.config.ClientProperties;
import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.util.Base64;
import java.util.List;

/**
 * created by julian on 02/10/2022
 */
@Service
@RequiredArgsConstructor
public class OAuthFlowService {


    @Value("${code.config.oauth2.authorization-server.base-url}")
    private String authServerBaseUrl;


    private final ClientProperties clientProperties;



    public Mono<String> exchangeAuthorizationCode(String authorizationCode) {
        var formData = new LinkedMultiValueMap<String, String>();
        formData.put("grant_type", List.of("authorization_code"));
        formData.put("code", List.of(authorizationCode));
        formData.put("client_id", List.of(clientProperties.getClientId()));
        formData.put("client_secret", List.of(clientProperties.getClientSecret()));
        formData.put("redirect_uri", clientProperties.getRedirectUris().stream().toList());

        return WebClient.builder().baseUrl(authServerBaseUrl)
            .clientConnector(
                new ReactorClientHttpConnector(
                    HttpClient.create()
                        .wiretap(true)
                        .wiretap(
                            "reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL
                        )
                )
            )
            .build()
            .post()
            .uri("/oauth2/token")
            .headers(
                httpHeaders -> {
                    httpHeaders.setBasicAuth(
                        clientProperties.getClientId(),
                        clientProperties.getClientSecret()
                    );
//                    httpHeaders.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(
//                        String.format("")
//                    ));
                    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                }
            )
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(String.class)
            .doOnNext(s -> {
                System.out.println("Auth Server Response: " + s);
            })
            .doOnError(e -> {
                System.out.println("Auth Server Error Response: " + e.getMessage());
            });
    }


}

