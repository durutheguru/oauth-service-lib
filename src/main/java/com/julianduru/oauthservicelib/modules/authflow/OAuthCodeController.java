package com.julianduru.oauthservicelib.modules.authflow;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * created by julian on 02/10/2022
 */
@RestController
@RequestMapping(OAuthCodeController.PATH)
@RequiredArgsConstructor
public class OAuthCodeController {

    public static final String PATH = "/auth/token";


    private final OAuthFlowService oAuthFlowService;


    @PostMapping
    public Mono<String> authorizeCode(@RequestParam("code") String code) {
        return oAuthFlowService.exchangeAuthorizationCode(code);
    }


}

