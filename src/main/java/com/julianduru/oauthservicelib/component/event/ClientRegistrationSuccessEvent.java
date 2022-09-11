package com.julianduru.oauthservicelib.component.event;

import com.julianduru.oauthservicelib.dto.ClientRegistrationDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * created by julian on 12/09/2022
 */
@Data
@RequiredArgsConstructor
public class ClientRegistrationSuccessEvent {


    private final ClientRegistrationDto registration;


}
