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
import org.springframework.web.multipart.MultipartFile;

import com.deloitte.fiddler.common.ProcessesArray;
import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;
import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.service.FileService;
import com.deloitte.fiddler.service.ProjectService;
import com.deloitte.fiddler.service.TeamService;
import com.deloitte.fiddler.service.VerifyService;


@Component
public class ProjectServiceImpl implements ProjectService {

	RestTemplate restTemplate;

	Environment env;
	
	TeamService ts;
	
	VerifyService vs;
	
	DiscoveryClient discoveryClient;
	
	FileService fs;

	@Autowired
	public ProjectServiceImpl(Environment envL, TeamService tsL, VerifyService vsL,  DiscoveryClient discoveryClientL, FileService fsL) {
		this.restTemplate = new RestTemplate();
		this.env = envL;
		this.ts = tsL;
		this.vs = vsL;
		this.discoveryClient = discoveryClientL;
		this.fs = fsL;
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
		StandardProjectInformationSchema proj = this.restTemplate.postForEntity(
				this.env.getProperty("fiddler.services.verify.host")
				+ this.env.getProperty("fiddler.services.verify.endpoints.verify")
				+ "StandardProjectInformationSchema",
		p, StandardProjectInformationSchema.class)
.getBody();
		
		if(proj.getTeam()==null) {
			this.setDefaultProjectTeam(proj);
		}

		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.project.host") + 
				this.env.getProperty("fiddler.services.project.endpoints.create"),
						proj,
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

	@Override
	public StandardProjectInformationSchema addProcesstoProject(String projectId, String processesArrayUrl) {
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.project.host") + 
				this.env.getProperty("fiddler.services.project.endpoints.create") + projectId + "/addProcess",
						this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.verify.host")
												+ this.env.getProperty("fiddler.services.verify.endpoints.verify")
												+ "ProcessesArray",
										processesArrayUrl, ProcessesArray.class)
								.getBody(),
								StandardProjectInformationSchema.class)
				.getBody();

	}

	@Override
	public StandardTaskSchema updateTaskStatus(String status, String projectId, int processIndex, int taskIndex) {
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.project.host")
				+ this.env.getProperty("fiddler.services.project.endpoints.update") + projectId + "/" + processIndex + "/"
				+ taskIndex +"/status", status, StandardTaskSchema.class).getBody();
	}
	
	private void setDefaultProjectTeam(StandardProjectInformationSchema proj) {

		proj.setTeam(this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.verify.host")
				+ this.env.getProperty("fiddler.services.verify.endpoints.verify")
				+ "StandardTeamSchema",
				"https://raw.githubusercontent.com/bobmalouf/FiddlerTemplates/master/src/templates/json/TechnologyPatentReviewTeamTemplate.json", StandardTeamSchema.class).getBody());
	}

	@Override
	public StandardProjectInformationSchema addDocumentToProject(String projectId, MultipartFile file) {
		System.out.println(this.env.getProperty("fiddler.services.project.host") + 
				this.env.getProperty("fiddler.services.project.endpoints.update") + projectId + "/addDocument");
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.project.host") + 
				this.env.getProperty("fiddler.services.project.endpoints.update") + projectId + "/addDocument",
				this.fs.saveFile(file), StandardProjectInformationSchema.class).getBody();
	}

	@Override
	public boolean removeDocumentFromProject(String projectId, String documentId) {
		return this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.project.host") + 
				this.env.getProperty("fiddler.services.project.endpoints.update") + projectId + "/removeDocument",
				documentId, boolean.class).getBody();
	}

}
