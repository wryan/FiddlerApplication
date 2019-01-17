package com.deloitte.fiddler.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FiddlerTask {
	
	public FiddlerTask() {
		this.setTaskStatus("Open");
	}
	
		
	private String taskTitle;
	
	private String taskDescription;
	
	private String taskStatus;
	
	private String applicationURI;
	

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getApplicationURI() {
		return applicationURI;
	}

	public void setApplicationURI(String applicationURI) {
		this.applicationURI = applicationURI;
	}

}
