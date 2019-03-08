package com.deloitte.fiddler.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;
import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.common.TeamRoleObject;
import com.deloitte.fiddler.service.TeamService;

@Component
public class TeamServiceImpl implements TeamService {

	RestTemplate restTemplate;

	Environment env;
	
	DiscoveryClient discoveryClient;

	@Autowired
	public TeamServiceImpl(Environment envL,  DiscoveryClient discoveryClientL) {
		this.restTemplate = new RestTemplate();
		this.env = envL;
		this.discoveryClient = discoveryClientL;

	}

	@Override
	public StandardTeamSchema updateTeam(String projectId, StandardTeamSchema fd) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<StandardTeamSchema> requestEntity = new HttpEntity<StandardTeamSchema>(
				fd);
		return restTemplate.exchange(this.env.getProperty("fiddler.services.project.host")
				+ this.env.getProperty("fiddler.services.project.endpoints.update") + projectId +
				this.env.getProperty("fiddler.services.project.endpoints.team.updateTeam"),
				HttpMethod.PUT, requestEntity, StandardTeamSchema.class).getBody();
	}


	@Override
	public StandardTeamSchema removePersonFromRole(String projectId, int roleIndex, String personId) {
		return this.restTemplate.postForObject(
				this.env.getProperty("fiddler.services.project.host")
						+ this.env.getProperty("fiddler.services.project.endpoints.get") + projectId
						+ this.env.getProperty("fiddler.services.project.endpoints.team.updateTeam") + roleIndex
						+ this.env.getProperty("fiddler.services.project.endpoints.team.removePersonFromRole") +"/" + personId,null,
						StandardTeamSchema.class);
	}

	@Override
	public StandardTeamSchema addPersonToRole(String projectId, int roleIndex, String personId) {
		return this.restTemplate.postForObject(
				this.env.getProperty("fiddler.services.project.host")
						+ this.env.getProperty("fiddler.services.project.endpoints.get") + projectId
						+ this.env.getProperty("fiddler.services.project.endpoints.team.updateTeam") + roleIndex
						+ this.env.getProperty("fiddler.services.project.endpoints.team.addPersonToRole"), personId,
						StandardTeamSchema.class);
	}

	@Override
	public TeamRoleObject getRoleFromTeam(String projectId, int roleIndex) {
		return this.restTemplate.getForObject(
				this.env.getProperty("fiddler.services.project.host")
						+ this.env.getProperty("fiddler.services.project.endpoints.get") + projectId
						+ this.env.getProperty("fiddler.services.project.endpoints.team.getRoleFromTeam") + roleIndex,
						TeamRoleObject.class);
	}

	public StandardTeamSchema removeRole(String projectId, int roleIndex) {
		return this.restTemplate.postForObject(
				this.env.getProperty("fiddler.services.project.host")
						+ this.env.getProperty("fiddler.services.project.endpoints.get") + projectId
						+ this.env.getProperty("fiddler.services.project.endpoints.team.updateTeam") + roleIndex
						+ this.env.getProperty("fiddler.services.project.endpoints.team.removePersonFromRole"),null,
						StandardTeamSchema.class);
	}

	@Override
	public StandardTeamSchema addRole(String projectId, String roleName) {
		return this.restTemplate.postForObject(
				this.env.getProperty("fiddler.services.project.host")
						+ this.env.getProperty("fiddler.services.project.endpoints.get") + projectId
						+ this.env.getProperty("fiddler.services.project.endpoints.team.addRole") ,roleName,
						StandardTeamSchema.class);
	}


}
