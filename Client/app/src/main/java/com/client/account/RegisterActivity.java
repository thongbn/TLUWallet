package com.client.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;
import com.client.database.LoginDataBaseAdapter;


public class RegisterActivity extends Activity{
	
	EditText editTextUserName, editTextPassword, editTextConfirmPassword;
	Button btnCreateAccount;
	LoginDataBaseAdapter loginDataBaseAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		//get Instance of Database Adapter
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		//get Refferences of Views
		editTextUserName = (EditText)findViewById(R.id.reg_username);
		editTextPassword = (EditText)findViewById(R.id.reg_password);
		editTextConfirmPassword = (EditText)findViewById(R.id.reg_confirm_password);
		
		btnCreateAccount = (Button)findViewById(R.id.btnRegister);
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String userName = editTextUserName.getText().toString();
				String password = editTextPassword.getText().toString();
				String confirmPassword = editTextConfirmPassword.getText().toString();
				
				//check if any of fields are vaccant
				if(userName.equals("") || password.equals("") || confirmPassword.equals("")){
					Toast.makeText(getApplicationContext(), "Chưa điền thông tin", Toast.LENGTH_LONG).show();
					return;
				}
				
				//check if both password matches
				if(!password.equals(confirmPassword)){
					Toast.makeText(getApplicationContext(), "Mật khẩu không khớp", Toast.LENGTH_LONG).show();
					return;
				}
				else{
					//Save the Data in Database
					loginDataBaseAdapter.insertEntry(userName, password);
					Toast.makeText(getApplicationContext(), "Tài khoản đã được tạo", Toast.LENGTH_LONG).show();
					Intent i = new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(i);
				}
			}
		});
		
		
		TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
		
		loginScreen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(i);		
			}
		});
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		loginDataBaseAdapter.close();
	}
}
