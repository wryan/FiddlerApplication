package com.deloitte.fiddler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.fiddler.service.VerifyService;
import com.deloitte.fiddler.util.JSONvalidator;
import com.deloitte.fiddler.util.WebCaller;

@Service
public class VerifyServiceImpl implements VerifyService {
	
	JSONvalidator jsv;
	WebCaller wc;
	
	@Autowired
	public VerifyServiceImpl(WebCaller wcL, JSONvalidator jsvL) {
		this.wc = wcL;
		this.jsv = jsvL;

	}

	@Override
	public <T> T validateJSON(Class<T> t, String url) {
		this.jsv.validateJSON(t, wc.getWebEntity(Object.class, url));
		return wc.getWebEntity(t, url);

	}

}
