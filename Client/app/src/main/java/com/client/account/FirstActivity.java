package com.client.account;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.client.R;


public class FirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_activity);
		
		TextView actionLogin = (TextView) findViewById(R.id.btnLoginMain);
		TextView actionRegister = (TextView) findViewById(R.id.btnRegisterMain);
		
		//Listening to register new account link
		
		actionLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(i);				
			}
		});
		
		actionRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
				startActivity(i);				
			}
		});
	}
}
