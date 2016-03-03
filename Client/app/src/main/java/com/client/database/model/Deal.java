package com.client.database.model;

import java.util.Date;

/**
 * Created by quang on 30/12/2015.
 */
public class Deal {
    private static String idDeal;
    private static String dealGroup;
    private static String dealMoney;
    private static String dealTypeMoney;
    private static String dealDetail;

    public static String getDealDate() {
        return dealDate;
    }

    public static void setDealDate(String dealDate) {
        Deal.dealDate = dealDate;
    }

    //    private static Date dealDate;
    private static String dealDate;

    private static String wallet;

    public Deal (){
        super();
    }

    public static String getIdDeal() {
        return idDeal;
    }

    public static void setIdDeal(String idDeal) {
        Deal.idDeal = idDeal;
    }

    public static String getDealGroup(){
        return  dealGroup;
    }

    public static void setDealGroup(String dealGroup){
        Deal.dealGroup = dealGroup;
    }

    public static String getDealMoney(){
        return dealMoney;
    }

    public static void setDealMoney(String dealMoney){
        Deal.dealMoney = dealMoney;
    }

    public static String getDealDetail(){
        return dealDetail;
    }

    public static void setDealDetail(String dealDetail){
        Deal.dealDetail = dealDetail;
    }

//    public static Date getDealDate(){
//        return dealDate;
//    }
//
//    public static void setDealDate(Date dealDate){
//        Deal.dealDate = dealDate;
//    }


    public static String getDealTypeMoney() {
        return dealTypeMoney;
    }

    public static void setDealTypeMoney(String dealTypeMoney) {
        Deal.dealTypeMoney = dealTypeMoney;
    }

    public static String getWallet() {
        return wallet;
    }

    public static void setWallet(String wallet) {
        Deal.wallet = wallet;
    }
}
