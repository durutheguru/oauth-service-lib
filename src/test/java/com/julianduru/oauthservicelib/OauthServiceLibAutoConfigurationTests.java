package com.julianduru.oauthservicelib;

import com.julianduru.oauthservicelib.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
	classes = {
		TestContainersConfig.class,
		OauthServiceLibAutoConfiguration.class,
	}
)
@EnableConfigurationProperties({DataSourceProperties.class})
class OauthServiceLibAutoConfigurationTests {

	@Test
	void contextLoads() {
	}

}
