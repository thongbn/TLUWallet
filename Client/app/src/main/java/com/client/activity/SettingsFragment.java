package com.client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.client.R;
import com.client.account.LoginActivity;

public class SettingsFragment extends Fragment implements View.OnClickListener{
	
	public SettingsFragment (){}
	
	@Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        Button btnLogout;
        btnLogout = (Button)rootView.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
         
        return rootView;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnLogout:
                SharedPreferences mSharedPrefence = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor mSaveState = mSharedPrefence.edit();
                mSaveState.putBoolean("LoginSession", false);
                mSaveState.clear();
                mSaveState.commit();
                Intent signout = new Intent(v.getContext(),LoginActivity.class);
                signout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signout);
                break;
        }
    }
}