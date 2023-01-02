package com.julianduru.oauthservicelib.util;

import com.julianduru.util.exception.InvalidClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * created by julian on 08/10/2022
 */
public class WebClientExceptionHandler {


    public static <T> Mono<T> handle(Throwable e) {
        if (e instanceof WebClientResponseException.BadRequest err) {
            return Mono.error(new InvalidClientRequestException("Invalid Request", err));
        }

        return Mono.error(e);
    }


}
