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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.model.MyPlan;
import com.client.model.Plan;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.util.Calendar;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class PlanActivity extends Activity{
    private EditText plan_Money, plan_Detail, eDate;
    private ImageView imgGroup;
    private String planMoney, planDetail, idUser;
    private TextView plan_TypeMoney, pickGroup;
    DataBaseHelper dataBaseHelper;
    private RelativeLayout saveButton, clearButton;

    int day,month,year;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        overridePendingTransition(R.anim.card_flip_left_in, R.anim.card_flip_right_out);

        FacebookSdk.sdkInitialize(getApplicationContext());
        dataBaseHelper = new DataBaseHelper(PlanActivity.this);
        dataBaseHelper.open();

        plan_Money = (EditText) findViewById(R.id.edit_Money);
        plan_TypeMoney = (TextView) findViewById(R.id.plan_type_money);
        plan_Detail = (EditText) findViewById(R.id.edit_Detail);
        eDate=(EditText) findViewById(R.id.edit_Date);
        imgGroup = (ImageView) findViewById(R.id.imageGroup);
        pickGroup = (TextView) findViewById(R.id.pickGroup);

        if(AccessToken.getCurrentAccessToken() != null){
            plan_TypeMoney.setText(UserFB.getIdMoneyTypebyFB());
        }else {
            plan_TypeMoney.setText(User.getIdMoneyType());
        }

        //pick group


        pickGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanActivity.this, PickGroupPlanActivity.class);
                startActivityForResult(intent, 2);
            }
        });


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

        setListener();
        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(113);
            }
        });

        saveButton = (RelativeLayout) findViewById(R.id.savePlan_action_text);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                planMoney = plan_Money.getText().toString();
                planDetail = plan_Detail.getText().toString();
                if (eDate != null)
                    Plan.setPlanDate(eDate.getText().toString());

                Plan.setPlanMoney(planMoney);
                Plan.setPlanDetail(planDetail);
                Plan.setPlanGroup(MyPlan.getPlanGroup());
                Plan.setPlanGroupDetailsPos(MyPlan.getPlanGroupDetailPos());
                Plan.setPlanGroupIcon(MyPlan.getPlanGroupImg());
                idUser = User.getIdNguoiDung();
                SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
                String facebookId = idFacebook.getString("idFB", "");
                Plan.getUserFB().setFacebookID(facebookId);
                Plan.getUser().setIdNguoiDung(idUser);
                //check if any of fields are vaccant
                if (planMoney.equals("") || planDetail.equals("") || pickGroup.equals("")) {
                    plan_Money.setError(getText(R.string.common_error_field_not_set));
                    plan_Detail.setError(getText(R.string.common_error_field_not_set));
                    return;
                } else {
                    if(AccessToken.getCurrentAccessToken() != null){
                        dataBaseHelper.insertPlanbyFB();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }else {
                        dataBaseHelper.insertPlan();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }



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

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && data != null) {
            if (resultCode == RESULT_OK) {
                String groupName = data.getStringExtra("GroupName");
                Integer groupIcon = data.getExtras().getInt("GroupImg");
                pickGroup.setText(groupName);
                imgGroup.setImageResource(groupIcon);
            }

            if (resultCode == RESULT_CANCELED) {
                imgGroup.setImageResource(R.drawable.icon_not_selected);
            }
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();

    }
}
