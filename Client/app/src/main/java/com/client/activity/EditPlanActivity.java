package com.client.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.Deal;
import com.client.database.model.Plan;
import com.client.database.model.User;
import com.client.database.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.util.Calendar;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class EditPlanActivity extends Activity{
    private EditText plan_Money, plan_Detail, eDate;
    private String planMoney, planDetail, planTypemoney, idUser, planID, planDate;
    private Integer planGroupDetailsPos, planIcon, planGroup;
    private TextView plan_TypeMoney, planGroupText;
    DataBaseHelper dataBaseHelper;
    private ImageView deleteButton, planGroupIcon, tranferButton;
    private boolean isUpdate;
    private RelativeLayout saveButton, clearButton;

    int day,month,year;
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        FacebookSdk.sdkInitialize(getApplicationContext());

        dataBaseHelper = new DataBaseHelper(EditPlanActivity.this);
        dataBaseHelper.open();

        plan_Money = (EditText) findViewById(R.id.edit_Money_Plan);
        plan_TypeMoney = (TextView) findViewById(R.id.edit_plan_type_money);
        plan_Detail = (EditText) findViewById(R.id.edit_Detail_Plan);
        planGroupText = (TextView) findViewById(R.id.pGroupDetails);
        planGroupIcon = (ImageView)findViewById(R.id.pGroupImg);
        eDate=(EditText) findViewById(R.id.edit_Date_Plan);

        if(AccessToken.getCurrentAccessToken() != null){
            plan_TypeMoney.setText(UserFB.getIdMoneyTypebyFB());
        }else {
            plan_TypeMoney.setText(User.getIdMoneyType());
        }


        //money
        plan_Money.addTextChangedListener(new TextWatcher() {
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
                    plan_Money.setText(finalValue.reverse());
                    plan_Money.setSelection(finalValue.length());
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
            planID = getIntent().getExtras().getString("PId");
            planMoney = getIntent().getExtras().getString("PMoney");
            planDetail = getIntent().getExtras().getString("PDetail");
            planTypemoney = getIntent().getExtras().getString("PTypeMoney");
            planDate = getIntent().getExtras().getString("PDate");
            planGroup = getIntent().getExtras().getInt("PGroup");
            planGroupDetailsPos = getIntent().getExtras().getInt("PGroupDetails");
            planIcon = getIntent().getExtras().getInt("PGroupImg");

            plan_Money.setText(planMoney);

            plan_TypeMoney.setText(planTypemoney);

            plan_Detail.setText(planDetail);

            eDate.setText(planDate);
            if (planGroup == 1){
                String income [] = getResources().getStringArray(R.array.income_categories);
                planGroupText.setText(income[planGroupDetailsPos]);
            } else {
                String outcome [] = getResources().getStringArray(R.array.outcome_categories);
                planGroupText.setText(outcome[planGroupDetailsPos]);
            }



            planGroupIcon.setImageResource(planIcon);
        }

        setListener();
        saveButton = (RelativeLayout) findViewById(R.id.savePlan_action_text);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planMoney = plan_Money.getText().toString();
                planTypemoney = plan_TypeMoney.getText().toString();
                planDetail = plan_Detail.getText().toString();
                if (eDate != null)
                    Plan.setPlanDate(eDate.getText().toString());

                Plan.setPlanMoney(planMoney);
                Plan.setPlanGroup(planGroup);
                Plan.setPlanGroupDetailsPos(planGroupDetailsPos);
                Plan.setPlanGroupIcon(planIcon);

                Plan.setPlanDetail(planDetail);
                idUser = User.getIdNguoiDung();
                SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
                String facebookId = idFacebook.getString("idFB", "");
                Plan.getUserFB().setFacebookID(facebookId);
                Plan.getUser().setIdNguoiDung(idUser);
                //check if any of fields are vaccant
                if (planMoney.equals("") || planDetail.equals("")) {
                    plan_Money.setError("Chưa điền thông tin");
                    plan_Detail.setError("Chưa điền thông tin");
                    return;
                } else {
                    dataBaseHelper.updatePlan(planID);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });

        clearButton = (RelativeLayout) findViewById(R.id.cancelPlan_action_text);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deleteButton = (ImageView) findViewById(R.id.delete_plan);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.deletePlan(planID);
                Toast.makeText(EditPlanActivity.this, "Giao dịch đã được xóa", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        tranferButton = (ImageView) findViewById(R.id.tranfer_plan);

        tranferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planMoney = plan_Money.getText().toString();
                planTypemoney = plan_TypeMoney.getText().toString();
                planDetail = plan_Detail.getText().toString();
                if (eDate != null)
                    Deal.setDealDate(eDate.getText().toString());

                Deal.setDealMoney(planMoney);
                Deal.setDealGroup(planGroup);
                Deal.setDealGroupDetailsPos(planGroupDetailsPos);
                Deal.setDealGroupIcon(planIcon);

                Deal.setDealDetail(planDetail);
                idUser = User.getIdNguoiDung();
                SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
                String facebookId = idFacebook.getString("idFB", "");
                Deal.getUserFB().setFacebookID(facebookId);
                Deal.getUser().setIdNguoiDung(idUser);
                //check if any of fields are vaccant
                if (planMoney.equals("") || planDetail.equals("")) {
                    plan_Money.setError("Chưa điền thông tin");
                    plan_Detail.setError("Chưa điền thông tin");
                    return;
                } else {
                    dataBaseHelper.insertDeal();
                    Toast.makeText(EditPlanActivity.this, "Giao dịch đã được tạo", Toast.LENGTH_LONG).show();
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