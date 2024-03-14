package com.chitterchallenge.server.repositories;

import com.chitterchallenge.server.models.Peep;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeepRepository extends MongoRepository<Peep, String> {
    List<Peep> findAllByOrderByDateCreatedDesc();
}
