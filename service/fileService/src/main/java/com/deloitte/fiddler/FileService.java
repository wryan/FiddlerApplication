package com.deloitte.fiddler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FileService {
	public static void main(String[] args) {
		SpringApplication.run(FileService.class, args);
	}
}
