package com.deloitte.fiddler.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;

@Service
public class WebCaller {

	public <T> T getWebEntity(Class<T> inputClass, String url) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
		messageConverters.add(converter);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<T> entity = new HttpEntity<>(headers);
		RestTemplate rt = new RestTemplate();
		rt.setMessageConverters(messageConverters);
		entity = rt.exchange(url, HttpMethod.GET, entity, inputClass);
		return entity.getBody();

	}

}
