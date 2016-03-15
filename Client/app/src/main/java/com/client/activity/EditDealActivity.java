package com.client.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.Deal;
import com.client.database.model.MyDeal;
import com.client.database.model.Plan;
import com.client.database.model.User;
import com.client.database.model.UserFB;
import com.client.fragment.DealDetailsFragment;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.util.Calendar;

/**
 * Created by ToanNguyen on 04/03/2016.
 */
public class EditDealActivity extends Activity{

    private EditText deal_Money, deal_Detail, eDate;
    private String dealMoney, dealDetail, dealTypemoney, idUser, dealID, dealDate;
    private Integer dealGroupDetailsPos, dealIcon, dealGroup;
    private TextView deal_TypeMoney, dealGroupText;
    DataBaseHelper dataBaseHelper;
    private ImageView deleteButton, dealGroupIcon, tranferButton;
    private boolean isUpdate;
    private RelativeLayout saveButton, clearButton;

    int day,month,year;
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deal);
        FacebookSdk.sdkInitialize(getApplicationContext());

        dataBaseHelper = new DataBaseHelper(EditDealActivity.this);
        dataBaseHelper.open();

        deal_Money = (EditText) findViewById(R.id.edit_Money_Deal);
        deal_TypeMoney = (TextView) findViewById(R.id.edit_deal_type_money);
        deal_Detail = (EditText) findViewById(R.id.edit_Detail_Deal);
        dealGroupText = (TextView) findViewById(R.id.dGroupDetails);
        dealGroupIcon = (ImageView)findViewById(R.id.dGroupImg);
        eDate=(EditText) findViewById(R.id.edit_Date_Deal);

        if(AccessToken.getCurrentAccessToken() != null){
            deal_TypeMoney.setText(UserFB.getIdMoneyTypebyFB());
        }else {
            deal_TypeMoney.setText(User.getIdMoneyType());
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

        eDate.setOnClickListener(new View.OnClickListener() {
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
            dealGroup = getIntent().getExtras().getInt("DGroup");
            dealGroupDetailsPos = getIntent().getExtras().getInt("DGroupDetails");
            dealIcon = getIntent().getExtras().getInt("DGroupImg");

            deal_Money.setText(dealMoney);

            deal_TypeMoney.setText(dealTypemoney);

            deal_Detail.setText(dealDetail);

            eDate.setText(dealDate);
            if (dealGroup == 1){
                String income [] = getResources().getStringArray(R.array.income_categories);
                dealGroupText.setText(income[dealGroupDetailsPos]);
            } else {
                String outcome [] = getResources().getStringArray(R.array.outcome_categories);
                dealGroupText.setText(outcome[dealGroupDetailsPos]);
            }



            dealGroupIcon.setImageResource(dealIcon);
        }

        setListener();
        saveButton = (RelativeLayout) findViewById(R.id.saveDeal_action_text);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealMoney = deal_Money.getText().toString();
                dealTypemoney = deal_TypeMoney.getText().toString();
                dealDetail = deal_Detail.getText().toString();
                if (eDate != null)
                    Deal.setDealDate(eDate.getText().toString());

                Deal.setDealMoney(dealMoney);
                Deal.setDealGroup(dealGroup);
                Deal.setDealGroupDetailsPos(dealGroupDetailsPos);
                Deal.setDealGroupIcon(dealIcon);

                Deal.setDealDetail(dealDetail);
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

        clearButton = (RelativeLayout) findViewById(R.id.cancelDeal_action_text);
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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        tranferButton = (ImageView) findViewById(R.id.tranfer_plan);
        tranferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealMoney = deal_Money.getText().toString();
                dealTypemoney = deal_TypeMoney.getText().toString();
                dealDetail = deal_Detail.getText().toString();
                if (eDate != null)
                    Plan.setPlanDate(eDate.getText().toString());

                Plan.setPlanMoney(dealMoney);
                Plan.setPlanGroup(dealGroup);
                Plan.setPlanGroupDetailsPos(dealGroupDetailsPos);
                Plan.setPlanGroupIcon(dealIcon);

                Plan.setPlanDetail(dealDetail);
                idUser = User.getIdNguoiDung();
                SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
                String facebookId = idFacebook.getString("idFB", "");
                Plan.getUserFB().setFacebookID(facebookId);
                Plan.getUser().setIdNguoiDung(idUser);
                //check if any of fields are vaccant
                if (dealMoney.equals("") || dealDetail.equals("")) {
                    deal_Money.setError("Chưa điền thông tin");
                    deal_Detail.setError("Chưa điền thông tin");
                    return;
                } else {
                    dataBaseHelper.insertPlan();
                    Toast.makeText(EditDealActivity.this, "Giao dịch đã được chuyển sang mục Dự định", Toast.LENGTH_LONG).show();
                }
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
            eDate.setText(day+"-"+(month+1)+"-"+year);
        }
    };
    private void setListener(){
        eDate.setInputType(InputType.TYPE_NULL);
        Calendar cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);
        eDate.setText(day + "-" + (month + 1) + "-" + year);

    }

}
