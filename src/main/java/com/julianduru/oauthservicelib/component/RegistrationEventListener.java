package com.julianduru.oauthservicelib.component;

import com.julianduru.oauthservicelib.component.event.ClientRegistrationSuccessEvent;
import com.julianduru.oauthservicelib.component.event.ResourceServerRegistrationSuccessEvent;
import com.julianduru.oauthservicelib.dto.ClientRegistrationDto;
import com.julianduru.oauthservicelib.dto.ResourceServerRegistrationDto;
import lombok.Getter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * created by julian on 12/09/2022
 */
@Component
@Getter
public class RegistrationEventListener {


    private ClientRegistrationDto clientRegistration;


    private ResourceServerRegistrationDto resourceServerRegistration;



    @EventListener
    public void handleClientRegistration(ClientRegistrationSuccessEvent event) {
        clientRegistration = event.getRegistration();
    }


    @EventListener
    public void handleResourceServerRegistration(ResourceServerRegistrationSuccessEvent event) {
        resourceServerRegistration = event.getRegistration();
    }


}

