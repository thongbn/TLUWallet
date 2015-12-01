package com.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.client.R;

/**
 * Created by nguye on 11/25/2015.
 */
public class Splash extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {

         /*
          * Showing splash screen with a timer. This will be useful when you
          * want to show case your app logo / company
          */

            @Override
            public void run() {

                startActivity(new Intent(getApplication(), LoginActivity.class));

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

