package com.julianduru.oauthservicelib.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * created by julian on 26/04/2022
 */
@Data
@ToString
public class ResourceServerRegistrationDto {


    private Long id;


    private String resourceServerId;


    private Set<String> allowedScopes;


    private String status;


}
