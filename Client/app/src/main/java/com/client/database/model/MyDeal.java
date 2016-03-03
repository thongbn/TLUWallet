package com.client.database.model;

import java.util.ArrayList;

/**
 * Created by ToanNguyen on 02/03/2016.
 */
public class MyDeal {

    private static String idDeal;

    private static String dealGroup;

    private static String dealMoney;

    public static String getDealMoneyType() {
        return dealMoneyType;
    }

    public static void setDealMoneyType(String dealMoneyType) {
        MyDeal.dealMoneyType = dealMoneyType;
    }

    private static String dealMoneyType;

    private static String dealDetais;

    private static String dealDate;

    private static String walletID;

    public static ArrayList<String> listDealiD = new ArrayList<String>();

    public static ArrayList<String> listDealGroup = new ArrayList<String>();

    public static ArrayList<String> listDealMoney = new ArrayList<String>();

    public static ArrayList<String> listDealDetails = new ArrayList<String>();

    public static ArrayList<String> listDealDate = new ArrayList<String>();

    public static ArrayList<String> listDealTypeMoney = new ArrayList<String>();

    public  MyDeal (){
        super();
    }

    public static String getIdDeal() {
        return idDeal;
    }

    public static void setIdDeal(String idDeal) {
        MyDeal.idDeal = idDeal;
    }

    public static String getDealGroup() {
        return dealGroup;
    }

    public static void setDealGroup(String dealGroup) {
        MyDeal.dealGroup = dealGroup;
    }

    public static String getDealMoney() {
        return dealMoney;
    }

    public static void setDealMoney(String dealMoney) {
        MyDeal.dealMoney = dealMoney;
    }

    public static String getDealDetais() {
        return dealDetais;
    }

    public static void setDealDetais(String dealDetais) {
        MyDeal.dealDetais = dealDetais;
    }

    public static String getDealDate() {
        return dealDate;
    }

    public static void setDealDate(String dealDate) {
        MyDeal.dealDate = dealDate;
    }

    public static String getWalletID() {
        return walletID;
    }

    public static void setWalletID(String walletID) {
        MyDeal.walletID = walletID;
    }

}
