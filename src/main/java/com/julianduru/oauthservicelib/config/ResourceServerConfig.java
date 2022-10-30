package com.julianduru.oauthservicelib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, ReactiveJwtDecoder jwtDecoder) throws Exception {
        http.authorizeExchange()
            .pathMatchers("/auth/token", "/auth/token/refresh")
            .permitAll()
            .and()
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt(
                customizer -> customizer.jwkSetUri(jwkSetUri).jwtDecoder(jwtDecoder)
            )
            .and().cors().and().csrf().disable();

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


    @Bean
    public CorsConfigurationSource corsConfiguration(
        @Value("${code.config.web.cors.allowed-origins}") String allowedOrigins
    ) {
        var corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("PATCH");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("OPTIONS");
        corsConfig.setAllowedOrigins(List.of(allowedOrigins.split("\\s*,\\s*")));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }


    @Bean
    public CorsWebFilter corsWebFilter(CorsConfigurationSource corsConfiguration) {
        return new CorsWebFilter(corsConfiguration);
    }


}



