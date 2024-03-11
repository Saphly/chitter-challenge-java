package com.chitterchallenge.server.controllers;

import com.chitterchallenge.server.dtos.UserDto;
import com.chitterchallenge.server.exceptions.IncorrectDetailsException;
import com.chitterchallenge.server.exceptions.UserEmailRegisteredException;
import com.chitterchallenge.server.exceptions.UsernameTakenException;
import com.chitterchallenge.server.mappers.UserMapper;
import com.chitterchallenge.server.model.User;
import com.chitterchallenge.server.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private Boolean existsByUsername(String username) {
       return userService.existsByUsername(username);
    }

    private Boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }

    private User findByEmail(String email) {
        try {
            return userService.findByEmail(email);
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
    public ResponseEntity<UserDto> userRegister(@Valid @RequestBody User input) {
        if(existsByEmail(input.getEmail())) {
            throw new UserEmailRegisteredException();
        }

        if (existsByUsername(input.getUsername())) {
            throw new UsernameTakenException();
        }

        User user = userService.registerUser(input);

        UserDto userDto = UserMapper.convertEntityToDto(user);
        userDto.setMessage("Register success");
        return ResponseEntity.ok().body(userDto);
    }
}
