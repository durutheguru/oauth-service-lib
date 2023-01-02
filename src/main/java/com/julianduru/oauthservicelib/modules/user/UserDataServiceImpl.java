package com.julianduru.oauthservicelib.modules.user;

import com.julianduru.data.messaging.dto.UserDataUpdate;
import com.julianduru.kafkaintegrationlib.Writer;
import com.julianduru.util.api.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * created by julian on 28/10/2022
 */
@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {


    private final Writer writer;


    @Value("${code.config.kafka.oauth-service.topic-name}")
    private String oauthServiceLogsTopicName;


    @Override
    public OperationStatus<String> processOAuthUserDataUpdate(UserDataUpdate userDataUpdate) {
        writer.write(oauthServiceLogsTopicName, UUID.randomUUID().toString(), userDataUpdate);
        return OperationStatus.success();
    }


}



