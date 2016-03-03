package com.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.User;
import com.client.database.model.Wallet;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

/**
 * Created by ToanNguyen on 29/02/2016.
 */
public class EditWalletActivity extends Activity{
    private String walletName, walletType, idWallet, idUser;
    private Spinner spinner;
    String[] spinnerValues = {"VNĐ", "USD", "EUR", "GBP"};
    int money_images[] = {R.drawable.ic_currency_vnd, R.drawable.ic_currency_usd, R.drawable.ic_currency_eur, R.drawable.ic_currency_gbp};
    private TextView saveButton, cancelButton;
    private EditText wallet_Name;
    private DataBaseHelper dataBaseHelper;
    private boolean isUpdate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wallet);

        FacebookSdk.sdkInitialize(getApplicationContext());

        //get Instance of Database Adapter
        dataBaseHelper = new DataBaseHelper(EditWalletActivity.this);
        dataBaseHelper.open();

        //Spinner

        spinner = (Spinner) findViewById(R.id.spinner2);

        spinner.setAdapter(new MyAdapter(this, R.layout.custom_spinner_money_type, spinnerValues));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                walletType = parent.getItemAtPosition(position).toString();

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

        //get Refferences of Views
        wallet_Name = (EditText) findViewById(R.id.eEditTenVi);

        isUpdate = getIntent().getExtras().getBoolean("update");
        if(isUpdate){
            idWallet = getIntent().getExtras().getString("ID");
            walletName = getIntent().getExtras().getString("WName");
            walletType = getIntent().getExtras().getString("WType");

            switch (walletType){
                case "VNĐ":
                    spinner.setSelection(0);
                    break;
                case "USD":
                    spinner.setSelection(1);
                    break;
                case "EUR":
                    spinner.setSelection(2);
                    break;
                case "GBP":
                    spinner.setSelection(3);
                    break;
            }

            wallet_Name.setText(walletName);


        }

        saveButton = (TextView) findViewById(R.id.save_action_text);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletName = wallet_Name.getText().toString();
                Wallet.setWalletName(walletName);
                Wallet.setWalletType(walletType);
                idUser = User.getIdNguoiDung();
                SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
                String facebookId = idFacebook.getString("idFB", "");
                Wallet.getUserFB().setFacebookID(facebookId);
                Wallet.getUser().setIdNguoiDung(idUser);

                if (walletName.equals("")){
                    wallet_Name.setError("Chưa có thông tin");
                }else {
                    if (AccessToken.getCurrentAccessToken() != null){
                        dataBaseHelper.updateWalletbyFB(idWallet);
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }else {
                        dataBaseHelper.updateWallet(idWallet);
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }

                }

            }
        });

        cancelButton = (TextView) findViewById(R.id.cancel_action_text);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    // Custom View Spinner

    public class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context context, int txtViewResourceID, String[] objects) {
            super(context, txtViewResourceID, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner_money_type, parent, false);
            TextView text_typeMoney = (TextView) mySpinner.findViewById(R.id.text_typeMoney);
            text_typeMoney.setText(spinnerValues[position]);
            ImageView image_typeMomey = (ImageView) mySpinner.findViewById(R.id.imageMoney);
            image_typeMomey.setImageResource(money_images[position]);
            return mySpinner;
        }
    }
}
