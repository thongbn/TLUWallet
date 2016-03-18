package com.client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.client.R;
import com.client.model.MyDeal;
import com.client.model.MyPlan;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class ReportFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_item_report);

        //Income overview

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        int sumIncome = 0;
        int sumOutcome = 0;
        int number1 [] = new int[MyDeal.listAllIncome.size()];
        int number [] = new int[MyDeal.listAllOutcome.size()];

        for (int i = 0 ; i< MyDeal.listAllIncome.size(); i++) {
            number1 [i] = Integer.parseInt(MyDeal.listAllIncome.get(i).replace(",", ""));
        }

        for (int i = 0; i < number1.length; i++){
            sumIncome += number1[i];
        }


        String income = numberFormat.format(sumIncome);

        TextView totalIncome = (TextView) rootView.findViewById(R.id.total_incomeMoneyReport);
        totalIncome.setText(income);


        //Chart deal income

        PieChart mPieChartIncome = (PieChart) rootView.findViewById(R.id.piechart1);

        String incomeName [] = rootView.getResources().getStringArray(R.array.income_categories);
        String outcomeName  [] = rootView.getResources().getStringArray(R.array.outcome_categories);
        int androidColors [] = rootView.getResources().getIntArray(R.array.androidcolors);

        for (int i = 0 ; i < MyDeal.listAllIncome.size(); i++) {
            mPieChartIncome.addPieSlice(new PieModel(incomeName[MyDeal.listAllIncomeGroupDetails.get(i)], (number1[i]*100.0f)/(sumIncome*100.0f)*100, androidColors[new Random().nextInt(androidColors.length)]));
        }

        if (MyDeal.listAllIncome.size() ==  0){
            mPieChartIncome.setVisibility(View.VISIBLE);
        }else {
            mPieChartIncome.startAnimation();
        }


        //Chart deal outcome

        for (int i = 0 ; i< MyDeal.listAllOutcome.size(); i++) {
            number [i] = Integer.parseInt(MyDeal.listAllOutcome.get(i).replace(",", ""));
        }


        for (int i = 0; i < number.length; i++){
            sumOutcome += number[i];
        }

        String outcome = numberFormat.format(sumOutcome);

        TextView totalOutcome = (TextView) rootView.findViewById(R.id.total_expenseMoneyReport);
        totalOutcome.setText(outcome);

        PieChart mPieChartOutcome = (PieChart) rootView.findViewById(R.id.piechart2);

        for (int i = 0 ; i < MyDeal.listAllOutcome.size(); i++) {
            mPieChartOutcome.addPieSlice(new PieModel(outcomeName[MyDeal.listAllOutcomeGroupDetails.get(i)], (number[i]*100.0f)/(sumOutcome*100.0f)*100, androidColors[new Random().nextInt(androidColors.length)]));
        }

        if (MyDeal.listAllOutcome.size() == 0){
            mPieChartOutcome.setVisibility(View.VISIBLE);
        }else {
            mPieChartOutcome.startAnimation();
        }

        //Plan overview

        int sumPlanIncome = 0;
        int sumPlanOutcome = 0;
        int numberPlanIn [] = new int[MyPlan.listAllIncome.size()];
        int numberPlanOut [] = new int[MyPlan.listAllOutcome.size()];

        for (int i = 0 ; i< MyPlan.listAllIncome.size(); i++) {
            numberPlanIn [i] = Integer.parseInt(MyPlan.listAllIncome.get(i).replace(",", ""));
        }

        for (int i = 0; i < numberPlanIn.length; i++){
            sumPlanIncome += numberPlanIn[i];
        }

        for (int i = 0 ; i< MyPlan.listAllOutcome.size(); i++) {
            numberPlanOut [i] = Integer.parseInt(MyPlan.listAllOutcome.get(i).replace(",", ""));
        }


        for (int i = 0; i < numberPlanOut.length; i++){
            sumPlanOutcome += numberPlanOut[i];
        }

        String incomePlan = numberFormat.format(sumPlanIncome);
        String outcomePlan = numberFormat.format(sumPlanOutcome);

        TextView planIncome = (TextView) rootView.findViewById(R.id.total_incomePlanMoneyReport);
        planIncome.setText(incomePlan);

        TextView planOutcome = (TextView) rootView.findViewById(R.id.total_expensePlanMoneyReport);
        planOutcome.setText(outcomePlan);

        //Plan Chart

        PieChart mPieChartOutcomePlan = (PieChart) rootView.findViewById(R.id.piechart4);

        for (int i = 0 ; i < MyPlan.listAllOutcome.size(); i++) {
            mPieChartOutcomePlan.addPieSlice(new PieModel(outcomeName[MyPlan.listAllOutcomePlanGroupDetails.get(i)], (numberPlanOut[i]*100.0f)/(sumPlanOutcome*100.0f)*100, androidColors[new Random().nextInt(androidColors.length)]));
        }

        if (MyPlan.listAllOutcome.size() == 0){
            mPieChartOutcomePlan.setVisibility(View.VISIBLE);
        }else {
            mPieChartOutcomePlan.startAnimation();
        }


        PieChart mPieChartIncomePlan = (PieChart) rootView.findViewById(R.id.piechart3);

        for (int i = 0 ; i < MyPlan.listAllOutcome.size(); i++) {
            mPieChartIncomePlan.addPieSlice(new PieModel(incomeName[MyPlan.listAllIncomePlanGroupDetails.get(i)], (numberPlanIn[i]*100.0f)/(sumPlanIncome*100.0f)*100, androidColors[new Random().nextInt(androidColors.length)]));
        }

        if (MyPlan.listAllOutcome.size() == 0){
            mPieChartIncomePlan.setVisibility(View.VISIBLE);
        }else {
            mPieChartIncomePlan.startAnimation();
        }


        TextView differentIncome = (TextView) rootView.findViewById(R.id.total_incomeDifferentMoneyReport);
        String totalDifferentIncome = numberFormat.format(sumIncome - sumPlanIncome);
        differentIncome.setText(totalDifferentIncome);

        TextView differentOutcome = (TextView) rootView.findViewById(R.id.total_expenseDifferentMoneyReport);
        String totalDifferentOutcome = numberFormat.format(sumOutcome - sumPlanOutcome);
        differentOutcome.setText(totalDifferentOutcome);

        return rootView;
    }

}
