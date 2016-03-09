package com.client.database.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by ToanNguyen on 02/03/2016.
 */
public class MyDeal {

    private static String idDeal;

    private static String dealGroup;

    private static Drawable dealGroupImg;

    private static String dealMoney;

    private static String dealMoneyType;

    private static String dealDetais;

    private static String dealDate;

    private static int dealGroupDetailPos;

    private static String dealGroupDetailName;

    public static ArrayList<String> listDealiD = new ArrayList<String>();

    public static ArrayList<String> listDealGroup = new ArrayList<String>();

    public static ArrayList<String> listDealMoney = new ArrayList<String>();

    public static ArrayList<String> listDealDetails = new ArrayList<String>();

    public static ArrayList<String> listDealDate = new ArrayList<String>();

    public static ArrayList<String> listDealTypeMoney = new ArrayList<String>();

    public static ArrayList<Integer> listDealGroupDetails = new ArrayList<Integer>();

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

    public static int getDealGroupDetailPos() {
        return dealGroupDetailPos;
    }

    public static void setDealGroupDetailPos(int dealGroupDetailPos) {
        MyDeal.dealGroupDetailPos = dealGroupDetailPos;
    }

    public static Drawable getDealGroupImg() {
        return dealGroupImg;
    }

    public static void setDealGroupImg(Drawable dealGroupImg) {
        MyDeal.dealGroupImg = dealGroupImg;
    }

    public static String getDealMoneyType() {
        return dealMoneyType;
    }

    public static void setDealMoneyType(String dealMoneyType) {
        MyDeal.dealMoneyType = dealMoneyType;
    }


    public static String getDealGroupDetailName() {
        return dealGroupDetailName;
    }

    public static void setDealGroupDetailName(String dealGroupDetailName) {
        MyDeal.dealGroupDetailName = dealGroupDetailName;
    }
}
