package com.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.CustomAdapter.CustomIncomeGroup;
import com.client.R;
import com.client.model.MyPlan;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class IncomeGroupPlanFragment extends Fragment{
    private ListView listView;
    private String [] incomeText;
    private int income [] = {R.drawable.ic_category_salary, R.drawable.ic_category_award, R.drawable.ic_category_selling, R.drawable.ic_category_give, R.drawable.ic_category_interestmoney, R.drawable.ic_category_other_income};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.income_fragment, container, false);

        incomeText = getResources().getStringArray(R.array.income_categories);

        listView = (ListView) rootView.findViewById(R.id.list_income_categories);
        listView.setAdapter(new CustomIncomeGroup(rootView.getContext(), incomeText, income));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPlan myPlan = new MyPlan();
                myPlan.setPlanGroup(1);
                myPlan.setPlanGroupDetailPos(position);
                myPlan.setPlanGroupDetailName(incomeText[position]);
                myPlan.setPlanGroupImg(income[position]);
                Intent intent = new Intent();
                intent.putExtra("GroupName", incomeText[position]);
                intent.putExtra("GroupImg", income[position]);
                getActivity().setResult(getActivity().RESULT_OK, intent);
                getActivity().finish();
            }
        });


        return rootView;
    }
}
