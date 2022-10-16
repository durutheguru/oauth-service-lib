package com.julianduru.oauthservicelib.modules.authflow;

import com.julianduru.oauthservicelib.dto.OAuthAccessToken;
import com.julianduru.oauthservicelib.util.WebClientExceptionHandler;
import com.julianduru.util.exception.InvalidClientRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
    public Mono<OAuthAccessToken> authorizeCode(@RequestParam("code") String code) {
        return oAuthFlowService.exchangeAuthorizationCode(code)
            .onErrorResume(WebClientExceptionHandler::handle);
    }


    @PostMapping("/refresh")
    public Mono<OAuthAccessToken> refreshToken(@RequestParam("token") String refreshToken) {
        return oAuthFlowService.refreshToken(refreshToken)
            .onErrorResume(WebClientExceptionHandler::handle);
    }


}

