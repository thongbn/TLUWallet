package com.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends Activity{

    private String email,password;
    private EditText editTextEmail,editTextPassword;
    private DataBaseHelper dataBaseHelper;
    private CheckBox saveLoginCheckBox, showPass;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private LoginButton fbbutton;
    private CallbackManager callbackManager;
    private static String idFB, emailFB, nameFB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SDK before setContentView(Layout ID)
        FacebookSdk.sdkInitialize(getApplicationContext());

        //setting defaut screen to login.xml
        setContentView(R.layout.login);

        dataBaseHelper = new DataBaseHelper(LoginActivity.this);
        dataBaseHelper.open();

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        showPass = (CheckBox) findViewById(R.id.show_pass);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                }else {
                    editTextPassword.setTransformationMethod(null);
                }
            }
        });

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextEmail.setText(loginPreferences.getString("email", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            if(editTextEmail.equals("") || editTextPassword.equals("")){
                editTextEmail.setError(getText(R.string.common_error_field_not_set));
                editTextPassword.setError(getText(R.string.common_error_field_not_set));
            }else {
                saveLoginCheckBox.setChecked(true);
                dataBaseHelper.login(loginPreferences.getString("email", ""), loginPreferences.getString("password", ""));
                if (User.getIdMoneyType() == null){
                    Intent i = new Intent(getApplicationContext(),ChooseMoneyTypeActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }

            }

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
                        if(dataBaseHelper.login(email, password) != null)
                        {
                            loginPrefsEditor.putString("email", email);
                            loginPrefsEditor.putString("password", password);
                            loginPrefsEditor.apply();
                            Toast.makeText(LoginActivity.this, getText(R.string.common_login_success), Toast.LENGTH_LONG).show();

                            if (User.getIdMoneyType() == null){
                                Intent i = new Intent(getApplicationContext(),ChooseMoneyTypeActivity.class);
                                startActivity(i);
                            }else {
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, getText(R.string.common_error_user_pass), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, getText(R.string.common_error_user_pass), Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.apply();
                }
            }

        });

        if (AccessToken.getCurrentAccessToken() != null){
            SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
            String facebookName = idFacebook.getString("nameFB", "");
            String facebookEmail = idFacebook.getString("emailFB", "");
            dataBaseHelper.loginFB(facebookEmail, facebookName);

            if (UserFB.getIdMoneyTypebyFB() == null){
                Intent i = new Intent(getApplicationContext(),ChooseMoneyTypeActivity.class);
                startActivity(i);
            }else {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }


        }

        // Initialize layout button
        fbbutton = (LoginButton) findViewById(R.id.facebook_login_button);
        fbbutton.setReadPermissions(Arrays.asList("email", "user_photos", "public_profile", "user_friends"));
        callbackManager = CallbackManager.Factory.create();

        fbbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                Log.v("LoginActivity", response.toString());
                                try {

                                    idFB = object.getString("id");
                                    emailFB = object.getString("email");
                                    nameFB = object.getString("name");

                                    SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
                                    SharedPreferences.Editor facebookPrefsEditor = idFacebook.edit();
                                    facebookPrefsEditor.putString("idFB", idFB);
                                    facebookPrefsEditor.putString("emailFB", emailFB);
                                    facebookPrefsEditor.putString("nameFB", nameFB);
                                    facebookPrefsEditor.apply();

                                    if (dataBaseHelper.checkFBiD(idFB)) {
                                        dataBaseHelper.insertFacebookEntry(idFB, emailFB, nameFB);
                                    }

                                    dataBaseHelper.loginFB(emailFB, nameFB);

                                    if (UserFB.getIdMoneyTypebyFB() == null){
                                        Intent i = new Intent(getApplicationContext(),ChooseMoneyTypeActivity.class);
                                        startActivity(i);
                                    }else {
                                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(i);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,name");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        dataBaseHelper.close();
    }
}
