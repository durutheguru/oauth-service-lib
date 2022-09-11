package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.component.event.ClientRegistrationSuccessEvent;
import com.julianduru.oauthservicelib.config.ClientProperties;
import com.julianduru.oauthservicelib.dto.ClientRegistrationDto;
import graphql.kickstart.spring.webclient.boot.GraphQLErrorsException;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * created by julian on 26/04/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientRegistrationHandler {


    private final GraphQLWebClient oauthServerGraphQLClient;


    private final ClientProperties clientProperties;


    private final ApplicationEventPublisher eventPublisher;


    public void registerClient() throws Exception {
        var request = GraphQLRequest.builder()
            .resource(
                "graphql/request/client.registration.request"
            )
            .variables(clientProperties.buildMap())
            .build();

        var response = oauthServerGraphQLClient.post(request).blockOptional();

        if (response.isPresent()) {
            var responseData = readResponse(response.get());
            responseData.ifPresent(
                data -> eventPublisher.publishEvent(new ClientRegistrationSuccessEvent(data))
            );
        }
        else {
            log.info("No Response received");
        }
    }


    private Optional<ClientRegistrationDto> readResponse(GraphQLResponse gqlResponse) {
        try {
            gqlResponse.validateNoErrors();

            var responseData = gqlResponse.getFirst(ClientRegistrationDto.class);
            log.info("Deserialized response: {}", responseData);

            return Optional.of(responseData);
        }
        catch (GraphQLErrorsException t) {
            log.warn(t.getMessage(), t);
            return Optional.empty();
        }
    }


}

