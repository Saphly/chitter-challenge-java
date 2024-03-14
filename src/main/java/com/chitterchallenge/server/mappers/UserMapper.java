package com.chitterchallenge.server.mappers;

import com.chitterchallenge.server.dtos.UserDto;
import com.chitterchallenge.server.models.User;

public class UserMapper {
    public static UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto(user.getName(), user.getUsername());
        return userDto;
    }
}
