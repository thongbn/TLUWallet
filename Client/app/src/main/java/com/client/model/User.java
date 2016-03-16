package com.client.model;

/**
 * Created by nguye on 12/2/2015.
 */
public class User {

    private static String idNguoiDung;

    private static String email;

    private static String password;

    private static String idMoneyType;

    public User (){
        super();
    }

    public static String getIdNguoiDung() {
        return idNguoiDung;
    }

    public static void setIdNguoiDung(String idNguoiDung) {
        User.idNguoiDung = idNguoiDung;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getIdMoneyType() {
        return idMoneyType;
    }

    public static void setIdMoneyType(String idMoneyType) {
        User.idMoneyType = idMoneyType;
    }


}
