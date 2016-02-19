package com.client.database.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye on 1/7/2016.
 */
public class MyWallet {
    private static String idWallet;

    private static String walletName;

    private static String walletMoney;

    private static String walletType;

    private static User user;

    private static UserFB userFB;

    public static ArrayList<String> listWalletMoneyType = new ArrayList<String>();

    public static ArrayList<String> listWalletName = new ArrayList<String>();

    public static ArrayList<String> listWalletMoney = new ArrayList<String>();

    public static ArrayList<String> listWalletID = new ArrayList<String>();

    public  MyWallet (){
        super();
    }

    public static String getIdWallet() {
        return idWallet;
    }

    public static void setIdWallet(String idWallet) {
        MyWallet.idWallet = idWallet;
    }

    public static String getWalletName() {
        return walletName;
    }

    public static void setWalletName(String walletName) {
        MyWallet.walletName = walletName;
    }

    public static String getWalletMoney() {
        return walletMoney;
    }

    public static void setWalletMoney(String walletMoney) {
        MyWallet.walletMoney = walletMoney;
    }

    public static String getWalletType(){
        return walletType;
    }
    public static void setWalletType(String walletType){
        MyWallet.walletType = walletType;
    }

    public static User getUser(){
        return  user;
    }
    public static void setUser(User user){
        MyWallet.user = user;
    }

    public static UserFB getUserFB(){
        return userFB;
    }
    public static void setUserFB (UserFB userFB){
        MyWallet.userFB = userFB;
    }
}

