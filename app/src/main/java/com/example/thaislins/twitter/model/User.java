package com.example.thaislins.twitter.model;

import java.io.Serializable;

/**
 * Created by thaislins on 22/09/17.
 */

public class User implements Serializable {

    private String username;
    private String name;
    private String email;
    private String phone;

    public User(String username, String name, String email, String phone) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() { return username; }

    public void setUsername() { this.username = name; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
