package com.chitterchallenge.server.services;

import com.chitterchallenge.server.model.Peep;
import com.chitterchallenge.server.repositories.PeepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PeepServices {
    @Autowired
    private PeepRepository peepRepository;

//    https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
    public List<Peep> getAllPeeps() {
        List<Peep> peeps = peepRepository.findAll();
        peeps.sort(Comparator.comparing(Peep::getDateCreated).reversed());
        return peeps;
    }

    public Peep addPeep(Peep peep) {
        return peepRepository.save(peep);
    }

}
