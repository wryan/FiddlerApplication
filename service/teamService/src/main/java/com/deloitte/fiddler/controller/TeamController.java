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
import com.deloitte.fiddler.service.TeamService;

@RestController
@RequestMapping("/api/team")
public class TeamController {

	@Autowired
	TeamService ts;

	@PostMapping
	public ResponseEntity<StandardTeamSchema> createNewTeam(@RequestBody StandardTeamSchema t) {
		return new ResponseEntity<StandardTeamSchema>(this.ts.createTeam(t), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<StandardTeamSchema>> getTeams() {
		return new ResponseEntity<List<StandardTeamSchema>>(this.ts.getAllTeams(), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<StandardTeamSchema> getTeamByID(@PathVariable String id) {
		return new ResponseEntity<StandardTeamSchema>(this.ts.getTeamByID(id), HttpStatus.OK);

	}

	@PutMapping()
	public ResponseEntity<StandardTeamSchema> updateTeam(@RequestBody StandardTeamSchema fp) {
		return new ResponseEntity<StandardTeamSchema>(this.ts.updateTeam(fp), HttpStatus.ACCEPTED);

	}

	@PostMapping("/{id}/delete")
	public ResponseEntity<Boolean> deleteTeam(@PathVariable String id) {
		return new ResponseEntity<Boolean>(this.ts.deleteTeam(id), HttpStatus.NO_CONTENT);
		
	}
	
	@PostMapping("/{id}/{roleIndex}/{personId}")
	public ResponseEntity<StandardTeamSchema> addPersonToTeam(@PathVariable String id, @PathVariable int roleIndex, @PathVariable String personId) {
		return new ResponseEntity<StandardTeamSchema>(this.ts.addPersonToRole(id, roleIndex, personId), HttpStatus.ACCEPTED);
		
	}
}
