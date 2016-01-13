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
                SharedPreferences loginPrefence = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor mSaveState = loginPrefence.edit();
                mSaveState.putBoolean("LoginSession", false);
                mSaveState.clear();
                mSaveState.commit();

                SharedPreferences facebookPrefence = getActivity().getSharedPreferences("idFacebook", Context.MODE_PRIVATE);
                SharedPreferences.Editor mSaveState1 = facebookPrefence.edit();
                mSaveState1.putBoolean("LoginFacebookSession", false);
                mSaveState1.clear();
                mSaveState1.commit();

                FacebookSdk.sdkInitialize(getContext());
                LoginManager.getInstance().logOut();
                Intent signout = new Intent(v.getContext(), LoginActivity.class);
                signout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signout);
                break;
        }
    }
}