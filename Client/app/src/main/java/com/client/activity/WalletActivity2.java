package com.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.User;
import com.client.database.model.Wallet;
import com.facebook.AccessToken;

public class WalletActivity2 extends Activity{
    private Button btSave;
    private EditText wallet_Name, wallet_Money;
    private DataBaseHelper dataBaseHelper;
    private String walletName, walletMoney, walletType, idUser, idWallet;
    private boolean isUpdate;
    private Spinner spinner;
    String[] spinnerValues = {"VNĐ", "USD", "EUR", "GBP"};
    int money_images[] = {R.drawable.ic_currency_vnd, R.drawable.ic_currency_usd, R.drawable.ic_currency_eur, R.drawable.ic_currency_gbp};

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        //get Instance of Database Adapter
        dataBaseHelper = new DataBaseHelper(WalletActivity2.this);
        dataBaseHelper.open();

        //Spinner

        spinner = (Spinner) findViewById(R.id.spinner);

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
        wallet_Name = (EditText) findViewById(R.id.wEditTenVi);
        wallet_Money = (EditText) findViewById(R.id.wEditTien);

        // Add . to number
        wallet_Money.addTextChangedListener(new TextWatcher() {

            boolean isManualChange = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (isManualChange) {
                    isManualChange = false;
                    return;
                }

                try {
                    String value = s.toString().replace(".", "");
                    String reverseValue = new StringBuilder(value).reverse()
                            .toString();
                    StringBuilder finalValue = new StringBuilder();
                    for (int i = 1; i <= reverseValue.length(); i++) {
                        char val = reverseValue.charAt(i - 1);
                        finalValue.append(val);
                        if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                            finalValue.append(".");
                        }
                    }
                    isManualChange = true;
                    wallet_Money.setText(finalValue.reverse());
                    wallet_Money.setSelection(finalValue.length());
                } catch (Exception e) {
                    // Do nothing since not a number
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        // get String

        isUpdate = getIntent().getExtras().getBoolean("update");
        if(isUpdate){
            idWallet = getIntent().getExtras().getString("ID");
            walletName = getIntent().getExtras().getString("WName");
            walletMoney = getIntent().getExtras().getString("WMoney");

            wallet_Name.setText(walletName);
            wallet_Money.setText(walletMoney);

        }

        btSave = (Button) findViewById(R.id.wbtLuu);
        btSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                walletName = wallet_Name.getText().toString();
                walletMoney = wallet_Money.getText().toString();
                Wallet.setWalletName(walletName);
                Wallet.setWalletMoney(walletMoney);
                Wallet.setWalletType(walletType);
                idUser = User.getIdNguoiDung();
                SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
                String facebookId = idFacebook.getString("idFB", "");
                Wallet.getUserFB().setFacebookID(facebookId);
                Wallet.getUser().setIdNguoiDung(idUser);
                //check if any of fields are vaccant
                if (walletName.equals("") || walletMoney.equals("") || walletType.equals("")) {
                    Toast.makeText(getApplicationContext(), "Chưa điền thông tin", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (isUpdate) {
                        //update data with new data
                        dataBaseHelper.updateWallet();
                    } else {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            dataBaseHelper.insertWalletByFB();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            //Save the Data in Database
                            dataBaseHelper.insertWallet();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        }
                    }
                }
            }

        });
    }

    // Custom View Spinner

    public class MyAdapter extends ArrayAdapter <String> {
        public MyAdapter (Context context, int txtViewResourceID, String[] objects) {
            super(context, txtViewResourceID, objects);
        }

        @Override
        public View getDropDownView (int position, View convertView, ViewGroup parent)  {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView (int position, View convertView, ViewGroup parent) {
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
