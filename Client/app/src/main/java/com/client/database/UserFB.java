package com.client.database;

/**
 * Created by nguye on 12/2/2015.
 */
public class UserFB {

    public String email;

    public String facebookID;

    public UserFB(String email, String facebookID){
        this.email = email;
        this.facebookID = facebookID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

}


