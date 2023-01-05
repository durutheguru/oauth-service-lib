package com.julianduru.oauthservicelib.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * created by julian on 03/10/2022
 */
@Data
public class OAuthAccessToken {


    @JsonProperty("access_token")
    private String accessToken;


    @JsonProperty("refresh_token")
    private String refreshToken;


    private String scope;


    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

}

