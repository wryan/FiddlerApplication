package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.common.ProcessesArray;
import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;
import com.deloitte.fiddler.repository.ProjectRepository;
import com.deloitte.fiddler.service.ProjectService;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;

@Component
public class ProjectServiceImpl implements ProjectService {

	ProjectRepository pr;

	Datastore datastore;

	@Autowired
	public ProjectServiceImpl(ProjectRepository prL) {
		this.pr = prL;
		this.datastore = DatastoreOptions.getDefaultInstance().getService();

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
		p.setProjectId(null);
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

}
