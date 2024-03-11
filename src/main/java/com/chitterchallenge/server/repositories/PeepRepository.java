package com.chitterchallenge.server.repositories;

import com.chitterchallenge.server.model.Peep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeepRepository extends MongoRepository<Peep, String> {
    List<Peep> findAllByOrderByDateCreatedDesc();
}
