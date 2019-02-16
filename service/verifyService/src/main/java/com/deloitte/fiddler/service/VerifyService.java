package com.deloitte.fiddler.service;

public interface VerifyService {
	
	public <T> T validateJSON(Class<T> t, String url);

}
