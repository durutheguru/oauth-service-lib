package com.julianduru.oauthservicelib.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * created by julian on 26/04/2022
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
    name = "code.config.oauth2.client.autoRegister", havingValue = "true"
)
public class ClientRegistrationBootstrapper implements ApplicationRunner {


    private final ClientRegistrationHandler registrationHandler;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        registrationHandler.registerClient();
    }


}

