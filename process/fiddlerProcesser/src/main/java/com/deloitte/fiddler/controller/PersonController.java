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

import com.deloitte.fiddler.common.TeamMemberObject;
import com.deloitte.fiddler.service.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {

	@Autowired
	PersonService ps;

	@PostMapping
	public ResponseEntity<TeamMemberObject> createNewPerson(@RequestBody String t) {
		return new ResponseEntity<TeamMemberObject>(this.ps.createPerson(t), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<TeamMemberObject>> getPeople() {
		return new ResponseEntity<List<TeamMemberObject>>(this.ps.getAllPeople(), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<TeamMemberObject> getPersonById(@PathVariable String id) {
		return new ResponseEntity<TeamMemberObject>(this.ps.getPersonById(id), HttpStatus.OK);

	}

	@PutMapping()
	public ResponseEntity<TeamMemberObject> updatePerson(@RequestBody TeamMemberObject fp) {
		return new ResponseEntity<TeamMemberObject>(this.ps.updatePerson(fp), HttpStatus.ACCEPTED);

	}

	@PostMapping("/{id}/delete")
	public ResponseEntity<Boolean> deletePerson(@PathVariable String id) {
		return new ResponseEntity<Boolean>(this.ps.deletePerson(id), HttpStatus.NO_CONTENT);
		
	}
}
