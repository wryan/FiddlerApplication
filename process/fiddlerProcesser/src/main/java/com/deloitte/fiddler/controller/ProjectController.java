package com.deloitte.fiddler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	ProjectService ps;

	@PostMapping
	public StandardProjectInformationSchema createNewProject(@RequestBody String url) {
		return this.ps.createProject(url);
	}

	@GetMapping
	public List<StandardProjectInformationSchema> getProjects() {
		return this.ps.getAllProjects();

	}

	@GetMapping("/{id}")
	public StandardProjectInformationSchema getProjectById(@PathVariable String id) {
		return this.ps.getProjectByID(id);

	}
	

	@PostMapping("/{id}/team/{teamId}")
	public StandardProjectInformationSchema setTeamID(@PathVariable String id, @PathVariable String teamId) {
		return this.ps.setTeamID(id, teamId);

	}
//
//	@PutMapping("/{id}")
//	public StandardProjectInformationSchema updateProject(@RequestBody StandardProjectInformationSchema fp) {
//		return this.ps.updateProject(fp);
//
//	}
//
//	@PostMapping("/{id}/{procId}/{taskId}")
//	public StandardTaskSchema updateTask(@PathVariable String id, @PathVariable int procId, 
//			@PathVariable int taskId, @RequestBody StandardTaskSchema ft) {
//		return this.ps.updateTask(ft, id, procId, taskId);
//		
//	}
}
