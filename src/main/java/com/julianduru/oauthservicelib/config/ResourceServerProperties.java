package com.julianduru.oauthservicelib.config;

import lombok.Data;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * created by julian on 26/04/2022
 */
@Data
@Component
@ConfigurationProperties(
    prefix = "code.config.oauth2.resource-server"
)
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ResourceServerProperties {


    @NotEmpty(message = "Resource Server ID is required in configuration")
    private String serverId;


    @NotEmpty(message = "Resource Server Scopes are required in configuration")
    private Set<String> allowedScopes;


    private boolean autoRegister;


}

