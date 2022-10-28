package com.julianduru.oauthservicelib.modules.user.controller;

import com.julianduru.oauthservicelib.OAuthLibConstants;
import com.julianduru.oauthservicelib.modules.user.UserDataService;
import com.julianduru.oauthservicelib.modules.user.dto.UserDataUpdate;
import com.julianduru.util.api.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by julian on 28/10/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(UserDataController.PATH)
public class UserDataController {


    public final static String PATH = OAuthLibConstants.API_BASE + "/user_data";


    private final UserDataService userDataService;



    @PostMapping
    public OperationStatus<String> updateUserData(@RequestBody UserDataUpdate dataUpdate) {
        return userDataService.processOAuthUserDataUpdate(dataUpdate);
    }


}

