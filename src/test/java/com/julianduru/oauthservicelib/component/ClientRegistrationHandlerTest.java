package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.OAuthServiceLibIntegrationTest;
import com.julianduru.oauthservicelib.config.ClientProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 26/04/2022
 */
@Scope
public class ClientRegistrationHandlerTest extends OAuthServiceLibIntegrationTest {


    @Autowired
    private ClientRegistrationHandler clientRegistrationHandler;


    @Autowired
    private ResourceServerRegistrationHandler serverRegistrationHandler;


    @Autowired
    private ClientProperties clientProperties;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void testClientRegistration() throws Exception {
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

