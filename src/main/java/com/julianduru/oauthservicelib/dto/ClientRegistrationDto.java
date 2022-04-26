package com.julianduru.oauthservicelib.dto;

import lombok.Data;

import java.util.Set;

/**
 * created by julian on 26/04/2022
 */
@Data
public class ClientRegistrationDto {


    private String id;

    private String clientId;

    private String clientSecret;

    private String clientName;

    private Set<String> redirectUris;


}


