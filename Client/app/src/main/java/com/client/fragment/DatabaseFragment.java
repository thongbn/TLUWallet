package com.client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.client.R;
import com.client.database.DataBaseHelper;

public class DatabaseFragment extends Fragment {
	
	public DatabaseFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.database_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_item_database);

        Button exportCSV = (Button) rootView.findViewById(R.id.exportCSV);

        exportCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.exportCSV();
                Toast.makeText(getActivity(), "Export Success", Toast.LENGTH_LONG).show();
            }
        });
         
        return rootView;
    }
}