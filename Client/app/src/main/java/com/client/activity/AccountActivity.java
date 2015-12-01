package com.client.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.client.R;


/**
 * Represents other activity different from the main activity
 */
public class AccountActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

    }


}
