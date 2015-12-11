package com.client.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.User;
import com.client.database.UserFB;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends Activity{

    private String email,password;
    private EditText editTextEmail,editTextPassword;
    private DataBaseHelper dataBaseHelper;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private LoginButton fbbutton;
    private CallbackManager callbackManager;
//    private static String idFB, emailFB, nameFB;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SDK before setContentView(Layout ID)
        FacebookSdk.sdkInitialize(getApplicationContext());

        //setting defaut screen to login.xml
        setContentView(R.layout.login);

        if (AccessToken.getCurrentAccessToken() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        // Initialize layout button
        fbbutton = (LoginButton) findViewById(R.id.facebook_login_button);
        fbbutton.setReadPermissions(Arrays.asList("email", "user_photos", "public_profile", "user_friends"));

        fbbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                callbackManager = CallbackManager.Factory.create();

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {

                                GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject json, GraphResponse response) {
//                                                getuserdata();

                                            }
                                        }).executeAsync();

                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });
            }
        });

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextEmail.setText(loginPreferences.getString("email", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            startActivity(new Intent(getApplication(), MainActivity.class));
        }

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextEmail.getWindowToken(), 0);

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                try{
                    if(email.length()>0 && password.length() >0)
                    {
                        dataBaseHelper = new DataBaseHelper(LoginActivity.this);
                        dataBaseHelper.open();
                        if(dataBaseHelper.login(email, password) != null)
                        {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Sai tên đăng nhập/mật khẩu", Toast.LENGTH_LONG).show();
                        }
                        dataBaseHelper.close();
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
                    loginPrefsEditor.putString("email", email);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

//    public void getuserdata() {
//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        // Application code
//                        Log.v("LoginActivity", response.toString());
//
//                        try {
//                            //and fill them here like so.
//                            idFB = object.getString("id");
//                            emailFB = object.getString("email");
//                            nameFB = object.getString("name");
//                            UserFB.setFacebookID(idFB);
//                            UserFB.setEmailFB(emailFB);
//                            UserFB.setNameFB(nameFB);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email,gender");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }


}
