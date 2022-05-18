package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.config.ClientProperties;
import com.julianduru.oauthservicelib.dto.ClientRegistrationDto;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * created by julian on 26/04/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientRegistrationHandler {


    private final GraphQLWebClient oauthServerGraphQLClient;


    private final ClientProperties clientProperties;


    public void registerClient() throws Exception {
        var request = GraphQLRequest.builder()
            .resource(
                "graphql/request/client.registration.request"
            )
            .variables(clientProperties.buildMap())
            .build();

        var response = oauthServerGraphQLClient.post(request).blockOptional();

        if (response.isPresent()) {
            var gqlResponse = response.get();
            if (!gqlResponse.getErrors().isEmpty()) {
                log.error(gqlResponse.getErrors().get(0).toString(), new Throwable("Error while registering Client"));
            }
            else {
                log.info("Deserialized response: {}", gqlResponse.getFirst(ClientRegistrationDto.class));
            }
        }
        else {
            log.info("No Response received");
        }
    }


}

