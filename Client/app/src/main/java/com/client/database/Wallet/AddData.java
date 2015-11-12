package com.client.database.Wallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.client.MainActivity;
import com.client.R;
import com.client.activity.wallet.WalletFragment;
import com.client.database.DataBaseHelper;

/**
 * Created by nguye on 11/9/2015.
 */
public class AddData extends Activity implements View.OnClickListener {
    private Button btSave;
    private EditText wallet_Name, wallet_Money, wallet_Type;
    private DataBaseHelper mydb;
    private SQLiteDatabase database;
    private String idWallet, walletName, walletMoney, walletType;
    private boolean isUpdate;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        btSave = (Button) findViewById(R.id.wbtLuu);

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
        btSave.setOnClickListener(this);
        mydb = new DataBaseHelper(this);
    }
    //saveButton click
    public void onClick(View view){
        walletName = wallet_Name.getText().toString().trim();
        walletMoney = wallet_Money.getText().toString().trim();
        walletType = wallet_Type.getText().toString().trim();

        if(walletName.length() > 0 && walletMoney.length() > 0 && walletType.length() > 0){
            saveData();
            startActivity(new Intent(getApplication(), MainActivity.class));
        }
        else {
            AlertDialog.Builder alerBuilder = new AlertDialog.Builder(AddData.this);
            alerBuilder.setTitle("Invalid Data");
            alerBuilder.setMessage("Please Enter valid data");
            alerBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alerBuilder.create().show();
        }
    }
    //save data into SQLite
    private void saveData(){
        database = mydb.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataBaseHelper.WALLET_NAME, walletName);
        contentValues.put(DataBaseHelper.WALLET_MONEY, walletMoney);
        contentValues.put(DataBaseHelper.WALLET_TYPE_MONEY, walletType);

        System.out.println("");
        if(isUpdate){
            //update data with new data
            database.update(DataBaseHelper.WALLET_TABLE, contentValues, DataBaseHelper.WALLET_ID +"="+idWallet, null);
        }
        else {
            //insert data into database
            database.insert(DataBaseHelper.WALLET_TABLE, null, contentValues);
        }
        //close database
        database.close();
        finish();
    }

}
