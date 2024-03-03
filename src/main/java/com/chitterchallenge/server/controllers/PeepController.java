package com.chitterchallenge.server.controllers;

import com.chitterchallenge.server.model.Peep;
import com.chitterchallenge.server.services.PeepServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PeepController {
    private final PeepServices peepServices;

    @Autowired
    public PeepController(PeepServices peepServices) {
        this.peepServices = peepServices;
    }

    @GetMapping(value = "/")
    public List<Peep> getAllPeeps() {
        return peepServices.getAllPeeps();
    }

    @PostMapping(value = "/add-peep")
    @ResponseStatus(HttpStatus.CREATED)
    public Peep addPeep(@RequestBody Peep peep){
        return peepServices.addPeep(peep);
    }
}
