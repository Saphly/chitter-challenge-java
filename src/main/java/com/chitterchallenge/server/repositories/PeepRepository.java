package com.chitterchallenge.server.repositories;

import com.chitterchallenge.server.model.Peep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeepRepository extends MongoRepository<Peep, String> {

}
