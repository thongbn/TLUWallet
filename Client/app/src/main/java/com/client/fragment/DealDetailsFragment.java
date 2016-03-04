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

import com.client.CustomDealList.CustomDealList;
import com.client.R;
import com.client.activity.DealActivity;
import com.client.activity.EditDealActivity;
import com.client.database.DataBaseHelper;
import com.client.database.model.MyDeal;
import com.client.database.model.MyWallet;

public class DealDetailsFragment extends Fragment {

    private ListView listDeal;
    private FloatingActionButton FAB;
    private DataBaseHelper dataBaseHelper;

    public DealDetailsFragment (){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.deal_details_fragment, container, false);

        dataBaseHelper = new DataBaseHelper(rootView.getContext());
        dataBaseHelper.getDeal(MyWallet.getIdWallet());

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
        listDeal.setAdapter(new CustomDealList(rootView.getContext()));

        listDeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(rootView.getContext(), EditDealActivity.class);
                intent.putExtra("DId", MyDeal.listDealiD.get(position));
                intent.putExtra("DMoney", MyDeal.listDealMoney.get(position));
                intent.putExtra("DDetail", MyDeal.listDealDetails.get(position));
                intent.putExtra("DTypeMoney", MyDeal.listDealTypeMoney.get(position));
                intent.putExtra("DGroup", MyDeal.listDealGroup.get(position));
                intent.putExtra("DDate", MyDeal.listDealDate.get(position));
                intent.putExtra("update", true);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
