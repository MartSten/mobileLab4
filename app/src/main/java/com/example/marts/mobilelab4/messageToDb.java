package com.example.marts.mobilelab4;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marts on 11.03.2018.
 */

public class messageToDb {
    String message;
    Date date;
    String user;

    public messageToDb(String theMessage){
        message = theMessage;
        //user = getUsername();
        date = new Date();
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        sfd.format(date);
    }

    public messageToDb(String messageToDb, String username){
        message = messageToDb;
        user = username;
        date = new Date();
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        sfd.format(date);
    }
}