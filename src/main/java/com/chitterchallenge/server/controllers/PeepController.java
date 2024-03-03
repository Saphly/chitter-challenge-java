package com.chitterchallenge.server.controllers;

import com.chitterchallenge.server.model.Peep;
import com.chitterchallenge.server.services.PeepServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("peep")
@Validated
public class PeepController {
    private final PeepServices peepServices;

    @Autowired
    public PeepController(PeepServices peepServices) {
        this.peepServices = peepServices;
    }

    @GetMapping(value = "/all")
    public List<Peep> getAllPeeps() {
        return peepServices.getAllPeeps();
    }

    @PostMapping(value = "/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Peep addPeep(@RequestBody Peep peep){
        return peepServices.addPeep(peep);
    }
}
