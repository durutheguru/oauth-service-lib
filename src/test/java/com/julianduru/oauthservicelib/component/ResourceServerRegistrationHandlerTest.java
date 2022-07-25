package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.OAuthServiceLibIntegrationTest;
import com.julianduru.oauthservicelib.config.ResourceServerProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * created by julian on 26/04/2022
 */
public class ResourceServerRegistrationHandlerTest extends OAuthServiceLibIntegrationTest {


    @Autowired
    private ResourceServerRegistrationHandler serverRegistrationHandler;


    @Autowired
    private ResourceServerProperties resourceServerProperties;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void testResourceServerRegistration() throws Exception {
        serverRegistrationHandler.registerResourceServer();

        var rowCount = JdbcTestUtils.countRowsInTableWhere(
            jdbcTemplate,
            "resource_server",
            String.format("resource_server_id='%s'", resourceServerProperties.getServerId())
        );

        assertThat(rowCount).isEqualTo(1);
    }


}


