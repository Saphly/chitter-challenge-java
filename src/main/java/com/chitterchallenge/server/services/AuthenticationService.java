package com.chitterchallenge.server.services;

import com.chitterchallenge.server.daos.JwtAuthenticationResponse;
import com.chitterchallenge.server.daos.SignInRequest;
import com.chitterchallenge.server.daos.SignUpRequest;
import com.chitterchallenge.server.models.User;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
}
