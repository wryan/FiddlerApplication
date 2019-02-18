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

import com.deloitte.fiddler.common.StandardProjectInformationSchema;
import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.service.TeamService;

@RestController
@RequestMapping("/api/team")
public class TeamController {

	@Autowired
	TeamService ts;

	@PostMapping
	public StandardTeamSchema createNewTeam(@RequestBody String t) {
		return this.ts.createTeam(t);
	}

	@GetMapping
	public List<StandardTeamSchema> getTeams() {
		return this.ts.getAllTeams();

	}

	@GetMapping("/{id}")
	public StandardTeamSchema getTeamByID(@PathVariable String id) {
		return this.ts.getTeamByID(id);

	}

	@PutMapping()
	public StandardTeamSchema updateTeam(@RequestBody StandardTeamSchema fp) {
		return this.ts.updateTeam(fp);

	}

	@PostMapping("/{id}/delete")
	public boolean deleteTeam(@PathVariable String id) {
		return this.ts.deleteTeam(id);
		
	}
}
