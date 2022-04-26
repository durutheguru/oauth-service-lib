package com.julianduru.oauthservicelib;

import com.julianduru.testlib.BaseServiceIntegrationTest;
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
public abstract class OAuthServiceLibIntegrationTest extends BaseServiceIntegrationTest {





}
