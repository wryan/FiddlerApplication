package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;
import com.deloitte.fiddler.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		return this.restTemplate.getForObject(this.env.getProperty("fiddler.services.project.host") + this.env.getProperty("fiddler.services.project.endpoints.get") + id, 
				StandardProjectInformationSchema.class);
	}
//
//	public boolean deleteProject(String id) {
//		this.pr.deleteById(id);
//		return true;
//
//	}
//
	public StandardProjectInformationSchema createProject(String p) {
		
		StandardProjectInformationSchema s = null;
		try {
			s = this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.project.host") +
					this.env.getProperty("fiddler.services.project.endpoints.create"), this.restTemplate.postForEntity(this.env.getProperty("fiddler.services.verify.host") +
							this.env.getProperty("fiddler.services.verify.endpoints.verify") + "StandardProjectInformationSchema", p,
							StandardProjectInformationSchema.class).getBody(),
					StandardProjectInformationSchema.class).getBody();
		} catch (HttpServerErrorException e) {
			//Object to JSON in String
			throw new java.lang.Error(e.getResponseBodyAsString());

		}

		return s;



		
	}
//
//	@Override
//	public StandardProjectInformationSchema updateProject(StandardProjectInformationSchema fp) {
//		this.checkForProjectClosure(fp);
//		return this.pr.save(fp);
//
//	}
//
//	@Override
//	public List<StandardProjectInformationSchema> getAllProjects() {
//		return this.pr.findAll();
//	}
//
//	@Override
//	public StandardTaskSchema updateTask(StandardTaskSchema ft, String fp, int processIndex, int taskIndex) {
//
//		StandardProjectInformationSchema proj = this.pr.findById(fp).get();
//		proj.getProcessesArray().get(processIndex).getSubProcessTasks().set(taskIndex, ft);
//		this.checkForProjectClosure(proj);
//		return this.pr.save(proj).getProcessesArray().get(processIndex).getSubProcessTasks().get(taskIndex);
//
//	}
//
//	private StandardProjectInformationSchema checkForProjectClosure(StandardProjectInformationSchema fp) {
//		if (fp.getProcessesArray().stream().allMatch(a -> a.getSubProcessTasks().stream().allMatch(b -> b
//				.getTaskStatusHistory().get(b.getTaskStatusHistory().size() - 1).getStatusValue().equals("Closed")))) {
//			fp.setProjectStatus("Closed");
//		}
//		return fp;
//	}

}
