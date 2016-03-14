package com.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.client.CustomDealList.CustomDealList;
import com.client.R;
import com.client.activity.DealActivity;
import com.client.activity.EditDealActivity;
import com.client.database.model.MyDeal;
import com.client.database.model.User;
import com.client.database.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.text.NumberFormat;
import java.util.Locale;


public class DealDetailsFragment extends Fragment {

    private ListView listDeal;
    private FloatingActionButton FAB;
    private TextView totalIncome, totalOutcome, total_Money;
    private CustomDealList adapter;

    public DealDetailsFragment (){}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.deal_details_fragment, container, false);
        FacebookSdk.sdkInitialize(rootView.getContext());

        //Floating action button

        FAB = (FloatingActionButton) rootView.findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(rootView.getContext(), DealActivity.class);
                intent.putExtra("update", false);
                startActivity(intent);
            }
        });

        //Custom deal list
        listDeal = (ListView) rootView.findViewById(R.id.listDealDetails);

        adapter = new CustomDealList(rootView.getContext());
        listDeal.setAdapter(adapter);

        listDeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), EditDealActivity.class);
                intent.putExtra("DId", MyDeal.listDealiD.get(position));
                intent.putExtra("DMoney", MyDeal.listDealMoney.get(position));
                intent.putExtra("DDetail", MyDeal.listDealDetails.get(position));
                intent.putExtra("DTypeMoney", User.getIdMoneyType());
                intent.putExtra("DGroup", MyDeal.listDealGroup.get(position));
                intent.putExtra("DDate", MyDeal.listDealDate.get(position));
                intent.putExtra("DGroupImg", MyDeal.listDealGroupIcon.get(position));
                intent.putExtra("DGroupDetails", MyDeal.listDealGroupDetailsPos.get(position));
                intent.putExtra("update", true);
                startActivity(intent);
            }
        });

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        int sumIncome = 0;
        int sumOutcome = 0;
        int number1 [] = new int[MyDeal.listAllIncome.size()];
        int number [] = new int[MyDeal.listAllOutcome.size()];

        totalIncome = (TextView) rootView.findViewById(R.id.total_incomeMoney);
        totalOutcome = (TextView) rootView.findViewById(R.id.total_outcomeMoney);
        total_Money = (TextView) rootView.findViewById(R.id.total_Money);


        for (int i = 0 ; i< MyDeal.listAllIncome.size(); i++) {
            number1 [i] = Integer.parseInt(MyDeal.listAllIncome.get(i).replace(",", ""));
        }

        for (int i = 0; i < number1.length; i++){
            sumIncome += number1[i];
        }

        for (int i = 0 ; i< MyDeal.listAllOutcome.size(); i++) {
            number [i] = Integer.parseInt(MyDeal.listAllOutcome.get(i).replace(",", ""));
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


        return rootView;
    }

    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
