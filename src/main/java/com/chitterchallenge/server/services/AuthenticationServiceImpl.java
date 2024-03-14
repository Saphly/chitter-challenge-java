package com.chitterchallenge.server.services;

import com.chitterchallenge.server.daos.JwtAuthenticationResponse;
import com.chitterchallenge.server.daos.SignInRequest;
import com.chitterchallenge.server.daos.SignUpRequest;
import com.chitterchallenge.server.models.Role;
import com.chitterchallenge.server.models.User;
import com.chitterchallenge.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);

        return response;
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        String jwt = jwtService.generateToken(user);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setToken(jwt);

        return response;
    }
}
