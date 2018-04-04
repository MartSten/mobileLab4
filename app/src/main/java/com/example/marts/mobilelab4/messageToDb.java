package com.example.marts.mobilelab4;


import android.util.Log;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by marts on 11.03.2018.
 * The Class is used to create a message that is sent to the db
 */

public class messageToDb {
    //THESE HAVE TO BE PUBLIC FOR FireDatabase TO WORK
    public String message;
    public String date;
    public String user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Constructor that takes a message and an username
     * @param messageToDb - the message
     * @param username - the username
     */
    messageToDb(String messageToDb, String username){
        this.message = messageToDb;
        this.user = username;
        Date date = new Date();
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.UK);
        this.date = sfd.format(date);
        Log.d("TEST", "Creating a new message: " + username + " " + messageToDb + " " + this.date);
    }

    //dataSnapShot.getValue() needs an empty constructor
    messageToDb(){}

}