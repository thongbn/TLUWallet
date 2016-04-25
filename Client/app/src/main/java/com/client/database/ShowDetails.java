package com.client.database;

import com.client.model.DatePicker;
import com.client.model.Deal;
import com.client.model.MyDeal;
import com.client.model.MyPlan;
import com.client.model.Plan;
import com.facebook.AccessToken;

/**
 * Created by ToanNguyen on 16/03/2016.
 */
public class ShowDetails {

    public void clear_list () {
        MyDeal.listDealGroupDetailsPos.clear();
        MyDeal.listDealGroup.clear();
        MyDeal.listDealGroupIcon.clear();
        MyDeal.listDealiD.clear();
        MyDeal.listDealDetails.clear();
        MyDeal.listDealMoney.clear();
        MyDeal.listDealDate.clear();
        MyDeal.listAllIncome.clear();
        MyDeal.listAllOutcome.clear();
        MyDeal.listAllOutcomeGroupDetails.clear();
        MyDeal.listAllIncomeGroupDetails.clear();

        MyPlan.listPlanGroupDetailsPos.clear();
        MyPlan.listPlanGroup.clear();
        MyPlan.listPlanGroupIcon.clear();
        MyPlan.listPlaniD.clear();
        MyPlan.listPlanDetails.clear();
        MyPlan.listPlanMoney.clear();
        MyPlan.listPlanDate.clear();
        MyPlan.listAllIncome.clear();
        MyPlan.listAllOutcome.clear();
        MyPlan.listAllOutcomePlanGroupDetails.clear();
        MyPlan.listAllIncomePlanGroupDetails.clear();
    }

    public void showDetails (DataBaseHelper dataBaseHelper){
        if (AccessToken.getCurrentAccessToken() != null){
            dataBaseHelper.getDealbyFB(Deal.getUserFB().getFacebookID(), DatePicker.getDate());
            dataBaseHelper.getAllIncomebyFB(Deal.getUserFB().getFacebookID(), "1", DatePicker.getDate());
            dataBaseHelper.getAllOutcomebyFB(Deal.getUserFB().getFacebookID(), "2", DatePicker.getDate());

            dataBaseHelper.getPlanbyFB(Plan.getUserFB().getFacebookID(), DatePicker.getDate());
            dataBaseHelper.getAllPlanIncomebyFB(Plan.getUserFB().getFacebookID(), "1", DatePicker.getDate());
            dataBaseHelper.getAllPlanOutcomebyFB(Plan.getUserFB().getFacebookID(), "2", DatePicker.getDate());
        }else {
            dataBaseHelper.getDeal(Deal.getUser().getIdNguoiDung(), DatePicker.getDate());
            dataBaseHelper.getAllIncome(Deal.getUser().getIdNguoiDung(), "1", DatePicker.getDate());
            dataBaseHelper.getAllOutcome(Deal.getUser().getIdNguoiDung(), "2", DatePicker.getDate());

            dataBaseHelper.getPlan(Plan.getUser().getIdNguoiDung(), DatePicker.getDate());
            dataBaseHelper.getAllPlanIncome(Plan.getUser().getIdNguoiDung(), "1", DatePicker.getDate());
            dataBaseHelper.getAllPlanOutcome(Plan.getUser().getIdNguoiDung(), "2", DatePicker.getDate());
        }
    }

}
