package com.client.model;

/**
 * Created by quang on 30/12/2015.
 */
public class Deal {
    private static String idDeal;
    private static int dealGroup;
    private static int dealGroupDetailsPos;

    public static int getDealGroupIcon() {
        return dealGroupIcon;
    }

    public static void setDealGroupIcon(int dealGroupIcon) {
        Deal.dealGroupIcon = dealGroupIcon;
    }

    private static int dealGroupIcon;
    private static String dealMoney;
    private static String dealDetail;
    private static User user;
    private static UserFB userFB;
    private static String dealDate;

    public Deal (){
        super();
    }

    public static String getIdDeal() {
        return idDeal;
    }

    public static void setIdDeal(String idDeal) {
        Deal.idDeal = idDeal;
    }

    public static int getDealGroup(){
        return  dealGroup;
    }

    public static void setDealGroup(int dealGroup){
        Deal.dealGroup = dealGroup;
    }

    public static int getDealGroupDetailsPos() {
        return dealGroupDetailsPos;
    }

    public static void setDealGroupDetailsPos(int dealGroupDetailsPos) {
        Deal.dealGroupDetailsPos = dealGroupDetailsPos;
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

    public static String getDealDate() {
        return dealDate;
    }

    public static void setDealDate(String dealDate) {
        Deal.dealDate = dealDate;
    }

    public static User getUser(){
        return  user;
    }
    public static void setUser(User user){
        Deal.user = user;
    }

    public static UserFB getUserFB(){
        return userFB;
    }
    public static void setUserFB (UserFB userFB){
        Deal.userFB = userFB;
    }

}
