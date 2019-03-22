package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.common.ProcessesArray;
import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;
import com.deloitte.fiddler.common.TaskStatusObject;
import com.deloitte.fiddler.repository.ProjectRepository;
import com.deloitte.fiddler.service.ProjectService;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;

@Component
public class ProjectServiceImpl implements ProjectService {

	ProjectRepository pr;


	@Autowired
	public ProjectServiceImpl(ProjectRepository prL) {
		this.pr = prL;

	}

	public StandardProjectInformationSchema getProjectByID(String id) throws NoSuchElementException {
		Optional<StandardProjectInformationSchema> proj = this.pr.findById(id);
		if (!proj.isPresent()) {
			throw new NoSuchElementException("cannot find project with id " + id);
		}
		return proj.get();
	}

	public boolean deleteProject(String id) {
		this.pr.deleteById(id);
		return true;

	}

	@Override
	public StandardProjectInformationSchema createProject(StandardProjectInformationSchema p) {
		this.projectInitializer(p);
		System.out.println(p.getCreated().getTime());
		return this.pr.save(p);

	}

	@Override
	public StandardProjectInformationSchema updateProject(StandardProjectInformationSchema fp) {
		this.checkForProjectClosure(fp);
		this.getProjectByID(fp.getProjectId());
		return this.pr.save(fp);

	}

	@Override
	public List<StandardProjectInformationSchema> getAllProjects() {
		return this.pr.findAll();
	}

	@Override
	public StandardTaskSchema updateTask(StandardTaskSchema ft, String fp, int processIndex, int taskIndex) {

		StandardProjectInformationSchema proj = this.getProjectByID(fp);
		proj.getProcessesArray().get(processIndex).getSubProcessTasks().set(taskIndex, ft);
		this.checkForProjectClosure(proj);
		return this.pr.save(proj).getProcessesArray().get(processIndex).getSubProcessTasks().get(taskIndex);

	}

	private StandardProjectInformationSchema checkForProjectClosure(StandardProjectInformationSchema fp) {
		if (fp.getProcessesArray().stream()
				.allMatch(a -> a.getSubProcessTasks().stream()
						.allMatch(b -> !b.getTaskStatusHistory().isEmpty() && b.getTaskStatusHistory()
								.get(b.getTaskStatusHistory().size() - 1).getStatusValue().equals("Closed")))) {
			fp.setProjectStatus("Closed");
		}
		return fp;
	}

	@Override
	public StandardProjectInformationSchema addProcesstoProject(String projectId, ProcessesArray processArray) {
		StandardProjectInformationSchema project = this.getProjectByID(projectId);
		project.getProcessesArray().add(processArray);
		return this.updateProject(project);
	}

	@Override
	public StandardTaskSchema updateTaskStatus(String status, String projectId, int processIndex, int taskIndex) {
		TaskStatusObject tso = new TaskStatusObject();
		tso.setStatusDate(DateTime.now().toDate());
		tso.setStatusOwner("Admin");
		tso.setStatusValue(status);
		StandardTaskSchema task = this.getProjectByID(projectId).getProcessesArray()
		.get(processIndex).getSubProcessTasks().get(taskIndex);
		task.getTaskStatusHistory().add(tso);
		return this.updateTask(task, projectId, processIndex, taskIndex);
	}
	
//	-------private methods-----
	private StandardProjectInformationSchema projectInitializer(StandardProjectInformationSchema proj) {
		proj.setProjectId(null);
		proj.setCreated(DateTime.now().toDate());
		TaskStatusObject tso = new TaskStatusObject();
		tso.setStatusDate(DateTime.now().toDate());
		tso.setStatusOwner("Admin");
		tso.setStatusValue("CREATED");
		proj.getProcessesArray().stream()
		.forEach(a -> a.getSubProcessTasks().stream()
				.forEach(b -> b.getTaskStatusHistory().add(tso)));
		return proj;
	}

	@Override
	public StandardProjectInformationSchema addDocumentToProject(String projectId, String documentId) {
		StandardProjectInformationSchema proj = this.getProjectByID(projectId);
		proj.getAttachments().add(documentId);
		return this.updateProject(proj);
	}

	@Override
	public boolean removeDocumentFromProject(String projectId, String documentId) {
		StandardProjectInformationSchema proj = this.getProjectByID(projectId);
		proj.getAttachments().remove(proj.getAttachments().stream().filter(a -> a.equals(documentId)).findFirst().get());
		System.out.println(proj.getAttachments().toString());
		this.updateProject(proj);
		return true;
	}

}
