package com.deloitte.fiddler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deloitte.fiddler.service.VerifyService;

@Component
public class VerifyServiceImpl implements VerifyService {


	RestTemplate restTemplate;

	Environment env;
	
	@Autowired
	public VerifyServiceImpl(Environment envL) {
		this.restTemplate = new RestTemplate();
		this.env = envL;

	}

	@Override
	public Object validateJSON(String clazz, String url) {
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.verify.host")
				+ this.env.getProperty("fiddler.services.verify.endpoints.verify")
				+ clazz,
		url, Object.class).getBody();
	}

}
