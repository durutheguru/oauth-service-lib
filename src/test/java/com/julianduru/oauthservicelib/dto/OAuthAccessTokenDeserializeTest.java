package com.julianduru.oauthservicelib.dto;

import com.julianduru.util.JSONUtil;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by julian on 03/10/2022
 */

public class OAuthAccessTokenDeserializeTest {


    @Test
    public void testOAuthAccessTokenDeserialization() throws Exception {
        var token = JSONUtil.fromJsonString(
            """
                {
                    "access_token": "skjnsdkjnksdjnfkds",
                    "refresh_token": "skjsjnsdkjdsn",
                    "scope": "read",
                    "token_type": "Bearer",
                    "expires_in": 599
                }
                """,
            OAuthAccessToken.class
        );

        assertThat(token.getAccessToken()).isNotBlank();
    }

}
