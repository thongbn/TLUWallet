package com.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.User;
import com.client.database.model.UserFB;
import com.client.database.model.Wallet;
import com.facebook.AccessToken;

import java.util.regex.Pattern;

/**
 * Created by quang on 22/12/2015.
 */
public class WalletActivity2 extends Activity{
    private Button btSave;
    private EditText wallet_Name, wallet_Money, wallet_Type;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private String idWallet, walletName, walletMoney, walletType, idUser, idFB;
    private boolean isUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        //get Instance of Database Adapter
        dataBaseHelper = new DataBaseHelper(WalletActivity2.this);
        dataBaseHelper.open();

        //get Refferences of Views
        wallet_Name = (EditText) findViewById(R.id.wEditTenVi);
        wallet_Money = (EditText) findViewById(R.id.wEditTien);
        wallet_Type = (EditText) findViewById(R.id.wEditType);
        isUpdate = getIntent().getExtras().getBoolean("update");
        if(isUpdate){
            idWallet = getIntent().getExtras().getString("ID");
            walletName = getIntent().getExtras().getString("WName");
            walletMoney = getIntent().getExtras().getString("WMoney");
            walletType = getIntent().getExtras().getString("WType");

            wallet_Name.setText(walletName);
            wallet_Money.setText(walletMoney);
            wallet_Type.setText(walletType);
        }

        btSave = (Button) findViewById(R.id.wbtLuu);
        btSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                walletName = wallet_Name.getText().toString();
                walletMoney = wallet_Money.getText().toString();
                walletType = wallet_Type.getText().toString();
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
                    if(isUpdate){
                   //update data with new data
                    dataBaseHelper.updateWallet();
                    }
                    else {
                        if (AccessToken.getCurrentAccessToken() != null){
                            dataBaseHelper.insertWalletByFB();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }else {
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

}
