package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.model.FiddlerProject;
import com.deloitte.fiddler.model.FiddlerTask;
import com.deloitte.fiddler.repository.ProjectRepository;
import com.deloitte.fiddler.service.ProjectService;
import com.deloitte.fiddler.util.WebCaller;

@Component
public class ProjectServiceImpl implements ProjectService {

	ProjectRepository pr;
	
	WebCaller wc;

	@Autowired
	public ProjectServiceImpl(ProjectRepository prL, WebCaller wcL) {
		this.pr = prL;
		this.wc = wcL;
	}

	public FiddlerProject getProjectByID(String id) throws NoSuchElementException {
		return this.pr.findById(id).get();
	}

	public boolean deleteProject(String id) {
		this.pr.deleteById(id);
		return true;

	}

	@Override
	public FiddlerProject createProject(String url) {
		return this.pr.save(wc.getWebEntity(FiddlerProject.class, url));
	}

	@Override
	public FiddlerProject updateProject(FiddlerProject fd) {
		return this.pr.save(fd);

	}

	@Override
	public List<FiddlerProject> getAllProjects() {
		return this.pr.findAll();
	}

	@Override
	public FiddlerTask updateTask(FiddlerTask ft, String fp, int processIndex, int taskIndex) {
		
		FiddlerProject proj = this.pr.findById(fp).get();
		proj.getProcesses().get(processIndex).getTasks().set(taskIndex, ft);
		return this.pr.save(proj).getProcesses().get(processIndex).getTasks().get(taskIndex);
	
	}

}
