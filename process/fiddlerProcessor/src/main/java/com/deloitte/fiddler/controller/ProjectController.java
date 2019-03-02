package com.deloitte.fiddler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTaskSchema;
import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.common.TeamRoleObject;
import com.deloitte.fiddler.service.ProjectService;
import com.deloitte.fiddler.service.TeamService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	ProjectService ps;

	@Autowired
	TeamService ts;

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

	@PostMapping("/{id}/addProcess")
	public StandardProjectInformationSchema addProcessToProject(@PathVariable String id,
			@RequestBody String processURL) {
		return this.ps.addProcesstoProject(id, processURL);

	}

	@PostMapping("/{id}/{procId}/{taskId}/status")
	public ResponseEntity<StandardTaskSchema> updateTask(@PathVariable String id, @PathVariable int procId,
			@PathVariable int taskId, @RequestBody String status) {
		return new ResponseEntity<StandardTaskSchema>(this.ps.updateTaskStatus(status, id, procId, taskId),
				HttpStatus.ACCEPTED);

	}

	@PutMapping("/{id}/team")
	public ResponseEntity<StandardTeamSchema> updateTeam(@PathVariable String id, @RequestBody StandardTeamSchema fp) {
		return new ResponseEntity<StandardTeamSchema>(this.ts.updateTeam(id, fp), HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}/team/{roleIndex}")
	public ResponseEntity<TeamRoleObject> getRoleFromTeam(@PathVariable String id, @PathVariable int roleIndex) {
		return new ResponseEntity<TeamRoleObject>(this.ts.getRoleFromTeam(id, roleIndex), HttpStatus.OK);
	}

	@PostMapping("/{id}/team/{roleIndex}/add")
	public ResponseEntity<StandardTeamSchema> addPersonToRole(@PathVariable String id, @PathVariable int roleIndex,
			@RequestBody String personId) {
		return new ResponseEntity<StandardTeamSchema>(this.ts.addPersonToRole(id, roleIndex, personId),
				HttpStatus.ACCEPTED);
	}

	@PostMapping("/{id}/team/{roleIndex}/remove")
	public ResponseEntity<StandardTeamSchema> removePersonFromRole(@PathVariable String id, @PathVariable int roleIndex,
			@RequestBody String personId) {
		return new ResponseEntity<StandardTeamSchema>(this.ts.removePersonFromRole(id, roleIndex, personId),
				HttpStatus.ACCEPTED);

	}
}
