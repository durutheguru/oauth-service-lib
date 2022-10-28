package com.julianduru.oauthservicelib.modules.user.controller;

import com.julianduru.oauthservicelib.OAuthServiceLibBaseControllerTest;
import com.julianduru.oauthservicelib.data.UserDataUpdateProvider;
import com.julianduru.util.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

/**
 * created by julian on 28/10/2022
 */
public class UserDataControllerTest extends OAuthServiceLibBaseControllerTest {


    @Autowired
    private UserDataUpdateProvider userDataUpdateProvider;


    @Test
    public void testWritingUserSaveDataRecord() throws Exception {
        var dataUpdate = userDataUpdateProvider.provide();

        webTestClient
            .post()
            .uri(UserDataController.PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(JSONUtil.asJsonString(dataUpdate))
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody()
            .consumeWith(System.out::println);
    }


}

