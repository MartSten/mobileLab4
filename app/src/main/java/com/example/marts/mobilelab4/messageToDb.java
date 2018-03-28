package com.example.marts.mobilelab4;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marts on 11.03.2018.
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
     * Constructor that only takes a message
     * @param theMessage - the message
     */
    public messageToDb(String theMessage){
        message = theMessage;
        //user = getUsername();
         Date date = new Date();
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.UK);
        this.date = sfd.format(date);
    }

    /**
     * Constructor that takes a message and an username
     * @param messageToDb - the message
     * @param username - the username
     */
    public messageToDb(String messageToDb, String username){
        this.message = messageToDb;
        this.user = username;
        Date date = new Date();
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.UK);
        this.date = sfd.format(date);
        Log.d("TEST", "Creating a new message: " + username + " " + messageToDb + " " + this.date);
    }
}