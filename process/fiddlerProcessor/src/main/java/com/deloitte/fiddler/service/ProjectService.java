package com.deloitte.fiddler.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.web.multipart.MultipartFile;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;

public interface ProjectService {
	
	public StandardProjectInformationSchema createProject(String url);
	
	public List<StandardProjectInformationSchema> getAllProjects();
	
	public StandardProjectInformationSchema getProjectByID(String id) throws NoSuchElementException;
	
	public StandardProjectInformationSchema updateProject(StandardProjectInformationSchema fd);
	
	public boolean deleteProject(String id);
	
	public StandardTaskSchema updateTask(StandardTaskSchema ft, String fp, int processIndex, int taskIndex);
		
	public StandardProjectInformationSchema addProcesstoProject(String projectId, String processesArrayUrl);

	public StandardTaskSchema updateTaskStatus(String status, String projectId, int processIndex, int taskIndex);
	
	public StandardProjectInformationSchema addDocumentToProject(String projectId, MultipartFile file);
	
	public boolean removeDocumentFromProject(String projectId, String documentId);

}
