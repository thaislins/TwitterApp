package com.example.thaislins.twitter.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import static java.lang.System.in;

/**
 * Created by thaislins on 30/08/17.
 */

public class Tweet implements Serializable {

    private User user;
    private String tweet;
    private Date date;

    public Tweet(User user, String tweet) {
        super();
        this.user = user;
        this.tweet = tweet;
        date = new Date();
    }

    /*public Tweet(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        tweet = in.readString();
        date = (Date) in.readSerializable();
    }*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return user + "\n" + tweet + "\n" + date.toString();
    }

}
