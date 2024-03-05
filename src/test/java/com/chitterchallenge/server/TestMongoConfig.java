package com.chitterchallenge.server;

import com.chitterchallenge.server.model.Peep;
import com.chitterchallenge.server.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Configuration
public class TestMongoConfig {
    @Bean
    public static MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(
                "mongodb://127.0.0.1:27017/chitterSpringBootTest"
        ));
    }

    public static void clearCollection(String collectionName) {
        System.out.println("Deleting existing " + collectionName);
        mongoTemplate().remove(new Query(), collectionName);
    }

    public static void repopulatePeepsCollection(List<Peep> peeps) {
        System.out.println("Creating peeps");
        System.out.println("Inserting peeps");
        mongoTemplate().insert(peeps, "peeps");
    }

    public static void repopulateUsersCollection(List<User> users) {
        System.out.println("Creating users");
        System.out.println("Inserting users");
        mongoTemplate().insert(users, "users");
    }
}
