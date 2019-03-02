package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.common.TeamMemberObject;
import com.deloitte.fiddler.repository.PersonRepository;
import com.deloitte.fiddler.service.PersonService;
import com.deloitte.fiddler.service.TeamService;

@Component
public class PersonServiceImpl implements PersonService {

	PersonRepository pr;

	@Autowired
	TeamService ts;

	@Autowired
	public PersonServiceImpl(PersonRepository prL) {
		this.pr = prL;

	}

	public TeamMemberObject createPerson(TeamMemberObject p) {
		p.setTeamMemberID(null);
		return this.pr.save(p);
	}

	public List<TeamMemberObject> getAllPeople() {
		return this.pr.findAll();
	}

	public TeamMemberObject getPersonById(String id) throws NoSuchElementException {

		Optional<TeamMemberObject> person = this.pr.findById(id);
		if (!person.isPresent()) {
			throw new NoSuchElementException("cannot find person with id " + id);
		}
		return person.get();
	}

	public TeamMemberObject updatePerson(TeamMemberObject p) throws NoSuchElementException {
		TeamMemberObject tmo = this.getPersonById(p.getTeamMemberID());
		tmo.setTeamMemberName(p.getTeamMemberName());
		return this.pr.save(tmo);
	}

	public boolean deletePerson(String id) {
		this.ts.getAllTeams().stream().forEach(a -> {
			a.getTeamRoleList().stream().forEach(b -> b.getTeamMembersInRole().remove(this.getPersonById(id).getTeamMemberID()));
			this.ts.updateTeam(a);
		});
		this.pr.deleteById(id);
		return true;
	}

}
