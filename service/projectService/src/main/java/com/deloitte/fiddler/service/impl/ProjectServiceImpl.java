package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.everit.json.schema.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.model.StandardProjectInformationSchema;
import com.deloitte.fiddler.model.StandardTaskSchema;
import com.deloitte.fiddler.repository.ProjectRepository;
import com.deloitte.fiddler.service.ProjectService;
import com.deloitte.fiddler.util.JSONvalidator;
import com.deloitte.fiddler.util.WebCaller;

@Component
public class ProjectServiceImpl implements ProjectService {

	ProjectRepository pr;

	WebCaller wc;

	JSONvalidator jsv;

	@Autowired
	public ProjectServiceImpl(ProjectRepository prL, WebCaller wcL, JSONvalidator jsvL) {
		this.pr = prL;
		this.wc = wcL;
		this.jsv = jsvL;

	}

	public StandardProjectInformationSchema getProjectByID(String id) throws NoSuchElementException {
		return this.pr.findByprojectId(id);
	}

	public boolean deleteProject(String id) {
		this.pr.deleteById(id);
		return true;

	}

	@Override
	public StandardProjectInformationSchema createProject(String url) {


			this.jsv.validateJSON(StandardProjectInformationSchema.class, wc.getWebEntity(Object.class, url));
			StandardProjectInformationSchema s = wc.getWebEntity(StandardProjectInformationSchema.class, url);
			s.setProjectId(java.util.UUID.randomUUID().toString());
			return this.pr.save(s);

		
	}

	@Override
	public StandardProjectInformationSchema updateProject(StandardProjectInformationSchema fp) {
		this.checkForProjectClosure(fp);
		return this.pr.save(fp);

	}

	@Override
	public List<StandardProjectInformationSchema> getAllProjects() {
		return this.pr.findAll();
	}

	@Override
	public StandardTaskSchema updateTask(StandardTaskSchema ft, String fp, int processIndex, int taskIndex) {

		StandardProjectInformationSchema proj = this.pr.findById(fp).get();
		proj.getProcessesArray().get(processIndex).getSubProcessTasks().set(taskIndex, ft);
		this.checkForProjectClosure(proj);
		return this.pr.save(proj).getProcessesArray().get(processIndex).getSubProcessTasks().get(taskIndex);

	}

	private StandardProjectInformationSchema checkForProjectClosure(StandardProjectInformationSchema fp) {
		if (fp.getProcessesArray().stream().allMatch(a -> a.getSubProcessTasks().stream().allMatch(b -> b
				.getTaskStatusHistory().get(b.getTaskStatusHistory().size() - 1).getStatusValue().equals("Closed")))) {
			fp.setProjectStatus("Closed");
		}
		return fp;
	}

}
