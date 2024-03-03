package com.chitterchallenge.server.controllers;

import com.chitterchallenge.server.dtos.UserDto;
import com.chitterchallenge.server.exceptions.IncorrectDetailsException;
import com.chitterchallenge.server.exceptions.UserEmailRegisteredException;
import com.chitterchallenge.server.exceptions.UsernameTakenException;
import com.chitterchallenge.server.mappers.UserMapper;
import com.chitterchallenge.server.model.User;
import com.chitterchallenge.server.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    private Boolean existsByUsername(String username) {
       return userServices.existsByUsername(username);
    }

    private Boolean existsByEmail(String email) {
        return userServices.existsByEmail(email);
    }

    public User findByEmail(String email) {
        try {
            return userServices.findByEmail(email);
        } catch (NoSuchElementException err) {
            throw new IncorrectDetailsException();
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserDto> userLogin(@RequestBody User input) {
        User user = findByEmail(input.getEmail());

        if(!Objects.equals(input.getPassword(), user.getPassword())) {
            throw new IncorrectDetailsException();
        }

        UserDto userDto = UserMapper.convertEntityToDto(user);
        userDto.setMessage("Login success");

        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> userRegister(@RequestBody User input) {
        if(existsByEmail(input.getEmail())) {
            throw new UserEmailRegisteredException();
        }

        if (existsByUsername(input.getUsername())) {
            throw new UsernameTakenException();
        }

        User user = userServices.registerUser(input);

        UserDto userDto = UserMapper.convertEntityToDto(user);
        return ResponseEntity.ok().body(userDto);
    }
}