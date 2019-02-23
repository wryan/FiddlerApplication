package com.deloitte.fiddler.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.common.TeamMemberObject;
import com.deloitte.fiddler.service.PersonService;

@Component
public class PersonServiceImpl implements PersonService {

	RestTemplate restTemplate;

	Environment env;

	@Autowired
	public PersonServiceImpl(Environment envL) {
		this.restTemplate = new RestTemplate();
		this.env = envL;

	}


	public TeamMemberObject getPersonById(String id) throws NoSuchElementException {
		return this.restTemplate.getForObject(
				this.env.getProperty("fiddler.services.person.host")
						+ this.env.getProperty("fiddler.services.person.endpoints.get") + id,
						TeamMemberObject.class);
	}

	public boolean deletePerson(String id) {
		return this.restTemplate.postForObject(this.env.getProperty("fiddler.services.person.host")
				+ this.env.getProperty("fiddler.services.person.endpoints.delete") + id + "/delete", null, boolean.class);

	}


	public TeamMemberObject createPerson(String t) {

		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.person.host") + 
				this.env.getProperty("fiddler.services.person.endpoints.create"),
						this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.verify.host")
												+ this.env.getProperty("fiddler.services.verify.endpoints.verify")
												+ "TeamMemberObject",
										t, TeamMemberObject.class)
								.getBody(),
								TeamMemberObject.class)
				.getBody();

		
	}

	@Override
	public TeamMemberObject updatePerson(TeamMemberObject p){
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<TeamMemberObject> requestEntity = new HttpEntity<TeamMemberObject>(
				p);
		return restTemplate.exchange(
				this.env.getProperty("fiddler.services.person.host")
						+ this.env.getProperty("fiddler.services.person.endpoints.update"),
				HttpMethod.PUT, requestEntity, TeamMemberObject.class).getBody();


	}

	@Override
	public List<TeamMemberObject> getAllPeople() {
		return Arrays.asList(this.restTemplate.getForEntity(
				this.env.getProperty("fiddler.services.person.host")
						+ this.env.getProperty("fiddler.services.person.endpoints.get"),
						TeamMemberObject[].class).getBody());
	}


}
