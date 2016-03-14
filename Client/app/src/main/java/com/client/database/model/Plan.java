package com.client.database.model;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class Plan {

    private static String idPlan;
    private static int planGroup;
    private static int planGroupDetailsPos;
    private static int planGroupIcon;
    private static String planMoney;
    private static String planDetail;
    private static User user;
    private static UserFB userFB;
    private static String planDate;

    public Plan (){
        super();
    }

    public static String getIdPlan() {
        return idPlan;
    }

    public static void setIdPlan(String idPlan) {
        Plan.idPlan = idPlan;
    }

    public static int getPlanGroup() {
        return planGroup;
    }

    public static void setPlanGroup(int planGroup) {
        Plan.planGroup = planGroup;
    }

    public static int getPlanGroupDetailsPos() {
        return planGroupDetailsPos;
    }

    public static void setPlanGroupDetailsPos(int planGroupDetailsPos) {
        Plan.planGroupDetailsPos = planGroupDetailsPos;
    }

    public static int getPlanGroupIcon() {
        return planGroupIcon;
    }

    public static void setPlanGroupIcon(int planGroupIcon) {
        Plan.planGroupIcon = planGroupIcon;
    }

    public static String getPlanMoney() {
        return planMoney;
    }

    public static void setPlanMoney(String planMoney) {
        Plan.planMoney = planMoney;
    }

    public static String getPlanDetail() {
        return planDetail;
    }

    public static void setPlanDetail(String planDetail) {
        Plan.planDetail = planDetail;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Plan.user = user;
    }

    public static UserFB getUserFB() {
        return userFB;
    }

    public static void setUserFB(UserFB userFB) {
        Plan.userFB = userFB;
    }

    public static String getPlanDate() {
        return planDate;
    }

    public static void setPlanDate(String planDate) {
        Plan.planDate = planDate;
    }

}
