package com.julianduru.oauthservicelib.modules.user;

import com.julianduru.oauthservicelib.modules.user.dto.UserDataUpdate;
import com.julianduru.util.api.OperationStatus;

/**
 * created by julian on 28/10/2022
 */
public interface UserDataService {


    OperationStatus<String> processOAuthUserDataUpdate(UserDataUpdate userDataUpdate);


}
