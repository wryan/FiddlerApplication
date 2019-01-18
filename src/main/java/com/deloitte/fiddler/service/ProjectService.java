package com.deloitte.fiddler.service;

import java.util.List;

import com.deloitte.fiddler.model.FiddlerProject;
import com.deloitte.fiddler.model.FiddlerTask;

public interface ProjectService {
	
	public FiddlerProject createProject(String url);
	
	public List<FiddlerProject> getAllProjects();
	
	public FiddlerProject getProjectByID(String id);
	
	public FiddlerProject updateProject(FiddlerProject fd);
	
	public boolean deleteProject(String id);
	
	public FiddlerTask updateTask(FiddlerTask ft, String fp, int processIndex, int taskIndex);

}
