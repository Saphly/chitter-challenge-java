package com.chitterchallenge.server.controllers;

import com.chitterchallenge.server.model.Peep;
import com.chitterchallenge.server.services.PeepService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("peep")
@Validated
public class PeepController {
    private final PeepService peepService;

    @Autowired
    public PeepController(PeepService peepServices) {
        this.peepService = peepServices;
    }

    @GetMapping(value = "/all")
    public List<Peep> getAllPeeps() {
        return peepService.getAllPeeps();
    }

    @PostMapping(value = "/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Peep addPeep(@Valid @RequestBody Peep peep){
        return peepService.addPeep(peep);
    }
}
