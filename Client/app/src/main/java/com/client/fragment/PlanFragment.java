package com.client.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import com.client.CustomAdapter.CustomPlanList;
import com.client.R;
import com.client.activity.EditPlanActivity;
import com.client.activity.PlanActivity;
import com.client.database.DataBaseHelper;
import com.client.database.ShowDetails;
import com.client.model.MyPlan;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PlanFragment extends Fragment{

    private ListView listPlan;
    private com.melnykov.fab.FloatingActionButton FAB;
    private TextView totalIncome, totalOutcome, total_Money, common_Overview;
    private CustomPlanList adapter;
    private DataBaseHelper dataBaseHelper;
    private ShowDetails showDetails;
    Calendar myCalendar = Calendar.getInstance();
    private String datePick;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.plan_fragment, container, false);
        FacebookSdk.sdkInitialize(rootView.getContext());

        getActivity().setTitle(R.string.nav_drawer_item_plan);

        //Floating action button

        FAB = (com.melnykov.fab.FloatingActionButton) rootView.findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(rootView.getContext(), PlanActivity.class);
                intent.putExtra("update", false);
                startActivityForResult(intent, 1);
            }
        });

        //Custom deal list
        listPlan = (ListView) rootView.findViewById(R.id.listPlanDetails);

        FAB.attachToListView(listPlan);

        adapter = new CustomPlanList(rootView.getContext());
        listPlan.setAdapter(adapter);

        listPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), EditPlanActivity.class);
                intent.putExtra("PId", MyPlan.listPlaniD.get(position - 1));
                intent.putExtra("PMoney", MyPlan.listPlanMoney.get(position - 1));
                intent.putExtra("PDetail", MyPlan.listPlanDetails.get(position - 1));
                if (AccessToken.getCurrentAccessToken() != null) {
                    intent.putExtra("PTypeMoney", UserFB.getIdMoneyTypebyFB());
                } else {
                    intent.putExtra("PTypeMoney", User.getIdMoneyType());
                }
                intent.putExtra("PGroup", MyPlan.listPlanGroup.get(position - 1));
                intent.putExtra("PDate", MyPlan.listPlanDate.get(position - 1));
                intent.putExtra("PGroupImg", MyPlan.listPlanGroupIcon.get(position - 1));
                intent.putExtra("PGroupDetails", MyPlan.listPlanGroupDetailsPos.get(position - 1));
                intent.putExtra("update", true);
                startActivityForResult(intent, 2);
            }
        });

        View header = getLayoutInflater(savedInstanceState).inflate(R.layout.custom_header_listdeal, null);

        common_Overview = (TextView) header.findViewById(R.id.comon_overview);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.US);
        try {
            Date newDate = format.parse(com.client.model.DatePicker.getDate());
            format = new SimpleDateFormat("MM-yyyy", Locale.US);
            datePick = format.format(newDate);
        }catch (java.text.ParseException e){
            e.printStackTrace();
        }

        common_Overview.setText(datePick);

      common_Overview.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              // TODO Auto-generated method stub
              new DatePickerDialog(getActivity(), date, myCalendar
                      .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                      myCalendar.get(Calendar.DAY_OF_MONTH)).show();
          }
      });

        totalIncome = (TextView) header.findViewById(R.id.total_incomeMoney);
        totalOutcome = (TextView) header.findViewById(R.id.total_outcomeMoney);
        total_Money = (TextView) header.findViewById(R.id.total_Money);

        countTotal();

        listPlan.addHeaderView(header, null, false);

        return rootView;
    }

    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null || requestCode == 2){
            if (resultCode == getActivity().RESULT_OK) {
                showDetails = new ShowDetails();
                showDetails.clear_list();
                dataBaseHelper = new DataBaseHelper(getContext());

                showDetails.showDetails(dataBaseHelper);

                countTotal();

            }

            if (resultCode == getActivity().RESULT_CANCELED){

            }
        }

    }

    private void countTotal() {

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        int sumIncome = 0;
        int sumOutcome = 0;
        int number1 [] = new int[MyPlan.listAllIncome.size()];
        int number [] = new int[MyPlan.listAllOutcome.size()];

        for (int i = 0 ; i< MyPlan.listAllIncome.size(); i++) {
            number1 [i] = Integer.parseInt(MyPlan.listAllIncome.get(i).replace(",", ""));
        }

        for (int i = 0; i < number1.length; i++){
            sumIncome += number1[i];
        }

        for (int i = 0 ; i< MyPlan.listAllOutcome.size(); i++) {
            number [i] = Integer.parseInt(MyPlan.listAllOutcome.get(i).replace(",", ""));
        }


        for (int i = 0; i < number.length; i++){
            sumOutcome += number[i];
        }

        String income = numberFormat.format(sumIncome);
        String outcome = numberFormat.format(sumOutcome);

        if (AccessToken.getCurrentAccessToken() != null){
            totalIncome.setText(income + " " + UserFB.getIdMoneyTypebyFB());
            totalOutcome.setText(outcome + " " + UserFB.getIdMoneyTypebyFB());
        }else {
            totalIncome.setText(income + " " + User.getIdMoneyType());
            totalOutcome.setText(outcome + " " + User.getIdMoneyType());
        }

        String totalmoney = numberFormat.format(sumIncome - sumOutcome);

        if (AccessToken.getCurrentAccessToken() != null){
            total_Money.setText(totalmoney + " " + UserFB.getIdMoneyTypebyFB());
        }else {
            total_Money.setText(totalmoney + " " + User.getIdMoneyType());
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

            dataBaseHelper = new DataBaseHelper(getContext());

            showDetails = new ShowDetails();
            showDetails.clear_list();

            showDetails.showDetails(dataBaseHelper);

            adapter.notifyDataSetChanged();
        }

    };


    private void updateLabel() {

        String myFormat = "MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        common_Overview.setText(sdf.format(myCalendar.getTime()));

        String sqlFormat = "yyyy-MM"; //In which you need put here
        SimpleDateFormat getSQL = new SimpleDateFormat(sqlFormat, Locale.US);

        com.client.model.DatePicker.setDate(getSQL.format(myCalendar.getTime()));

    }
}
