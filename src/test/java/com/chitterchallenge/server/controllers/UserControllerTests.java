package com.chitterchallenge.server.controllers;

import com.chitterchallenge.server.TestMongoConfig;
import com.chitterchallenge.server.exceptions.IncorrectDetailsException;
import com.chitterchallenge.server.exceptions.UserEmailRegisteredException;
import com.chitterchallenge.server.exceptions.UsernameTakenException;
import com.chitterchallenge.server.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.chitterchallenge.server.helpers.JsonFileReader.userJsonFileToObjectList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private String requestBody;

    private List<User> users = userJsonFileToObjectList();

    @BeforeEach
    void clearAndRepopulateCollection() {
        TestMongoConfig.clearCollection("users");
        TestMongoConfig.repopulateUsersCollection(users);
    }

    @Nested
    @DisplayName("User login tests")
    class UserLoginTests {

        private String validRequestBody = "{\"email\": \"" + users.get(0).getEmail() +
                "\",\"password\": \"" + users.get(0).getPassword() +
                "\"}";
        @Test
        @DisplayName("Should throw an Incorrect Details Exception when user tries to login with an " +
                "unregistered email")
        void throwIncorrectDetailsExceptionWhenLoginUnregisteredEmail() throws Exception {
            requestBody = "{\"email\": \"" + "invalid@email.com" +
                    "\",\"password\": \"" + "password" +
                    "\"}";

            mockMvc.perform(
                    post("/user/login")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectDetailsException));
        }

        @Test
        @DisplayName("Should throw Incorrect Details Exception when user uses wrong password")
        void throwIncorrectDetailsExceptionWhenLoginWrongPassword() throws Exception {
            requestBody = "{\"email\": \"" + "john@example.com" +
                    "\",\"password\": \"" + "password" +
                    "\"}";

            mockMvc.perform(
                    post("/user/login")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectDetailsException));
        }

        @Test
        @DisplayName("Should return status 200 when login success")
        void shouldReturnStatus200WhenLoginSuccess() throws Exception {
            mockMvc.perform(
                    post("/user/login")
                            .content(validRequestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

        }

        @Test
        @DisplayName("Should return User DTO with message when login success")
        void shouldReturnUserDTOWhenLoginSuccess() throws Exception{
            mockMvc.perform(
                    post("/user/login")
                            .content(validRequestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpectAll(
                    jsonPath("$.message", is("Login success")),
                    jsonPath("$.user.name", is(users.get(0).getName())),
                    jsonPath("$.user.username", is(users.get(0).getUsername()))
            );
        }
    }

    @Nested
    @DisplayName("User Register Tests")
    class UserRegisterTests {
        @Test
        @DisplayName("Should throw UserEmailRegisteredException when email is already registered")
        void shouldThrowUserEmailRegisteredExceptionWhenEmailIsRegistered() throws Exception {
            requestBody = "{\"email\": \"" + users.get(0).getEmail() +
                    "\",\"password\": \"" + users.get(0).getPassword() +
                    "\",\"name\": \"" + users.get(0).getName() +
                    "\",\"username\": \"" + users.get(0).getUsername() +
                    "\"}";

            mockMvc.perform(
                    post("/user/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            ).andExpectAll(
                    result -> assertTrue(result.getResolvedException() instanceof UserEmailRegisteredException),
                    status().isBadRequest(),
                    status().reason("Email has already been registered")
            );
        }

        @Test
        @DisplayName("Should throw UsernameTakenException when username is already taken")
        void shouldThrowUsernameTakenExceptionWhenUsernameIsTaken() throws Exception {
            requestBody = "{\"email\": \"" + "invalid@email.com" +
                    "\",\"password\": \"" + users.get(0).getPassword() +
                    "\",\"name\": \"" + users.get(0).getName() +
                    "\",\"username\": \"" + users.get(0).getUsername() +
                    "\"}";

            mockMvc.perform(
                    post("/user/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            ).andExpectAll(
                    result -> assertTrue(result.getResolvedException() instanceof UsernameTakenException),
                    status().isBadRequest(),
                    status().reason("Username is taken")
            );
        }


        @Test
        @DisplayName("Should return status code 200 and user DTO when register success")
        void shouldReturnStatus200AndUserDTOWhenRegisterSuccess() throws Exception {
            requestBody = "{\"email\": \"" + "john1test@example.com" +
                    "\",\"password\": \"" + "asd" +
                    "\",\"name\": \"" + "John test" +
                    "\",\"username\": \"" + "John 1 test" +
                    "\"}";

            mockMvc.perform(
                    post("/user/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpectAll(
                    status().isOk(),
                    jsonPath("$.message", is("Register success")),
                    jsonPath("$.user.username", is("John 1 test")),
                    jsonPath("$.user.name", is("John test"))
            );
        }

    }
}
