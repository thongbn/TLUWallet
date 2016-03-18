package com.client.model;

import java.util.ArrayList;

/**
 * Created by ToanNguyen on 02/03/2016.
 */
public class MyDeal {

    private static int dealGroup;

    private static int dealGroupImg;

    private static int dealGroupDetailPos;

    private static String dealGroupDetailName;

    public static ArrayList<String> listDealiD = new ArrayList<String>();

    public static ArrayList<Integer> listDealGroup = new ArrayList<Integer>();

    public static ArrayList<String> listDealMoney = new ArrayList<String>();

    public static ArrayList<String> listDealDetails = new ArrayList<String>();

    public static ArrayList<String> listDealDate = new ArrayList<String>();


    public static ArrayList<Integer> listDealGroupDetailsPos = new ArrayList<Integer>();

    public static ArrayList<Integer> listDealGroupIcon = new ArrayList<Integer>();

    public static ArrayList<String> listAllIncome = new ArrayList<String>();

    public static ArrayList<Integer> listAllIncomeGroupDetails = new ArrayList<Integer>();

    public static ArrayList<String> listAllOutcome = new ArrayList<String>();

    public static ArrayList<Integer> listAllOutcomeGroupDetails = new ArrayList<Integer>();

    public  MyDeal (){
        super();
    }

    public static int getDealGroup() {
        return dealGroup;
    }

    public static void setDealGroup(int dealGroup) {
        MyDeal.dealGroup = dealGroup;
    }

    public static int getDealGroupDetailPos() {
        return dealGroupDetailPos;
    }

    public static void setDealGroupDetailPos(int dealGroupDetailPos) {
        MyDeal.dealGroupDetailPos = dealGroupDetailPos;
    }

    public static int getDealGroupImg() {
        return dealGroupImg;
    }

    public static void setDealGroupImg(int dealGroupImg) {
        MyDeal.dealGroupImg = dealGroupImg;
    }

    public static String getDealGroupDetailName() {
        return dealGroupDetailName;
    }

    public static void setDealGroupDetailName(String dealGroupDetailName) {
        MyDeal.dealGroupDetailName = dealGroupDetailName;
    }
}
