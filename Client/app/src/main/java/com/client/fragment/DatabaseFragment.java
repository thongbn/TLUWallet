package com.client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.R;

public class DatabaseFragment extends Fragment {
	
	public DatabaseFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.database_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_item_database);
         
        return rootView;
    }
}