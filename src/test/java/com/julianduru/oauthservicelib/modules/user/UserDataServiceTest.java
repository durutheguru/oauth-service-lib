package com.julianduru.oauthservicelib.modules.user;

import com.julianduru.oauthservicelib.OAuthServiceLibIntegrationTest;
import com.julianduru.oauthservicelib.config.TestKafkaConsumer;
import com.julianduru.oauthservicelib.data.UserDataUpdateProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * created by julian on 30/10/2022
 */
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092", "port=9092"
    }
)
public class UserDataServiceTest extends OAuthServiceLibIntegrationTest {


    @Autowired
    private UserDataService userDataService;


    @Autowired
    private UserDataUpdateProvider userDataUpdateProvider;


    @Autowired
    private TestKafkaConsumer testKafkaConsumer;



    @Test
    public void testProcessOAuthUserDataUpdate() throws Exception {
        var update = userDataUpdateProvider.provide();

        userDataService.processOAuthUserDataUpdate(update);
        boolean messageConsumed = testKafkaConsumer.getLatch().await(2, TimeUnit.SECONDS);
        assertFalse(messageConsumed);
    }


}
