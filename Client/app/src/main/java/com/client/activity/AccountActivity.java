package com.client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;


/**
 * Represents other activity different from the main activity
 */
public class AccountActivity extends AppCompatActivity {

    private TextView account_email;
    private SharedPreferences loginPreferences;
    private ImageView imageAccount;
    private Button btnLogout;
    private ImageButton turnBack;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        FacebookSdk.sdkInitialize(getApplicationContext());

        account_email = (TextView) findViewById(R.id.email_account);
        imageAccount = (ImageView) findViewById(R.id.image_account);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        String emailLogin = loginPreferences.getString("email", "");
        if (AccessToken.getCurrentAccessToken() != null) {
            SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
            String facebookName = idFacebook.getString("emailFB", "");
            imageAccount.setImageResource(R.drawable.ic_facebook);
            account_email.setText(facebookName);
        }else {
            account_email.setText(emailLogin);
            imageAccount.setImageResource(R.drawable.ic_avatar_default);
        }


        btnLogout = (Button)findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences loginPrefence = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor mSaveState = loginPrefence.edit();
                mSaveState.putBoolean("LoginSession", false);
                mSaveState.clear();
                mSaveState.apply();

                SharedPreferences facebookPrefence = getSharedPreferences("idFacebook", Context.MODE_PRIVATE);
                SharedPreferences.Editor mSaveState1 = facebookPrefence.edit();
                mSaveState1.putBoolean("LoginFacebookSession", false);
                mSaveState1.clear();
                mSaveState1.apply();

                LoginManager.getInstance().logOut();
                Intent signout = new Intent(getApplicationContext(), LoginActivity.class);
                signout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signout);
            }
        });

        turnBack = (ImageButton) findViewById(R.id.turn_back);
        turnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
