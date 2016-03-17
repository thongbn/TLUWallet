package com.client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.R;

public class SettingsFragment extends Fragment{
	
	public SettingsFragment (){}
	
	@Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_item_settings);

        return rootView;
    }
}