package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.OAuthServiceLibIntegrationTest;
import com.julianduru.oauthservicelib.config.ClientProperties;
import com.julianduru.oauthservicelib.config.ResourceServerProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 26/04/2022
 */
public class ClientRegistrationHandlerTest extends OAuthServiceLibIntegrationTest {


    @Autowired
    private ClientRegistrationHandler clientRegistrationHandler;


    @Autowired
    private ResourceServerRegistrationHandler serverRegistrationHandler;


    @Autowired
    private ResourceServerProperties resourceServerProperties;


    @Autowired
    private ClientProperties clientProperties;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void testClientRegistration() throws Exception {
        resourceServerProperties.setServerId(faker.code().isbn13());
        clientProperties.setClientId(faker.code().isbn13());
        clientProperties.setClientName(clientProperties.getClientName() + faker.code().isbn10());

        serverRegistrationHandler.registerResourceServer();
        clientRegistrationHandler.registerClient();

        var rowCount = JdbcTestUtils.countRowsInTableWhere(
            jdbcTemplate,
            "oauth2_registered_client",
            String.format("client_name='%s'", clientProperties.getClientName())
        );

        assertThat(rowCount).isEqualTo(1);
    }


}


