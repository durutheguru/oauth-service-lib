package com.julianduru.oauthservicelib.config;

import lombok.Data;
import org.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * created by julian on 26/04/2022
 */
@Data
@Component
@ConfigurationProperties(
    prefix = "code.config.oauth2.client"
)
public class ClientProperties {


    private String clientId;


    private String clientName;


    private Set<String> redirectUris;


    private Map<String, Object> clientSettings;


    private Map<String, Object> tokenSettings;


    public Map<String, Object> buildMap() {
        var map = new HashMap<String, Object>();

        if (StringUtils.hasText(getClientId())) {
            map.put("clientId", getClientId());
        }

        if (StringUtils.hasText(getClientName())) {
            map.put("clientName", getClientName());
        }

        if (getRedirectUris() != null) {
            map.put("redirectUri", getRedirectUris().stream().toList());
        }

        if (getClientSettings() != null) {
            map.put("clientSettingsMap", new JSONObject(getClientSettings()).toString());
        }

        if (getTokenSettings() != null) {
            map.put("tokenSettingsMap", new JSONObject(getTokenSettings()).toString());
        }

        return map;
    }


}


