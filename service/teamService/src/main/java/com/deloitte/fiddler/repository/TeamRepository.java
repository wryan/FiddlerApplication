
package com.deloitte.fiddler.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.deloitte.fiddler.common.StandardTeamSchema;


public interface TeamRepository extends MongoRepository<StandardTeamSchema, String> {
	
}
