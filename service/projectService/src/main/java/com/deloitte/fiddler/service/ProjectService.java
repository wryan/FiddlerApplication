package com.deloitte.fiddler.service;

import java.util.List;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;

public interface ProjectService {
	
	public StandardProjectInformationSchema createProject(StandardProjectInformationSchema p);
	
	public List<StandardProjectInformationSchema> getAllProjects();
	
	public StandardProjectInformationSchema getProjectByID(String id);
	
	public StandardProjectInformationSchema updateProject(StandardProjectInformationSchema fd);
	
	public boolean deleteProject(String id);
	
	public StandardTaskSchema updateTask(StandardTaskSchema ft, String fp, int processIndex, int taskIndex);

}
 