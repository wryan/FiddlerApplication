package com.deloitte.fiddler.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebCaller {
	
	public <T> T getWebEntity(Class<T> inputClass, String url){
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<T> entity = new HttpEntity<>(headers);
		RestTemplate rt = new RestTemplate();
		entity = rt.exchange(url, HttpMethod.GET,
				entity, inputClass);
		return entity.getBody();
		
	}

}
