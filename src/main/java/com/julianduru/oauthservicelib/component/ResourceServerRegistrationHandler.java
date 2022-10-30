package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.component.event.ResourceServerRegistrationSuccessEvent;
import com.julianduru.oauthservicelib.config.ResourceServerProperties;
import com.julianduru.oauthservicelib.dto.ResourceServerRegistrationDto;
import graphql.kickstart.spring.webclient.boot.GraphQLErrorsException;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * created by julian on 26/04/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceServerRegistrationHandler {


    private final GraphQLWebClient oauthServerGraphQLClient;


    private final ResourceServerProperties resourceServerProperties;


    private final ApplicationEventPublisher eventPublisher;



    public void registerResourceServer() throws Exception {
        var request = GraphQLRequest.builder()
            .query(
                """
                    mutation RegisterResourceServer(
                        $serverId: String!,
                        $allowedScopes: [String]!,
                        $userAuthoritiesOnSignUp: [String]
                    ) {
                        registerResourceServer(server: {
                            serverId: $serverId,
                            allowedScopes: $allowedScopes,
                            userAuthoritiesOnSignUp: $userAuthoritiesOnSignUp
                        }) {
                            id
                            resourceServerId
                            allowedScopes
                            status
                        }
                    }
                """
            )
            .variables(
                Map.of(
                    "serverId", resourceServerProperties.getServerId(),
                    "allowedScopes", resourceServerProperties.getAllowedScopes().stream().toList(),
                    "userAuthoritiesOnSignUp", resourceServerProperties.getUserAuthoritiesOnSignUp().stream().toList()
                )
            )
            .build();

        var response = oauthServerGraphQLClient.post(request).blockOptional();
        if (response.isPresent()) {
            var responseData = readResponse(response.get());
            responseData.ifPresent(
                data -> eventPublisher.publishEvent(new ResourceServerRegistrationSuccessEvent(data))
            );
        }
        else {
            throw new IllegalStateException("No Response received");
        }
    }


    private Optional<ResourceServerRegistrationDto> readResponse(GraphQLResponse gqlResponse) {
        try {
            gqlResponse.validateNoErrors();

            var responseData = gqlResponse.getFirst(ResourceServerRegistrationDto.class);
            log.info("Deserialized response: {}", responseData);

            return Optional.of(responseData);
        }
        catch (GraphQLErrorsException t) {
            log.warn(t.getMessage(), t);
            return Optional.empty();
        }
    }


}

