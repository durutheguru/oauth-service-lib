package com.julianduru.oauthservicelib.data;

import com.julianduru.oauthservicelib.modules.user.dto.UserDataUpdate;
import com.julianduru.util.test.DataProvider;
import org.springframework.stereotype.Component;

/**
 * created by julian on 28/10/2022
 */
@Component
public class UserDataUpdateProvider implements DataProvider<UserDataUpdate> {


    @Override
    public UserDataUpdate provide() {
        return new UserDataUpdate(
            faker.code().isbn10(false),
            faker.name().firstName(),
            faker.name().lastName(),
            faker.internet().emailAddress()
        );
    }


}
