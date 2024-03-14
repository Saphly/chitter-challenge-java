package com.chitterchallenge.server.services;

import com.chitterchallenge.server.models.Peep;
import com.chitterchallenge.server.repositories.PeepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeepService {
    @Autowired
    private PeepRepository peepRepository;

    public List<Peep> getAllPeeps() {
//        return peepRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
        return peepRepository.findAllByOrderByDateCreatedDesc();
    }

    public Peep addPeep(Peep peep) {
        return peepRepository.save(peep);
    }

}
