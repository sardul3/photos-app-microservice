package com.example.photosappdiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class PhotosAppDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotosAppDiscoveryServiceApplication.class, args);
	}

}
