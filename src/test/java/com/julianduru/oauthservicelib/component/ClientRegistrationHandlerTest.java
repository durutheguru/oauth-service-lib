package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.OAuthServiceLibIntegrationTest;
import com.julianduru.oauthservicelib.config.ClientProperties;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * created by julian on 26/04/2022
 */
public class ClientRegistrationHandlerTest extends OAuthServiceLibIntegrationTest {


    @Autowired
    private ClientRegistrationHandler registrationHandler;


    @Autowired
    private ClientProperties clientProperties;


    @Test
    @Disabled
    public void testClientRegistration() throws Exception {
        clientProperties.setClientName(faker.name().fullName() + " Client");
        registrationHandler.registerClient();
    }


}

