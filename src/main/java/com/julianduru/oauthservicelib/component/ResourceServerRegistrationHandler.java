package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.config.ResourceServerProperties;
import com.julianduru.oauthservicelib.dto.ResourceServerRegistrationDto;
import graphql.kickstart.spring.webclient.boot.GraphQLErrorsException;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 26/04/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceServerRegistrationHandler {


    private final GraphQLWebClient oauthServerGraphQLClient;


    private final ResourceServerProperties resourceServerProperties;


    public void registerResourceServer() throws Exception {
        var request = GraphQLRequest.builder()
            .query(
                """
                    mutation RegisterResourceServer($serverId: String!, $allowedScopes: [String]!) {
                        registerResourceServer(server: {
                            serverId: $serverId,
                            allowedScopes: $allowedScopes
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
                    "allowedScopes", resourceServerProperties.getAllowedScopes().stream().toList()
                )
            )
            .build();

        var response = oauthServerGraphQLClient.post(request).blockOptional();
        if (response.isPresent()) {
            readResponse(response.get());
        }
        else {
            throw new IllegalStateException("No Response received");
        }
    }


    private void readResponse(GraphQLResponse gqlResponse) {
        try {
            gqlResponse.validateNoErrors();

            var responseData = gqlResponse.getFirst(ResourceServerRegistrationDto.class);
            log.info("Deserialized response: {}", responseData);
        }
        catch (GraphQLErrorsException t) {
            log.warn(t.getMessage(), t);
        }
    }


}

