package com.deloitte.fiddler.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "projects")
public class FiddlerProject {
	
	public FiddlerProject() {
		this.setProjectStatus("Open");
		this.setCreated(new Date());
	}
	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}


	public List<FiddlerProcess> getProcesses() {
		return processes;
	}

	public void setProcesses(List<FiddlerProcess> processes) {
		this.processes = processes;
	}
	
	public String getProjectId() {
		return this.projectId;
	}

	public Date getCreated() {
		return created;
	}

	private void setCreated(Date created) {
		this.created = created;
	}

	@Id
	private String projectId;
	
	private String projectType;
	
	private String projectName;
	
	private String projectStatus;
	
	private Date created;
	
	private List<FiddlerProcess> processes = new ArrayList<>();

}
