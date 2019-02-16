
package com.deloitte.fiddler.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.deloitte.fiddler.common.StandardProjectInformationSchema;


public interface ProjectRepository extends MongoRepository<StandardProjectInformationSchema, String> {
	
	public StandardProjectInformationSchema findByprojectId(String id);

}
