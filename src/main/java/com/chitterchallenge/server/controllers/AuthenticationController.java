package com.chitterchallenge.server.controllers;

import com.chitterchallenge.server.daos.JwtAuthenticationResponse;
import com.chitterchallenge.server.daos.SignInRequest;
import com.chitterchallenge.server.daos.SignUpRequest;
import com.chitterchallenge.server.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> userRegister(@RequestBody SignUpRequest request) {
        System.out.println("HELLOOOOOOOOO");
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> userLogin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

}
