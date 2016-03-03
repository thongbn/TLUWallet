package com.client.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.client.R;
import com.client.activity.LoginActivity;
import com.client.database.model.MyWallet;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class SettingsFragment extends Fragment{
	
	public SettingsFragment (){}
	
	@Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        return rootView;
    }
}