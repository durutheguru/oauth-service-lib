package com.julianduru.oauthservicelib;

import com.julianduru.oauthservicelib.config.ClientProperties;
import com.julianduru.oauthservicelib.config.ResourceServerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
	prefix = "code.auto-configure.oauth-service-lib",
	name = "enabled",
	havingValue = "true",
	matchIfMissing = true
)
@ComponentScan(
	basePackages = {
		"com.julianduru.oauthservicelib",
	}
)
@EnableConfigurationProperties({
	ClientProperties.class,
	ResourceServerProperties.class
})
public class OauthServiceLibAutoConfiguration {


}


