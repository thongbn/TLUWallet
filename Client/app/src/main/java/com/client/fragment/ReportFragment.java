package com.client.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.ShowDetails;
import com.client.model.MyDeal;
import com.client.model.MyPlan;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReportFragment extends Fragment {

    public static Fragment newInstance() {
        return new ReportFragment();
    }

    private DataBaseHelper dataBaseHelper;
    private ShowDetails showDetails;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.report_fragment, container, false);

        FacebookSdk.sdkInitialize(rootView.getContext());

        getActivity().setTitle(R.string.nav_drawer_item_report);

        DatePicker dp = (DatePicker) rootView.findViewById(R.id.dp);

        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);

        String myFormat = "yyyy-MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        com.client.model.DatePicker date = new com.client.model.DatePicker();

        date.setDate(sdf.format(myCalendar.getTime()));

        dataBaseHelper = new DataBaseHelper(getContext());

        showDetails = new ShowDetails();
        showDetails.clear_list();

        showDetails.showDetails(dataBaseHelper);

        chart(rootView);


        dp.init(
                year,
                month,
                dayOfMonth,
                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(
                            DatePicker view,
                            int year,
                            int monthOfYear,
                            int dayOfMonth)
                    {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String sqlFormat = "yyyy-MM"; //format put into sql
                        SimpleDateFormat getSQL = new SimpleDateFormat(sqlFormat, Locale.US);

                        com.client.model.DatePicker date = new com.client.model.DatePicker();

                        date.setDate(getSQL.format(myCalendar.getTime()));

                        dataBaseHelper = new DataBaseHelper(getContext());

                        showDetails = new ShowDetails();
                        showDetails.clear_list();

                        showDetails.showDetails(dataBaseHelper);

                        chart(rootView);

                    }
                });

        int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
        if (daySpinnerId != 0)
        {
            View daySpinner = dp.findViewById(daySpinnerId);
            if (daySpinner != null)
            {
                daySpinner.setVisibility(View.GONE);
            }
        }

        int monthSpinnerId = Resources.getSystem().getIdentifier("month", "id", "android");
        if (monthSpinnerId != 0)
        {
            View monthSpinner = dp.findViewById(monthSpinnerId);
            if (monthSpinner != null)
            {
                monthSpinner.setVisibility(View.VISIBLE);
            }
        }

        int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
        if (yearSpinnerId != 0)
        {
            View yearSpinner = dp.findViewById(yearSpinnerId);
            if (yearSpinner != null)
            {
                yearSpinner.setVisibility(View.VISIBLE);
            }
        }

        return rootView;
    }


    public void chart (View rootView) {

        //Income overview

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        String income_text = getString(R.string.common_income);
        String outcome_text = getString(R.string.common_outcome);
        String plan_income_text = getString(R.string.common_income_plan);
        String plan_outcome_text = getString(R.string.common_outcome_plan);
        int sumIncome = 0;
        int number1 [] = new int[MyDeal.listAllIncome.size()];


        for (int i = 0 ; i< MyDeal.listAllIncome.size(); i++) {
            number1 [i] = Integer.parseInt(MyDeal.listAllIncome.get(i).replace(",", ""));
        }

        for (int i = 0; i < number1.length; i++){
            sumIncome += number1[i];
        }


        String income = numberFormat.format(sumIncome);

        TextView totalIncome = (TextView) rootView.findViewById(R.id.total_incomeMoneyReport);

        //Chart deal income

        PieChart mPieChart1 = (PieChart) rootView.findViewById(R.id.piechart1);
        drawChart(rootView, number1, sumIncome, MyDeal.listAllIncome, MyDeal.listAllIncomeGroupDetails, mPieChart1, R.array.income_categories, income_text);



        //Outcome overview

        int sumOutcome = 0;
        int number [] = new int[MyDeal.listAllOutcome.size()];

        for (int i = 0 ; i< MyDeal.listAllOutcome.size(); i++) {
            number [i] = Integer.parseInt(MyDeal.listAllOutcome.get(i).replace(",", ""));
        }


        for (int i = 0; i < number.length; i++){
            sumOutcome += number[i];
        }

        String outcome = numberFormat.format(sumOutcome);

        TextView totalOutcome = (TextView) rootView.findViewById(R.id.total_expenseMoneyReport);

        //Chart deal outcome

        PieChart mPieChart2 = (PieChart) rootView.findViewById(R.id.piechart2);
        drawChart(rootView, number, sumOutcome, MyDeal.listAllOutcome, MyDeal.listAllOutcomeGroupDetails, mPieChart2, R.array.outcome_categories, outcome_text);


        //Plan income Overview
        int sumPlanIncome = 0;
        int numberPlanIn [] = new int[MyPlan.listAllIncome.size()];

        for (int i = 0 ; i< MyPlan.listAllIncome.size(); i++) {
            numberPlanIn [i] = Integer.parseInt(MyPlan.listAllIncome.get(i).replace(",", ""));
        }

        for (int i = 0; i < numberPlanIn.length; i++){
            sumPlanIncome += numberPlanIn[i];
        }

        String incomePlan = numberFormat.format(sumPlanIncome);
        TextView planIncome = (TextView) rootView.findViewById(R.id.total_incomePlanMoneyReport);

        //Chart income plan

        PieChart mPieChart3 = (PieChart) rootView.findViewById(R.id.piechart3);
        drawChart(rootView, numberPlanIn, sumPlanIncome, MyPlan.listAllIncome, MyPlan.listAllIncomePlanGroupDetails, mPieChart3, R.array.income_categories, plan_income_text);


        //Plan outcome Overview
        int sumPlanOutcome = 0;
        int numberPlanOut [] = new int[MyPlan.listAllOutcome.size()];

        for (int i = 0 ; i< MyPlan.listAllOutcome.size(); i++) {
            numberPlanOut [i] = Integer.parseInt(MyPlan.listAllOutcome.get(i).replace(",", ""));
        }


        for (int i = 0; i < numberPlanOut.length; i++){
            sumPlanOutcome += numberPlanOut[i];
        }

        String outcomePlan = numberFormat.format(sumPlanOutcome);
        TextView planOutcome = (TextView) rootView.findViewById(R.id.total_expensePlanMoneyReport);

        //Chart outcome plan

        PieChart mPieChart4 = (PieChart) rootView.findViewById(R.id.piechart4);
        drawChart(rootView, numberPlanOut, sumPlanOutcome, MyPlan.listAllOutcome, MyPlan.listAllOutcomePlanGroupDetails, mPieChart4, R.array.outcome_categories, plan_outcome_text);

        TextView differentIncome = (TextView) rootView.findViewById(R.id.total_incomeDifferentMoneyReport);
        String totalDifferentIncome = numberFormat.format(sumIncome - sumPlanIncome);


        TextView differentOutcome = (TextView) rootView.findViewById(R.id.total_expenseDifferentMoneyReport);
        String totalDifferentOutcome = numberFormat.format(sumOutcome - sumPlanOutcome);

        if (AccessToken.getCurrentAccessToken() != null){
            totalIncome.setText(income + " " + UserFB.getIdMoneyTypebyFB());
            totalOutcome.setText(outcome + " " + UserFB.getIdMoneyTypebyFB());
            planIncome.setText(incomePlan + " " + UserFB.getIdMoneyTypebyFB());
            planOutcome.setText(outcomePlan + " " + UserFB.getIdMoneyTypebyFB());
            differentIncome.setText(totalDifferentIncome + " " + UserFB.getIdMoneyTypebyFB());
            differentOutcome.setText(totalDifferentOutcome + " " + UserFB.getIdMoneyTypebyFB());
        }else {
            totalIncome.setText(income + " " + User.getIdMoneyType());
            totalOutcome.setText(outcome + " " + User.getIdMoneyType());
            planIncome.setText(incomePlan + " " + User.getIdMoneyType());
            planOutcome.setText(outcomePlan + " " + User.getIdMoneyType());
            differentIncome.setText(totalDifferentIncome + " " + User.getIdMoneyType());
            differentOutcome.setText(totalDifferentOutcome + " " + User.getIdMoneyType());
        }

    }

    public void drawChart (View rootView, int number1 [], int sum, ArrayList<String> list, ArrayList<Integer> listGroupDetails, PieChart mPieChart, int array, String insidetext){

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i< list.size(); i++) {

            entries.add(new Entry((number1[i]*100.0f)/(sum*100.0f)*100, i));

        }
        mPieChart.getLegend().setEnabled(false);

        ArrayList<String> labels = new ArrayList<String>();
        String name [] = rootView.getResources().getStringArray(array);
        for (int i = 0; i< list.size(); i++) {
            labels.add(name[listGroupDetails.get(i)]);
        }
        PieDataSet dataset = new PieDataSet(entries, null);
        PieData data = new PieData(labels, dataset);
        mPieChart.setData(data);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        mPieChart.animateY(1500);
        mPieChart.setDescription("");
        mPieChart.setDrawHoleEnabled(false);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(6.5f);
        dataset.setSliceSpace(4);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setCenterText(insidetext);

    }

    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + " %"; // e.g. append a dollar-sign
        }
    }


}
