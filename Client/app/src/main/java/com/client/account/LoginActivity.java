package com.client.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.client.MainActivity;
import com.client.R;
import com.client.database.LoginDataBaseAdapter;

public class LoginActivity extends Activity{
	
	private String username,password;
    private EditText editTextUsername,editTextPassword;
    LoginDataBaseAdapter loginDataBaseAdapter;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//setting defaut screen to login.xml
		setContentView(R.layout.login);
		
		editTextUsername = (EditText)findViewById(R.id.editTextUsername);
		editTextPassword = (EditText)findViewById(R.id.editTextPassword);
		saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextUsername.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }
		
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);
				
				username = editTextUsername.getText().toString();
				password = editTextPassword.getText().toString();
				 
				try{
					if(username.length()>0 && password.length() >0)
					{
						loginDataBaseAdapter = new LoginDataBaseAdapter(LoginActivity.this);
						loginDataBaseAdapter.open();
						if(loginDataBaseAdapter.Login(username, password))
						{
							Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
							Intent i = new Intent(getApplicationContext(),MainActivity.class);
							startActivity(i);	
						}
						else
						{
							Toast.makeText(LoginActivity.this, "Sai tên đăng nhập/mật khẩu", Toast.LENGTH_LONG).show();
						}
						loginDataBaseAdapter.close();
					}else{
						Toast.makeText(LoginActivity.this, "Sai tên đăng nhập/mật khẩu", Toast.LENGTH_LONG).show();
					}
				}
				catch(Exception e)
				{
					Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
				
				if (saveLoginCheckBox.isChecked()) {
	                loginPrefsEditor.putBoolean("saveLogin", true);
	                loginPrefsEditor.putString("username", username);
	                loginPrefsEditor.putString("password", password);
	                loginPrefsEditor.commit();
	            } else {
	                loginPrefsEditor.clear();
	                loginPrefsEditor.commit();
	            }
			}
			
		});
		
		
		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
		
		//Listening to register new account link
		
		registerScreen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
				startActivity(i);				
			}
		});
		
		TextView retrivePass = (TextView) findViewById(R.id.forgotPass);
		
		//Listening to register new account link
		
		retrivePass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(),RetrivePass.class);
				startActivity(i);				
			}
		});
	}

}
