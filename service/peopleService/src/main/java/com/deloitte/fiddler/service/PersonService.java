package com.deloitte.fiddler.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.deloitte.fiddler.common.TeamMemberObject;

public interface PersonService {
	
	public TeamMemberObject createPerson(String name);
	
	public List<TeamMemberObject> getAllPeople();
	
	public TeamMemberObject getPersonById(String id) throws NoSuchElementException ;
	
	public TeamMemberObject updatePerson(TeamMemberObject fd);
	
	public boolean deletePerson(String id);
	
}
