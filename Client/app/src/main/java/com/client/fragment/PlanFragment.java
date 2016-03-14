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
import com.client.CustomAdapter.CustomPlanList;
import com.client.R;
import com.client.activity.EditPlanActivity;
import com.client.activity.PlanActivity;
import com.client.database.model.MyPlan;
import com.client.database.model.User;
import com.client.database.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.text.NumberFormat;
import java.util.Locale;

public class PlanFragment extends Fragment {

    private ListView listPlan;
    private FloatingActionButton FAB;
    private TextView totalIncome, totalOutcome, total_Money;
    private CustomPlanList adapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.plan_fragment, container, false);
        FacebookSdk.sdkInitialize(rootView.getContext());

        //Floating action button

        FAB = (FloatingActionButton) rootView.findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(rootView.getContext(), PlanActivity.class);
                intent.putExtra("update", false);
                startActivity(intent);
            }
        });

        //Custom deal list
        listPlan = (ListView) rootView.findViewById(R.id.listPlanDetails);

        adapter = new CustomPlanList(rootView.getContext());
        listPlan.setAdapter(adapter);

        listPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), EditPlanActivity.class);
                intent.putExtra("PId", MyPlan.listPlaniD.get(position));
                intent.putExtra("PMoney", MyPlan.listPlanMoney.get(position));
                intent.putExtra("PDetail", MyPlan.listPlanDetails.get(position));
                intent.putExtra("PTypeMoney", User.getIdMoneyType());
                intent.putExtra("PGroup", MyPlan.listPlanGroup.get(position));
                intent.putExtra("PDate", MyPlan.listPlanDate.get(position));
                intent.putExtra("PGroupImg", MyPlan.listPlanGroupIcon.get(position));
                intent.putExtra("PGroupDetails", MyPlan.listPlanGroupDetailsPos.get(position));
                intent.putExtra("update", true);
                startActivity(intent);
            }
        });

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        int sumIncome = 0;
        int sumOutcome = 0;
        int number1 [] = new int[MyPlan.listAllIncome.size()];
        int number [] = new int[MyPlan.listAllOutcome.size()];

        totalIncome = (TextView) rootView.findViewById(R.id.total_incomeMoney);
        totalOutcome = (TextView) rootView.findViewById(R.id.total_outcomeMoney);
        total_Money = (TextView) rootView.findViewById(R.id.total_Money);


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


        return rootView;
    }

    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
