package com.chitterchallenge.server.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Peep {
    @Id private String id;

    private String username;
    private String name;
    private Date dateCreated;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPeep() {
        return peep;
    }

    public void setPeep(String peep) {
        this.peep = peep;
    }

    private String peep;
}
