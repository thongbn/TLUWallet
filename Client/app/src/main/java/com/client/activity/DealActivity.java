package com.client.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.Deal;
import com.client.database.model.MyDeal;
import com.client.database.model.User;
import com.client.database.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.util.Calendar;

public class DealActivity extends Activity {
    private EditText  deal_Money, deal_Detail, eDate;
    private ImageView imgGroup;
    private String dealMoney, dealDetail, idUser;
    private TextView addButton, clearButton, deal_TypeMoney, textGroup;
    DataBaseHelper dataBaseHelper;
    private TableRow pickGroup;

    int day,month,year;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deal);
        FacebookSdk.sdkInitialize(getApplicationContext());
        dataBaseHelper = new DataBaseHelper(DealActivity.this);
        dataBaseHelper.open();

        deal_Money = (EditText) findViewById(R.id.edit_Money);
        deal_TypeMoney = (TextView) findViewById(R.id.deal_type_money);
        deal_Detail = (EditText) findViewById(R.id.edit_Detail);
        eDate=(EditText) findViewById(R.id.edit_Date);

        if(AccessToken.getCurrentAccessToken() != null){
            deal_TypeMoney.setText(UserFB.getIdMoneyTypebyFB());
        }else {
            deal_TypeMoney.setText(User.getIdMoneyType());
        }

        //pick group

        pickGroup = (TableRow) findViewById(R.id.pickGroup);
        pickGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PickGroupActivity.class));
            }
        });

        textGroup = (TextView) findViewById(R.id.textGroup);
        imgGroup = (ImageView) findViewById(R.id.imageGroup);

        textGroup.setText(MyDeal.getDealGroupDetailName());

        if (MyDeal.getDealGroupImg() != 0){
            imgGroup.setImageResource(MyDeal.getDealGroupImg());
        } else {
            imgGroup.setImageResource(R.drawable.ic_category_all);
        }


        //money
        deal_Money.addTextChangedListener(new TextWatcher() {
            boolean isManualChange = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (isManualChange) {
                    isManualChange = false;
                    return;
                }
                try {
                    String value = s.toString().replace(",", "");
                    String reverseValue = new StringBuilder(value).reverse()
                            .toString();
                    StringBuilder finalValue = new StringBuilder();
                    for (int i = 1; i <= reverseValue.length(); i++) {
                        char val = reverseValue.charAt(i - 1);
                        finalValue.append(val);
                        if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                            finalValue.append(",");
                        }
                    }
                    isManualChange = true;
                    deal_Money.setText(finalValue.reverse());
                    deal_Money.setSelection(finalValue.length());
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

        setListener();
        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(113);
            }
        });

        addButton = (TextView) findViewById(R.id.saveDeal_action_text);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dealMoney = deal_Money.getText().toString();
                dealDetail = deal_Detail.getText().toString();
                if (eDate != null)
                    Deal.setDealDate(eDate.getText().toString());

                Deal.setDealMoney(dealMoney);
                Deal.setDealDetail(dealDetail);
                Deal.setDealGroup(MyDeal.getDealGroup());
                Deal.setDealGroupDetailsPos(MyDeal.getDealGroupDetailPos());
                Deal.setDealGroupIcon(MyDeal.getDealGroupImg());
                idUser = User.getIdNguoiDung();
                SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
                String facebookId = idFacebook.getString("idFB", "");
                Deal.getUserFB().setFacebookID(facebookId);
                Deal.getUser().setIdNguoiDung(idUser);
                //check if any of fields are vaccant
                if (dealMoney.equals("") || dealDetail.equals("") || textGroup.equals("")) {
                    deal_Money.setError("Chưa điền thông tin");
                    deal_Detail.setError("Chưa điền thông tin");
                    return;
                } else {
                    if(AccessToken.getCurrentAccessToken() != null){
                        dataBaseHelper.insertDealbyFB();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else {
                        dataBaseHelper.insertDeal();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }



                }
            }
        });

        clearButton = (TextView) findViewById(R.id.cancelDeal_action_text);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if(id==113)
        {
            return new DatePickerDialog(this, dateChange, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dateChange= new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year1, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            year=year1;
            month=monthOfYear;
            day=dayOfMonth;
            EditText eDate=(EditText) findViewById(R.id.edit_Date);
            eDate.setText(day+"-"+(month+1)+"-"+year);
        }
    };
    private void setListener(){
        eDate.setInputType(InputType.TYPE_NULL);
        Calendar cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);
        eDate.setText(day+"-"+(month+1)+"-"+year);


    }

}
