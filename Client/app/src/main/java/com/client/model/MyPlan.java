package com.client.model;

import java.util.ArrayList;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class MyPlan {

    public static ArrayList<String> listPlaniD = new ArrayList<String>();

    public static ArrayList<Integer> listPlanGroup = new ArrayList<Integer>();

    public static ArrayList<String> listPlanMoney = new ArrayList<String>();

    public static ArrayList<String> listPlanDetails = new ArrayList<String>();

    public static ArrayList<String> listPlanDate = new ArrayList<String>();

    public static ArrayList<Integer> listPlanGroupDetailsPos = new ArrayList<Integer>();

    public static ArrayList<Integer> listPlanGroupIcon = new ArrayList<Integer>();

    public static ArrayList<String> listAllIncome = new ArrayList<String>();

    public static ArrayList<Integer> listAllIncomePlanGroupDetails = new ArrayList<Integer>();

    public static ArrayList<String> listAllOutcome = new ArrayList<String>();

    public static ArrayList<Integer> listAllOutcomePlanGroupDetails = new ArrayList<Integer>();

    private static int planGroup;

    private static int planGroupImg;

    private static int planGroupDetailPos;

    private static String planGroupDetailName;

    public  MyPlan (){
        super();
    }

    public static int getPlanGroup() {
        return planGroup;
    }

    public static void setPlanGroup(int planGroup) {
        MyPlan.planGroup = planGroup;
    }

    public static int getPlanGroupImg() {
        return planGroupImg;
    }

    public static void setPlanGroupImg(int planGroupImg) {
        MyPlan.planGroupImg = planGroupImg;
    }

    public static int getPlanGroupDetailPos() {
        return planGroupDetailPos;
    }

    public static void setPlanGroupDetailPos(int planGroupDetailPos) {
        MyPlan.planGroupDetailPos = planGroupDetailPos;
    }

    public static String getPlanGroupDetailName() {
        return planGroupDetailName;
    }

    public static void setPlanGroupDetailName(String planGroupDetailName) {
        MyPlan.planGroupDetailName = planGroupDetailName;
    }

}
