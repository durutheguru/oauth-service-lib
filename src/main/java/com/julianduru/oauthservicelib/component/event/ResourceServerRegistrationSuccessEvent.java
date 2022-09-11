package com.julianduru.oauthservicelib.component.event;

import com.julianduru.oauthservicelib.dto.ResourceServerRegistrationDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * created by julian on 12/09/2022
 */
@Data
@RequiredArgsConstructor
public class ResourceServerRegistrationSuccessEvent {


    private final ResourceServerRegistrationDto registration;


}
