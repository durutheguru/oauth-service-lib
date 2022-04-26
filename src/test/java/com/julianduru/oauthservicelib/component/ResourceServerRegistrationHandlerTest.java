package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.OauthServiceLibAutoConfiguration;
import com.julianduru.oauthservicelib.config.ResourceServerProperties;
import com.julianduru.testlib.BaseServiceIntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;


/**
 * created by julian on 26/04/2022
 */
@ContextConfiguration(
    classes = {
        OauthServiceLibAutoConfiguration.class,
    }
)
@EnableAutoConfiguration
public class ResourceServerRegistrationHandlerTest extends BaseServiceIntegrationTest {


    @Autowired
    private ResourceServerRegistrationHandler serverRegistrationHandler;


    @Autowired
    private ResourceServerProperties resourceServerProperties;


    @Test
//    @Disabled
    public void testResourceServerRegistration() throws Exception {
        resourceServerProperties.setServerId(faker.code().ean13());
        serverRegistrationHandler.registerResourceServer();
    }


}
