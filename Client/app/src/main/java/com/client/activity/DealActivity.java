package com.client.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.Deal;
import com.client.database.model.MyDeal;
import com.client.database.model.MyWallet;
import com.client.database.model.Wallet;

import java.sql.Date;
import java.util.Calendar;

public class DealActivity extends Activity {
    private EditText  deal_Money, deal_Detail, deal_Date, eDate;
    private String dealGroup, dealMoney, dealDetail, dealDate, dealTypemoney, dealWallet;
    private Spinner spinnerW, spinnerGroup;
    private TextView addButton, clearButton, deal_TypeMoney;
    Calendar calendar;
    DataBaseHelper dataBaseHelper;
    String[] spinnerValues = {"Thu vào", "Chi ra"};
    int group_images[] = {R.drawable.ic_cash_in, R.drawable.ic_cash_out};
    private boolean isUpdate;

    int day,month,year;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deal);
        dataBaseHelper = new DataBaseHelper(DealActivity.this);
        dataBaseHelper.open();

        deal_Money = (EditText) findViewById(R.id.edit_Money);
        deal_TypeMoney = (TextView) findViewById(R.id.deal_type_money);
        deal_Detail = (EditText) findViewById(R.id.edit_Detail);
        deal_Date = (EditText) findViewById(R.id.edit_Date);
        deal_Date.setInputType(InputType.TYPE_NULL);

        //spinner group
        spinnerGroup = (Spinner) findViewById(R.id.spinnerGroup);
        spinnerGroup.setAdapter(new MyAdapter(this, R.layout.custom_spinner_group, spinnerValues));
        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dealGroup = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //spinner wallet
        spinnerW = (Spinner) findViewById(R.id.spinner_wallet);
        spinnerW.setAdapter(new ArrayAdapter<>(this,R.layout.custom_spinner_wallet, MyWallet.listWalletName));
        spinnerW.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dealWallet = MyWallet.listWalletID.get(position);
                deal_TypeMoney.setText(MyWallet.listWalletMoneyType.get(position));

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

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
        deal_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(113);
            }
        });

        isUpdate = getIntent().getExtras().getBoolean("update");
        if(isUpdate){
            dealMoney = getIntent().getExtras().getString("DMoney");
            dealDetail = getIntent().getExtras().getString("DDetail");
            dealTypemoney = getIntent().getExtras().getString("DTypeMoney");
            dealDate = getIntent().getExtras().getString("DDate");

            deal_Money.setText(dealMoney);
            deal_TypeMoney.setText(dealTypemoney);
            deal_Detail.setText(dealDetail);
            deal_Date.setText(dealDate);
        }

        addButton = (TextView) findViewById(R.id.saveDeal_action_text);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                dealGroup = deal_Group.getText().toString();
                dealMoney = deal_Money.getText().toString();
                dealTypemoney = deal_TypeMoney.getText().toString();
                dealDetail = deal_Detail.getText().toString();
                if (eDate != null)
                    Deal.setDealDate(eDate.getText().toString());

                if(dealGroup == "Thu vào") {
                    Deal.setDealGroup("1");
                }else {
                    Deal.setDealGroup("2");
                }

                Deal.setDealMoney(dealMoney);
                Deal.setDealTypeMoney(dealTypemoney);
                Deal.setDealDetail(dealDetail);
                Deal.setWallet(dealWallet);
                //check if any of fields are vaccant
                if (dealMoney.equals("") || dealDetail.equals("")) {
                    Toast.makeText(getApplicationContext(), "Chưa điền thông tin", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (isUpdate) {
                        //update data with new data
                        dataBaseHelper.updateDeal();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        dataBaseHelper.insertDeal();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
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
        eDate=(EditText) findViewById(R.id.edit_Date);
        Calendar cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);
        eDate.setText(day+"-"+(month+1)+"-"+year);


    }
    public class MyAdapter extends ArrayAdapter <String> {
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
            View mySpinner = inflater.inflate(R.layout.custom_spinner_group, parent, false);
            TextView text_typeMoney = (TextView) mySpinner.findViewById(R.id.text_Group);
            text_typeMoney.setText(spinnerValues[position]);
            ImageView image_group = (ImageView) mySpinner.findViewById(R.id.imageGroup);
            image_group.setImageResource(group_images[position]);
            return mySpinner;
        }
    }

}
