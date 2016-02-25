package com.client.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.Deal;
import com.client.database.model.MyWallet;
import com.client.database.model.Wallet;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DealActivity extends Activity {
    public SQLiteDatabase db;
    private EditText  deal_Money, deal_Detail, deal_Date, deal_Wallet;
    private String dealGroup, dealMoney, dealDetail, dealDate;
    private Wallet dealWallet;
    private Spinner spinnerW, spinnerGroup;
    private TextView addButton, clearButton;
    private  static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    DataBaseHelper dataBaseHelper;
    String[] spinnerValues = {"Thu vào", "Chi ra"};
    private boolean isUpdate;

    int day,month,year;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deal);
        dataBaseHelper = new DataBaseHelper(DealActivity.this);
        dataBaseHelper.open();

//        deal_Group = (EditText) findViewById(R.id.edit_Group);
        deal_Money = (EditText) findViewById(R.id.edit_Money);
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
                dealWallet = (Wallet) parent.getItemAtPosition(position);
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
//            idWallet = getIntent().getExtras().getString("ID");
            dealMoney = getIntent().getExtras().getString("DMoney");
            dealDetail = getIntent().getExtras().getString("DDetail");
            dealDate = getIntent().getExtras().getString("DDate");

            deal_Money.setText(dealMoney);
            deal_Detail.setText(dealDetail);
            deal_Date.setText(dealDate);
        }

        addButton = (TextView) findViewById(R.id.saveDeal_action_text);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                dealGroup = deal_Group.getText().toString();
                dealMoney = deal_Money.getText().toString();
                dealDetail = deal_Detail.getText().toString();
                if (calendar != null)
                    Deal.setDealDate(calendar.getTime());
                Deal.setDealGroup(dealGroup);
                Deal.setDealMoney(dealMoney);
                Deal.setDealDetail(dealDetail);
                Deal.setWallet(dealWallet);
                //check if any of fields are vaccant
                if (dealGroup.equals("") || dealMoney.equals("") || dealDate.equals("")) {
                    Toast.makeText(getApplicationContext(), "Chưa điền thông tin", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (isUpdate) {
                        //update data with new data
                        dataBaseHelper.updateDeal();
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
        EditText eDate=(EditText) findViewById(R.id.edit_Date);
        Calendar cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);
        eDate.setText(day+"-"+(month+1)+"-"+year);

//        clearButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                resetAllFields();
//            }
//        });
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
            return mySpinner;
        }
    }


    protected void resetAllFields(){
//        deal_Group.setText("");
        deal_Money.setText("");
        deal_Detail.setText("");
        deal_Date.setText("");
        if (spinnerW.getAdapter().getCount() > 0)
            spinnerW.setSelection(0);
    }

}
