package com.julianduru.oauthservicelib.modules.user;

import com.julianduru.oauthservicelib.modules.user.dto.UserDataUpdate;
import com.julianduru.util.api.OperationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * created by julian on 28/10/2022
 */
@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {


    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Value("${code.config.kafka.oauth-service.topic-name}")
    private String oauthServiceLogsTopicName;


    @Override
    public OperationStatus<String> processOAuthUserDataUpdate(UserDataUpdate userDataUpdate) {
        kafkaTemplate.send(oauthServiceLogsTopicName, UUID.randomUUID().toString(), userDataUpdate);
        return OperationStatus.success();
    }


}



