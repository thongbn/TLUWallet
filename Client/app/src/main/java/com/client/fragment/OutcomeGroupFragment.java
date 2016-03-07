package com.client.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.client.CustomAdapter.CustomIncomeGroup;
import com.client.R;

/**
 * Created by ToanNguyen on 07/03/2016.
 */
public class OutcomeGroupFragment extends Fragment{

    private ListView listView;
    private String [] outcomeText;


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.outcome_fragment, container, false);

            Context context = rootView.getContext();
            outcomeText = getResources().getStringArray(R.array.outcome_categories);
            Resources res = context.getResources();
            TypedArray icons = res.obtainTypedArray(R.array.outcome_img);
            listView = (ListView) rootView.findViewById(R.id.list_outcome_categories);
            listView.setAdapter(new CustomIncomeGroup(rootView.getContext(), outcomeText, icons));


            return rootView;
        }
}
