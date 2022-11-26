package com.julianduru.oauthservicelib;


import com.julianduru.oauthservicelib.config.TestContainersConfig;
import com.julianduru.oauthservicelib.config.TestDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;


@Slf4j
@ExtendWith({SpringExtension.class})
@SpringBootTest(
    classes = {
        TestContainersConfig.class,
        TestDatabaseConfig.class,
        OauthServiceLibAutoConfiguration.class,
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableAutoConfiguration
public abstract class OAuthServiceLibBaseControllerTest {


    @Autowired
    protected WebTestClient webTestClient;


    @Autowired
    private ReactiveOAuth2AuthorizedClientManager manager;


    private boolean webTestClientInitialized = false;



    @BeforeEach
    public void beforeClass() {
        initializeWebTestClient();
    }


    private void initializeWebTestClient() {
        if (webTestClientInitialized) {
            log.info("Web Test Client already initialized");
            return;
        }

        var exchangeStrategies =
            ExchangeStrategies.builder()
                .codecs(ClientCodecConfigurer::defaultCodecs)
                .build();
        var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(manager);
        oauth.setDefaultClientRegistrationId("oauth_service");

        webTestClient = webTestClient.mutate()
            .filter(oauth)
            .exchangeStrategies(exchangeStrategies)
            .build();

        webTestClientInitialized = true;
    }

}

