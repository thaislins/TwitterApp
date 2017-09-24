package com.example.thaislins.twitter.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by thaislins on 24/09/17.
 */

public class DirectMessage implements Serializable {

    private String message;
    private Date date;
    private User user;

    public DirectMessage(Date date, User user, String message) {
        this.date = date;
        this.user = user;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
