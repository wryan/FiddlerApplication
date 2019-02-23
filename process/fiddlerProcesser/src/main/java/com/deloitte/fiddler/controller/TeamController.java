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

import com.deloitte.fiddler.common.StandardTeamSchema;
import com.deloitte.fiddler.common.TeamRoleObject;
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
	@PostMapping("/{id}/{roleIndex}/{personId}")
	public ResponseEntity<StandardTeamSchema> addPersonToTeam(@PathVariable String id, @PathVariable int roleIndex, @PathVariable String personId) {
		return new ResponseEntity<StandardTeamSchema>(this.ts.addPersonToRole(id, roleIndex, personId), HttpStatus.ACCEPTED);
		
	}
	@GetMapping("/{id}/{roleIndex}")
	public ResponseEntity<TeamRoleObject> getRoleFromTeam(@PathVariable String id, @PathVariable int roleIndex) {
		return new ResponseEntity<TeamRoleObject>(this.ts.getRoleFromTeam(id, roleIndex), HttpStatus.OK);
		
	}
}
