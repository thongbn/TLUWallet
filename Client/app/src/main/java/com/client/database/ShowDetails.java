package com.client.database;

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

        MyPlan.listPlanGroupDetailsPos.clear();
        MyPlan.listPlanGroup.clear();
        MyPlan.listPlanGroupIcon.clear();
        MyPlan.listPlaniD.clear();
        MyPlan.listPlanDetails.clear();
        MyPlan.listPlanMoney.clear();
        MyPlan.listPlanDate.clear();
        MyPlan.listAllIncome.clear();
        MyPlan.listAllOutcome.clear();
    }

    public void showDetails (DataBaseHelper dataBaseHelper){
        if (AccessToken.getCurrentAccessToken() != null){
            dataBaseHelper.getDealbyFB(Deal.getUserFB().getFacebookID());
            dataBaseHelper.getAllIncomebyFB(Deal.getUserFB().getFacebookID(), "1");
            dataBaseHelper.getAllOutcomebyFB(Deal.getUserFB().getFacebookID(), "2");

            dataBaseHelper.getPlanbyFB(Plan.getUserFB().getFacebookID());
            dataBaseHelper.getAllPlanIncomebyFB(Plan.getUserFB().getFacebookID(), "1");
            dataBaseHelper.getAllPlanOutcomebyFB(Plan.getUserFB().getFacebookID(), "2");
        }else {
            dataBaseHelper.getDeal(Deal.getUser().getIdNguoiDung());
            dataBaseHelper.getAllIncome(Deal.getUser().getIdNguoiDung(), "1");
            dataBaseHelper.getAllOutcome(Deal.getUser().getIdNguoiDung(), "2");

            dataBaseHelper.getPlan(Plan.getUser().getIdNguoiDung());
            dataBaseHelper.getAllPlanIncome(Plan.getUser().getIdNguoiDung(), "1");
            dataBaseHelper.getAllPlanOutcome(Plan.getUser().getIdNguoiDung(), "2");
        }
    }

}
