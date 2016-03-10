package com.client.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import com.client.fragment.DealDetailsFragment;

import java.util.Calendar;

/**
 * Created by ToanNguyen on 04/03/2016.
 */
public class EditDealActivity extends Activity{

    private EditText deal_Money, deal_Detail, deal_Date, eDate;
    private String dealGroup, dealMoney, dealDetail, dealDate, dealTypemoney, idUser, dealID;
    private Integer dealGroupDetailsPos, dealIcon;
    private TextView saveButton, clearButton, deal_TypeMoney, dealGroupText;
    DataBaseHelper dataBaseHelper;
    private ImageView deleteButton, dealGroupIcon;
    private boolean isUpdate;
    private TableRow pickGroup;

    int day,month,year;
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deal);

        dataBaseHelper = new DataBaseHelper(EditDealActivity.this);
        dataBaseHelper.open();

        deal_Money = (EditText) findViewById(R.id.edit_Money_Deal);
        deal_TypeMoney = (TextView) findViewById(R.id.edit_deal_type_money);
        deal_Detail = (EditText) findViewById(R.id.edit_Detail_Deal);
        deal_Date = (EditText) findViewById(R.id.edit_Date_Deal);
        deal_Date.setInputType(InputType.TYPE_NULL);
        dealGroupText = (TextView) findViewById(R.id.dGroupDetails);
        dealGroupIcon = (ImageView)findViewById(R.id.dGroupImg);


        pickGroup = (TableRow) findViewById(R.id.dRowEditGroup);
        pickGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PickGroupActivity.class));
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

        deal_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(113);
            }
        });
        isUpdate = getIntent().getExtras().getBoolean("update");
        if(isUpdate) {
            dealID = getIntent().getExtras().getString("DId");
            dealMoney = getIntent().getExtras().getString("DMoney");
            dealDetail = getIntent().getExtras().getString("DDetail");
            dealTypemoney = getIntent().getExtras().getString("DTypeMoney");
            dealDate = getIntent().getExtras().getString("DDate");
            dealGroup = getIntent().getExtras().getString("DGroup");
            dealGroupDetailsPos = getIntent().getExtras().getInt("DGroupDetails");
            dealIcon = getIntent().getExtras().getInt("DGroupImg");

            deal_Money.setText(dealMoney);
            deal_TypeMoney.setText(dealTypemoney);
            deal_Detail.setText(dealDetail);
            deal_Date.setText(dealDate);
            String income [] = getResources().getStringArray(R.array.income_categories);
            dealGroupText.setText(income[dealGroupDetailsPos]);

            dealGroupIcon.setImageResource(dealIcon);
        }

        setListener();
        saveButton = (TextView) findViewById(R.id.saveDeal_action_text);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealMoney = deal_Money.getText().toString();
                dealTypemoney = deal_TypeMoney.getText().toString();
                dealDetail = deal_Detail.getText().toString();
                if (eDate != null)
                    Deal.setDealDate(eDate.getText().toString());

                Deal.setDealMoney(dealMoney);
                Deal.setDealTypeMoney(dealTypemoney);
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
                if (dealMoney.equals("") || dealDetail.equals("")) {
                    deal_Money.setError("Chưa điền thông tin");
                    deal_Detail.setError("Chưa điền thông tin");
                    return;
                } else {
                    dataBaseHelper.updateDeal(dealID);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

        deleteButton = (ImageView) findViewById(R.id.delete_deal);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.deleteDeal(dealID);
                Intent i = new Intent(getApplicationContext(), DealDetailsFragment.class);
                startActivity(i);
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
        eDate.setText(day + "-" + (month + 1) + "-" + year);


    }
}
