package com.client.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.R;
import com.client.model.MyDeal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportFragment extends Fragment {

    private Date[] date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_item_report);

//        SimpleDateFormat fommatter = new SimpleDateFormat("dd-MM-yyyy");
//
//        try {
//
//            date = new Date[MyDeal.listDealDate.size()];
//
//            for (int i = 0; i< MyDeal.listDealDate.size(); i++){
//                date [i] = fommatter.parse(MyDeal.listDealDate.get(i));
//            }
//
//            System.out.print(date);
//
//        }catch (ParseException e){
//            e.printStackTrace();
//        }

        return rootView;
    }

}
