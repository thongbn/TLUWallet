package com.client.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import com.client.CustomAdapter.CustomDealList;
import com.client.R;
import com.client.activity.DealActivity;
import com.client.activity.EditDealActivity;
import com.client.database.DataBaseHelper;
import com.client.database.ShowDetails;
import com.client.model.MyDeal;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DealDetailsFragment extends Fragment{

  private ListView listDeal;
  private com.melnykov.fab.FloatingActionButton FAB;
  private TextView totalIncome, totalOutcome, total_Money;
  private CustomDealList adapter;
  private DataBaseHelper dataBaseHelper;
  private ShowDetails showDetails;
  Calendar myCalendar = Calendar.getInstance();

  @Override
  public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    final View rootView = inflater.inflate(R.layout.deal_details_fragment, container, false);
    FacebookSdk.sdkInitialize(rootView.getContext());

    getActivity().setTitle(R.string.nav_drawer_item_deal_details);

    //Floating action button

    FAB = (com.melnykov.fab.FloatingActionButton) rootView.findViewById(R.id.imageButton);
    FAB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(rootView.getContext(), DealActivity.class);
        startActivityForResult(intent, 1);
      }
    });


    //Custom deal list
    listDeal = (ListView) rootView.findViewById(R.id.listDealDetails);

    FAB.attachToListView(listDeal);

    adapter = new CustomDealList(rootView.getContext());
    listDeal.setAdapter(adapter);

    listDeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(rootView.getContext(), EditDealActivity.class);
        intent.putExtra("DId", MyDeal.listDealiD.get(position-1));
        intent.putExtra("DMoney", MyDeal.listDealMoney.get(position-1));
        intent.putExtra("DDetail", MyDeal.listDealDetails.get(position - 1));
        if (AccessToken.getCurrentAccessToken() != null){
          intent.putExtra("DTypeMoney", UserFB.getIdMoneyTypebyFB());
        }else {
          intent.putExtra("DTypeMoney", User.getIdMoneyType());
        }

        intent.putExtra("DGroup", MyDeal.listDealGroup.get(position-1));
        intent.putExtra("DDate", MyDeal.listDealDate.get(position-1));
        intent.putExtra("DGroupImg", MyDeal.listDealGroupIcon.get(position-1));
        intent.putExtra("DGroupDetails", MyDeal.listDealGroupDetailsPos.get(position-1));
        intent.putExtra("update", true);
        startActivityForResult(intent, 2);
      }
    });

    View header = getLayoutInflater(savedInstanceState).inflate(R.layout.custom_header_listdeal, null);

    DatePicker dp = (DatePicker) header.findViewById(R.id.dp);

    int year = myCalendar.get(Calendar.YEAR);
    int month = myCalendar.get(Calendar.MONTH);
    int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);

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

                countTotal();

                adapter.notifyDataSetChanged();
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

    totalIncome = (TextView) header.findViewById(R.id.total_incomeMoney);
    totalOutcome = (TextView) header.findViewById(R.id.total_outcomeMoney);
    total_Money = (TextView) header.findViewById(R.id.total_Money);

    countTotal();

    listDeal.addHeaderView(header, null, false);

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
        dataBaseHelper = new DataBaseHelper(getContext());

        showDetails = new ShowDetails();
        showDetails.clear_list();

        showDetails.showDetails(dataBaseHelper);

        countTotal();

      }

      if (resultCode == getActivity().RESULT_CANCELED){

      }
    }

  }

  private void countTotal (){

    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
    int sumIncome = 0;
    int sumOutcome = 0;
    int number1 [] = new int[MyDeal.listAllIncome.size()];
    int number [] = new int[MyDeal.listAllOutcome.size()];

    for (int i = 0 ; i< MyDeal.listAllIncome.size(); i++) {
      number1 [i] = Integer.parseInt(MyDeal.listAllIncome.get(i).replace(".", ""));
    }

    for (int i = 0; i < number1.length; i++){
      sumIncome += number1[i];
    }

    for (int i = 0 ; i< MyDeal.listAllOutcome.size(); i++) {
      number [i] = Integer.parseInt(MyDeal.listAllOutcome.get(i).replace(".", ""));
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

}
