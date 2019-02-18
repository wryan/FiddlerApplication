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

import com.deloitte.fiddler.model.JSONInitializer;
import com.deloitte.fiddler.model.StandardTeamSchema;
import com.deloitte.fiddler.service.TeamService;

@RestController
@RequestMapping("/api/team")
public class TeamController {

	@Autowired
	TeamService ts;

	@PostMapping
	public StandardTeamSchema createNewProject(@RequestBody JSONInitializer pj) {
		return this.ts.createTeam(pj.getJsonURL());
	}

	@GetMapping
	public List<StandardTeamSchema> getProjects() {
		return this.ts.getAllTeams();

	}

	@GetMapping("/{id}")
	public StandardTeamSchema getProjectById(@PathVariable String id) {
		return this.ts.getTeamByID(id);

	}

	@PutMapping("/{id}")
	public StandardTeamSchema updateProject(@RequestBody StandardTeamSchema fp) {
		return this.ts.updateTeam(fp);

	}

}
