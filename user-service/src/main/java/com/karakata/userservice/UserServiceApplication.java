package com.karakata.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
