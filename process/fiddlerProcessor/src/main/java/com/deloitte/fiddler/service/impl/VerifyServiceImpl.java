package com.deloitte.fiddler.service.impl;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deloitte.fiddler.service.VerifyService;

@Component
public class VerifyServiceImpl implements VerifyService {


	RestTemplate restTemplate;

	Environment env;
	
	DiscoveryClient discoveryClient;
	
	@Autowired
	public VerifyServiceImpl(Environment envL, DiscoveryClient discoveryClientL) {
		this.restTemplate = new RestTemplate();
		this.env = envL;
		this.discoveryClient = discoveryClientL;

	}

	@Override
	public Object validateJSON(String clazz, String url) {
		System.out.println(URLEncoder.encode(this.env.getProperty("fiddler.services.verify.host")));
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.verify.host")
				+ this.env.getProperty("fiddler.services.verify.endpoints.verify")
				+ clazz,
		url, Object.class).getBody();
	}

}
