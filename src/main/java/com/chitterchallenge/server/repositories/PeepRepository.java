package com.chitterchallenge.server.repositories;

import com.chitterchallenge.server.model.Peep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "peeps", path = "peeps")
public interface PeepRepository extends MongoRepository<Peep, String> {

}
