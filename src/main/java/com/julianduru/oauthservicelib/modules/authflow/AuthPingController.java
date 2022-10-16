package com.julianduru.oauthservicelib.modules.authflow;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by julian
 */
@RestController
@RequestMapping("/api/auth")
public class AuthPingController {


    @GetMapping
    public void ping() { }


}

