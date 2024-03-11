package com.chitterchallenge.server.services;

import com.chitterchallenge.server.model.User;
import com.chitterchallenge.server.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User newUser;

    @BeforeEach
    void createNewUser() {
        newUser = new User();
        newUser.setEmail("john@example.com");
        newUser.setName("John Doe");
        newUser.setPassword("asd");
        newUser.setUsername("JD");
    }

    @Nested
    class findByEmailTests {
        @Test
        @DisplayName("Should return user with existing email")
        void shouldReturnUserWithExistingEmail() {
            when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.of(newUser));

            User userResult = userService.findByEmail("john@example.com");
            assertEquals(userResult.getUsername(), newUser.getUsername());
            assertEquals(userResult.getEmail(), newUser.getEmail());
            assertEquals(userResult.getName(), newUser.getName());
        }

        @Test
        @DisplayName("Should throw no such element exception when email is not found")
        void shouldThrowNoSuchElementException() {
            when(userRepository.findByEmail("")).thenThrow(new NoSuchElementException("User not found"));

            Exception exception = assertThrows(NoSuchElementException.class, () -> {
                userService.findByEmail("");
            });

            assertTrue(exception.getMessage().contains("User not found"));
        }
    }

    @Nested
    class findByUsernameTests {
        @Test
        @DisplayName("Should return user given existing username")
        void shouldReturnValidUserGivenUsername() {
            when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.of(newUser));

            User userResult = userService.findByUsername("JD");
            assertEquals(userResult.getName(), newUser.getName());
            assertEquals(userResult.getUsername(), newUser.getUsername());
            assertEquals(userResult.getEmail(), newUser.getEmail());
        }

        @Test
        @DisplayName("Should throw no such element exception when username is not found")
        void shouldThrowNoSuchElementException() {
            when(userRepository.findByUsername("nonexistent")).thenThrow(new NoSuchElementException("User not found"));

            Exception exception = assertThrows(NoSuchElementException.class, () -> {
                userService.findByUsername("nonexistent");
            });

            assertTrue(exception.getMessage().contains("User not found"));
        }
    }

    @Nested
    class existsByEmailTests {
        @Test
        @DisplayName("should return true if email exists")
        void shouldReturnTrueIfEmailExists() {
            when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(true);
            assertTrue(userService.existsByEmail(newUser.getEmail()));
        }

        @Test
        @DisplayName("should return false if email does not exist")
        void shouldReturnFalseIfEmailDoesNotExist() {
            when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);
            assertFalse(userService.existsByEmail(newUser.getEmail()));
        }
    }

    @Nested
    class existsByUsernameTests {
        @Test
        @DisplayName("Should return true if username exists")
        void shouldReturnTrueIfUsernameExists() {
            when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(true);
            assertTrue(userService.existsByUsername(newUser.getUsername()));
        }
    }

    @Nested
    class registerUserTests {
        @Test
        @DisplayName("Should return user details when register is successful")
        void shouldReturnUserWhenRegisterSuccess() {
            when(userRepository.save(any(User.class))).then(returnsFirstArg());
            User userResult = userService.registerUser(newUser);
            assertEquals(userResult.getEmail(), newUser.getEmail());
            assertEquals(userResult.getUsername(), newUser.getUsername());
        }
    }
}
