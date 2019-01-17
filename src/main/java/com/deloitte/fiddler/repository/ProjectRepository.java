
package com.deloitte.fiddler.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.deloitte.fiddler.model.FiddlerProject;


public interface ProjectRepository extends MongoRepository<FiddlerProject, String> {

}
