package com.chitterchallenge.server.helpers;

import com.chitterchallenge.server.models.Peep;
import com.chitterchallenge.server.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {

    public static List<Peep> peepJsonFileToObjectList() {
        // This is to register the dependency that is needed for mapping
        ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

        try {
            List<Peep> peeps = objectMapper.readValue(
                    JsonFileReader.class.getResourceAsStream("/peepsTestData.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Peep.class)
            );

            return peeps;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<User> userJsonFileToObjectList() {
        ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

        try {
            List<User> users = objectMapper.readValue(
                    JsonFileReader.class.getResourceAsStream("/usersTestData.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, User.class)
            );

            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
