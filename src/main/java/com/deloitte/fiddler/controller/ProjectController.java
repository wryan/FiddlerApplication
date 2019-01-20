package com.deloitte.fiddler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.fiddler.model.FiddlerProject;
import com.deloitte.fiddler.model.ProjectInitializer;
import com.deloitte.fiddler.service.ProjectService;


@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired
	ProjectService ps;
	
	@PostMapping
    public FiddlerProject createNewProject(@RequestBody ProjectInitializer pj) {
        return this.ps.createProject(pj.getJsonURL());
    }
	
	@GetMapping
	public List<FiddlerProject> getProjects() {
		return this.ps.getAllProjects();
		
	}
	
	@GetMapping("/{id}")
	public FiddlerProject getProjectById(@PathVariable String id) {
		return this.ps.getProjectByID(id);
		
	}
	
	@PutMapping("/{id}")
	public FiddlerProject updateProject(@RequestBody FiddlerProject fp) {
		return this.ps.updateProject(fp);
		
	}
}
