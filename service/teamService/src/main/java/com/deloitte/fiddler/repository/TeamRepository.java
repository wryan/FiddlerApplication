
package com.deloitte.fiddler.repository;


import java.util.NoSuchElementException;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deloitte.fiddler.common.StandardTeamSchema;


public interface TeamRepository extends MongoRepository<StandardTeamSchema, String> {
	
	public StandardTeamSchema findByteamId(String id) throws NoSuchElementException;

}
