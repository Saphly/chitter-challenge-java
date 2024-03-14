package com.chitterchallenge.server.services;

import com.chitterchallenge.server.models.User;
import com.chitterchallenge.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return findByUsername(username);
            }
        };
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new NoSuchElementException("User not found")
                );
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new NoSuchElementException("User not found")
                );
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }
}
