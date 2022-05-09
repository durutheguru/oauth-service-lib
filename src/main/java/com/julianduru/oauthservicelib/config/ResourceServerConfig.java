package com.julianduru.oauthservicelib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.ArrayList;
import java.util.List;

/**
 * created by julian on 25/04/2022
 */
@Configuration
@ConditionalOnProperty(
    name = "code.config.oauth2.resource-server.enabled", havingValue = "true"
)
@EnableWebFluxSecurity
public class ResourceServerConfig {


    @Value("${code.config.oauth2.resource-server.serverId}")
    private String resourceServerId;


    @Value("${code.config.oauth2.resource-server.jwk-set-uri}")
    private String jwkSetUri;


    @Bean
    @Primary
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, ReactiveJwtDecoder jwtDecoder) throws Exception {
        http.authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt(
                customizer -> customizer.jwkSetUri(jwkSetUri).jwtDecoder(jwtDecoder)
            );

        return http.build();
    }


    @Bean
    @Primary
    public ReactiveJwtDecoder jwtDecoder() {
        var decoder = NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();

        List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
        validators.add(new JwtTimestampValidator());
        validators.add(
            new JwtClaimValidator<List<String>>(
                JwtClaimNames.AUD, (audList) -> audList != null && audList.contains(resourceServerId)
            )
        );

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(validators));

        return decoder;
    }


}



