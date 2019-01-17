package com.deloitte.fiddler.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.model.FiddlerProcess;
import com.deloitte.fiddler.model.FiddlerProject;
import com.deloitte.fiddler.model.FiddlerTask;
import com.deloitte.fiddler.repository.ProjectRepository;
import com.deloitte.fiddler.service.ProjectService;

@Component
public class ProjectServiceImpl implements ProjectService {

	ProjectRepository pr;

	@Autowired
	public ProjectServiceImpl(ProjectRepository prL) {
		this.pr = prL;
	}

	public FiddlerProject getProjectByID(String id) throws NoSuchElementException {
		return this.pr.findById(id).get();
	}

	public boolean deleteProject(String id) {
		this.pr.deleteById(id);
		return true;

	}

	@Override
	public FiddlerProject createProject() {
		FiddlerTask ft = new FiddlerTask();
		FiddlerProcess fps = new FiddlerProcess();
		fps.getTasks().add(ft);
		FiddlerProject fp = new FiddlerProject();
		fp.getProcesses().add(fps);
		return this.pr.save(fp);
	}

	@Override
	public FiddlerProject updateProject(FiddlerProject fd) {
		return this.pr.save(fd);

	}

	@Deprecated
	public FiddlerTask updateTask(FiddlerTask ft) {
		return  null;
//		for (FiddlerProject fp : this.pr.findAll()) {
//			for (FiddlerProcess fidProc : fp.getProcesses()) {
//				fidProc.getTasks().stream().filter(a -> a.getTaskId() == ft.getTaskId()).findFirst();
//			}
//		}
//		this.pr.findAll().stream().filter(a -> a.getProcesses().stream().filter(b 
//				-> b.getTasks().stream().filter(c -> c.getTaskId() == ft.getTaskId()).))

		// good
//		this.pr.findAll().stream().filter(a -> a.getProcesses().stream().filter(b -> b.getCategory().length() ==1).findFirst().isPresent());
//		FiddlerProject fp = this.pr.findAll().stream().filter(a -> a.getProcesses().stream()
//				.filter(b -> b.getTasks().stream().filter(c -> c.getTaskId() == ft.getTaskId()).findFirst().isPresent())
//				.findFirst().isPresent()).findFirst().get();

		// good
//		FiddlerTask ft2 = this.pr.findAll().stream()
//				.map(a -> a.getProcesses().stream()
//						.map(b -> b.getTasks().stream().filter(c -> c.getTaskId() == ft.getTaskId()).findFirst().get())
//						.findFirst().get())
//				.findFirst().get();

//		return this.pr.findAll().stream().map(a -> a.getProcesses().stream().map(b -> b.getTasks().stream().map(c -> {
//			if (c.get_id().equals(ft.get_id())) {
//				c = ft;
//				this.pr.save(a);
//				return c;
//			}
//			return null;
//		}).findFirst().get()).findFirst().get()).findFirst().get();

	}

	@Override
	public List<FiddlerProject> getAllProjects() {
		return this.pr.findAll();
	}

	@Override
	public FiddlerTask updateTask(FiddlerTask ft, String fp, int processIndex, int taskIndex) {
		//Bob stopped here
		return null;
//return this.pr.save(this.pr.findById(fp).get().getProcesses().get(processIndex)
//.getTasks().set(taskIndex, ft));
		
		

	
	}

}
