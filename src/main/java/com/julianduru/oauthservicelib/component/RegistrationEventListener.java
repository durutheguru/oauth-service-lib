package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.component.event.ClientRegistrationSuccessEvent;
import com.julianduru.oauthservicelib.component.event.ResourceServerRegistrationSuccessEvent;
import com.julianduru.oauthservicelib.dto.ClientRegistrationDto;
import com.julianduru.oauthservicelib.dto.ResourceServerRegistrationDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

/**
 * created by julian on 12/09/2022
 */
@Component
@Getter
@RequiredArgsConstructor
public class RegistrationEventListener {


    @Value("${code.config.oauth2.authorization-server.base-url}")
    private String authServerBaseUrl;


    private final ReactiveClientRegistrationRepository clientRegistrationRepository;


    private ClientRegistrationDto clientRegistration;


    private ResourceServerRegistrationDto resourceServerRegistration;



    @EventListener
    public void handleResourceServerRegistration(ResourceServerRegistrationSuccessEvent event) {
        resourceServerRegistration = event.getRegistration();
    }


    @EventListener
    public void handleClientRegistration(ClientRegistrationSuccessEvent event) {
        clientRegistration = event.getRegistration();
        if (clientRegistrationRepository instanceof MutatingReactiveClientRegistrationRepository) {
            ((MutatingReactiveClientRegistrationRepository)clientRegistrationRepository)
                .addClientRegistration(
                    ClientRegistration.withRegistrationId(clientRegistration.getClientId())
                        .clientId(clientRegistration.getClientId())
                        .clientSecret(clientRegistration.getClientSecret())
                        .clientName(clientRegistration.getClientName())
                        .redirectUri(clientRegistration.getRedirectUris().stream().findFirst().orElse(""))
                        .scope("openid")
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .authorizationUri(authServerBaseUrl + "/oauth2/authorize")
                        .tokenUri(authServerBaseUrl + "/oauth2/token")
                        .jwkSetUri(authServerBaseUrl + "/oauth2/jwks")
                        .build()
                );
        }
    }


}

