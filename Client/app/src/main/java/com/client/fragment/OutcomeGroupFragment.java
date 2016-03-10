package com.client.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.CustomAdapter.CustomIncomeGroup;
import com.client.R;
import com.client.activity.DealActivity;
import com.client.database.model.MyDeal;

/**
 * Created by ToanNguyen on 07/03/2016.
 */
public class OutcomeGroupFragment extends Fragment{

    private ListView listView;
    private String [] outcomeText;
    private int outcome [] = {R.drawable.ic_category_travel, R.drawable.ic_category_foodndrink, R.drawable.ic_category_entertainment, R.drawable.ic_category_friendnlover,
            R.drawable.ic_category_family, R.drawable.ic_category_transport, R.drawable.ic_category_medical, R.drawable.ic_category_education, R.drawable.ic_category_shopping,
            R.drawable.ic_category_invest, R.drawable.ic_category_other_expense};


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.outcome_fragment, container, false);

            Context context = rootView.getContext();
            outcomeText = getResources().getStringArray(R.array.outcome_categories);
//            Resources res = context.getResources();
//            final TypedArray icons = res.obtainTypedArray(R.array.outcome_img);
            listView = (ListView) rootView.findViewById(R.id.list_outcome_categories);
            listView.setAdapter(new CustomIncomeGroup(rootView.getContext(), outcomeText, outcome));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyDeal mydeal = new MyDeal();
                mydeal.setDealGroup(2);
                mydeal.setDealGroupDetailName(outcomeText[position]);
                mydeal.setDealGroupDetailPos(position);
                mydeal.setDealGroupImg(outcome[position]);
                startActivity(new Intent(rootView.getContext(), DealActivity.class));
            }
        });

            return rootView;
        }
}
