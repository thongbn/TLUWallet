package com.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.client.CustomAdapter.CustomChooseMoneyType;
import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class ChooseMoneyTypeActivity extends Activity{

    private String [] moneytype = {"đ", "$", "€", "£", "¥", "$", " ¥", "₩", "$", "฿"};
    private DataBaseHelper dataBaseHelper;

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_money);
        FacebookSdk.sdkInitialize(getApplicationContext());
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        ListView listMoneyType = (ListView) findViewById(R.id.listMoneyType);
        CustomChooseMoneyType adapter = new CustomChooseMoneyType(getApplicationContext());
        listMoneyType.setAdapter(adapter);

        listMoneyType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    UserFB.setIdMoneyTypebyFB(moneytype[position]);
                    dataBaseHelper.inserMoneyFB(UserFB.getFacebookID());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    User.setIdMoneyType(moneytype[position]);
                    dataBaseHelper.inserMoney(User.getIdNguoiDung());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });


    }
}
