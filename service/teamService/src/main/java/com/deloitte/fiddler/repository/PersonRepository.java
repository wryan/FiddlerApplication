
package com.deloitte.fiddler.repository;


import java.util.NoSuchElementException;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deloitte.fiddler.common.TeamMemberObject;


public interface PersonRepository extends MongoRepository<TeamMemberObject, String> {
	
	public TeamMemberObject findByteamMemberID(String id) throws NoSuchElementException;
	public void deleteByteamMemberID(String id) throws NoSuchElementException;

}
