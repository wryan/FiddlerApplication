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

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;
import com.deloitte.fiddler.service.ProjectService;

@Component
public class ProjectServiceImpl implements ProjectService {

	RestTemplate restTemplate;

	Environment env;

	@Autowired
	public ProjectServiceImpl(Environment envL) {
		this.restTemplate = new RestTemplate();
		this.env = envL;

	}

	public StandardProjectInformationSchema getProjectByID(String id) throws NoSuchElementException {

		return this.restTemplate.getForObject(
				this.env.getProperty("fiddler.services.project.host")
						+ this.env.getProperty("fiddler.services.project.endpoints.get") + id,
				StandardProjectInformationSchema.class);
	}

	public boolean deleteProject(String id) {
		return this.restTemplate.getForObject(this.env.getProperty("fiddler.services.project.host")
				+ this.env.getProperty("fiddler.services.project.endpoints.delete") + id, boolean.class);

	}

	public StandardProjectInformationSchema createProject(String p) {

		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.project.host") + 
				this.env.getProperty("fiddler.services.project.endpoints.create"),
						this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.verify.host")
												+ this.env.getProperty("fiddler.services.verify.endpoints.verify")
												+ "StandardProjectInformationSchema",
										p, StandardProjectInformationSchema.class)
								.getBody(),
						StandardProjectInformationSchema.class)
				.getBody();

	}

	@Override
	public StandardProjectInformationSchema updateProject(StandardProjectInformationSchema fp) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<StandardProjectInformationSchema> requestEntity = new HttpEntity<StandardProjectInformationSchema>(
				fp);
		return restTemplate.exchange(
				this.env.getProperty("fiddler.services.project.host")
						+ this.env.getProperty("fiddler.services.project.endpoints.update"),
				HttpMethod.PUT, requestEntity, StandardProjectInformationSchema.class).getBody();

	}

	@Override
	public List<StandardProjectInformationSchema> getAllProjects() {

		return Arrays.asList(this.restTemplate.getForEntity(
				this.env.getProperty("fiddler.services.project.host")
						+ this.env.getProperty("fiddler.services.project.endpoints.get"),
				StandardProjectInformationSchema[].class).getBody());
	}

	@Override
	public StandardTaskSchema updateTask(StandardTaskSchema ft, String fp, int processIndex, int taskIndex) {
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.project.host")
				+ this.env.getProperty("fiddler.services.project.endpoints.update") + fp + "/" + processIndex + "/"
				+ taskIndex, ft, StandardTaskSchema.class).getBody();

	}

}
