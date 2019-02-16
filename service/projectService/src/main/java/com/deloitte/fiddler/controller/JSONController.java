package com.deloitte.fiddler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.fiddler.model.ProjectInitializer;
import com.deloitte.fiddler.util.WebCaller;

@RestController
@RequestMapping("/api/json")
public class JSONController {

	WebCaller wc;

	@Autowired
	public JSONController(WebCaller wcL) {
		this.wc = wcL;

	}

	@PostMapping
	public Object getJSON(@RequestBody ProjectInitializer pi) {

		return this.wc.getWebEntity(Object.class, pi.getJsonURL());

	}

}
