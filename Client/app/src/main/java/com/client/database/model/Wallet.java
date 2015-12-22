package com.client.database.model;

/**
 * Created by quang on 21/12/2015.
 */
public class Wallet {
    private static String idWallet;

    private static String walletName;

    private static String walletMoney;

    private static String walletType;

    private static User user;

    private static UserFB userFB;

    public Wallet (){
        super();
    }

    public static String getIdWallet() {
        return idWallet;
    }

    public static void setIdWallet(String idWallet) {
        Wallet.idWallet = idWallet;
    }

    public static String getWalletName() {
        return walletName;
    }

    public static void setWalletName(String walletName) {
        Wallet.walletName = walletName;
    }

    public static String getWalletMoney() {
        return walletMoney;
    }

    public static void setWalletMoney(String walletMoney) {
        Wallet.walletMoney = walletMoney;
    }

    public static String getWalletType(){
        return walletType;
    }
    public static void setWalletType(String walletType){
        Wallet.walletType = walletType;
    }

    public static User getUser(){
        return  user;
    }
    public static void setUser(User user){
        Wallet.user = user;
    }

    public static UserFB getUserFB(){
        return userFB;
    }
    public static void setUserFB (UserFB userFB){
        Wallet.userFB = userFB;
    }
}
