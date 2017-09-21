package com.example.thaislins.twitter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by thaislins on 30/08/17.
 */

public class Tweet implements Serializable {

    private String username;
    private String tweet;
    private Date date;

    public Tweet(String username, String tweet) {
        this.username = username;
        this.tweet = tweet;
        date = new Date();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return username + "\n" + tweet + "\n" + date.toString();
    }
}
