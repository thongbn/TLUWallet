package com.client.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.client.CustomAdapter.CustomIncomeGroup;
import com.client.R;

/**
 * Created by ToanNguyen on 07/03/2016.
 */
public class IncomeGroupFragment extends Fragment{


    private ListView listView;
    private String [] incomeText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.income_fragment, container, false);

        Context context = rootView.getContext();
        incomeText = getResources().getStringArray(R.array.income_categories);
        Resources res = context.getResources();
        TypedArray icons = res.obtainTypedArray(R.array.income_img);

        listView = (ListView) rootView.findViewById(R.id.list_income_categories);
        listView.setAdapter(new CustomIncomeGroup(rootView.getContext(), incomeText, icons));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        return rootView;
    }

}
