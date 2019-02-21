package com.deloitte.fiddler.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deloitte.fiddler.common.TeamMemberObject;
import com.deloitte.fiddler.repository.PersonRepository;
import com.deloitte.fiddler.service.PersonService;

@Component
public class PersonServiceImpl implements PersonService {

	PersonRepository pr;

	@Autowired
	public PersonServiceImpl(PersonRepository prL) {
		this.pr = prL;

	}

	@Override
	public TeamMemberObject createPerson(TeamMemberObject p) {
		p.setTeamMemberID(java.util.UUID.randomUUID().toString());
		return this.pr.save(p);
	}

	@Override
	public List<TeamMemberObject> getAllPeople() {
		return this.pr.findAll();
	}

	@Override
	public TeamMemberObject getPersonById(String id) throws NoSuchElementException {
		TeamMemberObject person = null;
		person = this.pr.findByteamMemberID(id);
		if (person == null) {
			throw new NoSuchElementException("cannot find person with id " + id);
		}
		return person;
	}

	@Override
	public TeamMemberObject updatePerson(TeamMemberObject p) throws NoSuchElementException{
		this.deletePerson(this.getPersonById(p.getTeamMemberID()).getTeamMemberID());
		return this.pr.save(p);
	}

	@Override
	public boolean deletePerson(String id) {
		this.pr.deleteByteamMemberID(id);
		return true;
	}

}
