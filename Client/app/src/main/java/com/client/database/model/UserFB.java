package com.client.database.model;

/**
 * Created by nguye on 12/2/2015.
 */
public class UserFB {

    private static String emailFB;

    private static String facebookID;

    private static String nameFB;

    public String getEmailFB() {
        return emailFB;
    }

    public static void setEmailFB(String emailFB) {
        UserFB.emailFB = emailFB;
    }

    public static String getFacebookID() {
        return facebookID;
    }

    public static void setFacebookID(String facebookID) {
        UserFB.facebookID = facebookID;
    }

    public static String getNameFB() {
        return nameFB;
    }

    public static void setNameFB(String nameFB) {
        UserFB.nameFB = nameFB;
    }
}


