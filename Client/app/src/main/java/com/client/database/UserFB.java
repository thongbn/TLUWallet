package com.client.database;

/**
 * Created by nguye on 12/2/2015.
 */
public class UserFB {

    public static String emailFB;

    public static String facebookID;

    public UserFB(){
        super();
    }

    public UserFB(String emailFB, String facebookID){
        this.emailFB = emailFB;
        this.facebookID = facebookID;
    }

    public String getEmailFB() {
        return emailFB;
    }

    public void setEmailFB(String email) {
        this.emailFB = email;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

}


