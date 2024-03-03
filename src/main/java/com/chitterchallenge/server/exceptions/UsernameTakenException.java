package com.chitterchallenge.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Username is taken")
public class UsernameTakenException extends RuntimeException {
}
