package com.deloitte.fiddler.service;

import java.util.List;

import com.deloitte.fiddler.common.ProcessesArray;
import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;

public interface ProjectService {
	
	public StandardProjectInformationSchema createProject(StandardProjectInformationSchema p);
	
	public List<StandardProjectInformationSchema> getAllProjects();
	
	public StandardProjectInformationSchema getProjectByID(String id);
	
	public StandardProjectInformationSchema updateProject(StandardProjectInformationSchema fd);
	
	public boolean deleteProject(String id);
	
	public StandardTaskSchema updateTask(StandardTaskSchema ft, String fp, int processIndex, int taskIndex);
	
	public StandardProjectInformationSchema addProcesstoProject(String projectId, ProcessesArray processArray);
	
	public StandardTaskSchema updateTaskStatus(String status, String projectId, int processIndex, int taskIndex);
	
	public StandardProjectInformationSchema addDocumentToProject(String projectId, String documentId);
	
	public boolean removeDocumentFromProject(String projectId, String documentId);

}
 