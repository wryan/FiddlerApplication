
package com.deloitte.fiddler.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.deloitte.fiddler.common.TeamMemberObject;


public interface PersonRepository extends MongoRepository<TeamMemberObject, String> {
	

}
