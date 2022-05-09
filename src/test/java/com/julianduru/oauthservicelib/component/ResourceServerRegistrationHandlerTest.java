package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.OAuthServiceLibIntegrationTest;
import com.julianduru.oauthservicelib.config.ResourceServerProperties;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;


/**
 * created by julian on 26/04/2022
 */
public class ResourceServerRegistrationHandlerTest extends OAuthServiceLibIntegrationTest {


    @Autowired
    private ResourceServerRegistrationHandler serverRegistrationHandler;


    @Autowired
    private ResourceServerProperties resourceServerProperties;


    @Test
    @Disabled
    public void testResourceServerRegistration() throws Exception {
        resourceServerProperties.setServerId(faker.code().ean13());
        serverRegistrationHandler.registerResourceServer();
    }


}



