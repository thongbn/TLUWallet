package com.client.database;

/**
 * Created by nguye on 12/2/2015.
 */
public class User {

    private static String idNguoiDung;

    private static String email;

    private static String password;

    public User(){
        super();
    }

    public User (String email, String password){
        this.email = email;
        this.password = password;
    }

    public User (String idNguoiDung, String email, String password){
        this.idNguoiDung = idNguoiDung;
        this.email = email;
        this.password = password;
    }

    public String getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(String idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
