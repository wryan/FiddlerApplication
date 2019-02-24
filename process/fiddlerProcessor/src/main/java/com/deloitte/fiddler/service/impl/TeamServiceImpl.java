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
import com.deloitte.fiddler.common.TeamRoleObject;
import com.deloitte.fiddler.service.TeamService;

@Component
public class TeamServiceImpl implements TeamService {

	RestTemplate restTemplate;

	Environment env;

	@Autowired
	public TeamServiceImpl(Environment envL) {
		this.restTemplate = new RestTemplate();
		this.env = envL;

	}


	public StandardTeamSchema getTeamByID(String id) throws NoSuchElementException {
		return this.restTemplate.getForObject(
				this.env.getProperty("fiddler.services.team.host")
						+ this.env.getProperty("fiddler.services.team.endpoints.get") + id,
						StandardTeamSchema.class);
	}

	public boolean deleteTeam(String id) {
		return this.restTemplate.getForObject(this.env.getProperty("fiddler.services.team.host")
				+ this.env.getProperty("fiddler.services.team.endpoints.delete") + id, boolean.class);

	}

	@Override
	public StandardTeamSchema createTeam(String t) {

		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.team.host") + 
				this.env.getProperty("fiddler.services.team.endpoints.create"),
						this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.verify.host")
												+ this.env.getProperty("fiddler.services.verify.endpoints.verify")
												+ "StandardTeamSchema",
										t, StandardTeamSchema.class)
								.getBody(),
								StandardTeamSchema.class)
				.getBody();

		
	}

	@Override
	public StandardTeamSchema updateTeam(StandardTeamSchema fp) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<StandardTeamSchema> requestEntity = new HttpEntity<StandardTeamSchema>(
				fp);
		return restTemplate.exchange(
				this.env.getProperty("fiddler.services.team.host")
						+ this.env.getProperty("fiddler.services.team.endpoints.update"),
				HttpMethod.PUT, requestEntity, StandardTeamSchema.class).getBody();


	}

	@Override
	public List<StandardTeamSchema> getAllTeams() {
		return Arrays.asList(this.restTemplate.getForEntity(
				this.env.getProperty("fiddler.services.team.host")
						+ this.env.getProperty("fiddler.services.team.endpoints.get"),
						StandardTeamSchema[].class).getBody());
	}


	@Override
	public StandardTeamSchema addPersonToRole(String teamId, int roleIndex, String personId) {
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.team.host")
						+ this.env.getProperty("fiddler.services.team.endpoints.get") + teamId + "/" + 
				roleIndex + "/" + personId, null, StandardTeamSchema.class).getBody();
	}


	@Override
	public TeamRoleObject getRoleFromTeam(String teamId, int roleIndex) {
		return this.restTemplate.getForObject(
				this.env.getProperty("fiddler.services.team.host")
						+ this.env.getProperty("fiddler.services.team.endpoints.get") + teamId + "/" + roleIndex,
						TeamRoleObject.class);
	}


}
