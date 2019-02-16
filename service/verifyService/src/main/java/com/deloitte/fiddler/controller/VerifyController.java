package com.deloitte.fiddler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.fiddler.model.JSONwrapper;
import com.deloitte.fiddler.service.VerifyService;



@RestController
@RequestMapping("/api/verify")
public class VerifyController {
	
	@Autowired
	VerifyService vs;

	@PostMapping("/{clazz}")
	public Object validateJSON(@RequestBody JSONwrapper json, @PathVariable String clazz) throws ClassNotFoundException {
		return this.vs.validateJSON(Class.forName("com.deloitte.fiddler.common." + clazz), json.getJsonURL());
	}

}
