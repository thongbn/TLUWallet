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
import android.widget.Toast;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.model.Deal;
import com.client.model.Plan;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ToanNguyen on 04/03/2016.
 */
public class EditDealActivity extends Activity{

    private EditText deal_Money, deal_Detail, eDate;
    private String dealMoney, dealDetail, dealTypemoney, idUser, dealID, saveSQL, dealDate;
    private Integer dealGroupDetailsPos, dealIcon, dealGroup;
    private TextView deal_TypeMoney;
    DataBaseHelper dataBaseHelper;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deal);

        overridePendingTransition(R.anim.card_flip_right_in, R.anim.card_flip_left_out);

        FacebookSdk.sdkInitialize(getApplicationContext());

        dataBaseHelper = new DataBaseHelper(EditDealActivity.this);
        dataBaseHelper.open();

        deal_Money = (EditText) findViewById(R.id.edit_Money_Deal);
        deal_TypeMoney = (TextView) findViewById(R.id.edit_deal_type_money);
        deal_Detail = (EditText) findViewById(R.id.edit_Detail_Deal);
        TextView dealGroupText = (TextView) findViewById(R.id.dGroupDetails);
        ImageView dealGroupIcon = (ImageView)findViewById(R.id.dGroupImg);
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

        boolean isUpdate = getIntent().getExtras().getBoolean("update");
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
            saveSQL = dealDate;

            if (dealGroup == 1){
                String income [] = getResources().getStringArray(R.array.income_categories);
                dealGroupText.setText(income[dealGroupDetailsPos]);
            } else {
                String outcome [] = getResources().getStringArray(R.array.outcome_categories);
                dealGroupText.setText(outcome[dealGroupDetailsPos]);
            }



            dealGroupIcon.setImageResource(dealIcon);
        }


        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditDealActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        RelativeLayout saveButton = (RelativeLayout) findViewById(R.id.saveDeal_action_text);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealMoney = deal_Money.getText().toString();
                dealTypemoney = deal_TypeMoney.getText().toString();
                dealDetail = deal_Detail.getText().toString();

                if (eDate != null)
                    Deal.setDealDate(saveSQL);

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
                    deal_Money.setError(getText(R.string.common_error_field_not_set));
                    deal_Detail.setError("Chưa điền thông tin");
                    return;
                } else {
                    dataBaseHelper.updateDeal(dealID);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        RelativeLayout clearButton = (RelativeLayout) findViewById(R.id.cancelDeal_action_text);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView deleteButton = (ImageView) findViewById(R.id.delete_deal);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataBaseHelper.deleteDeal(dealID);
                Toast.makeText(EditDealActivity.this, getText(R.string.common_delete), Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ImageView tranferButton = (ImageView) findViewById(R.id.tranfer_plan);
        tranferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealMoney = deal_Money.getText().toString();
                dealTypemoney = deal_TypeMoney.getText().toString();
                dealDetail = deal_Detail.getText().toString();

                if (eDate != null)
                    Plan.setPlanDate(saveSQL);

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
                    deal_Money.setError(getText(R.string.common_error_field_not_set));
                    deal_Detail.setError(getText(R.string.common_error_field_not_set));
                    return;
                } else {
                    dataBaseHelper.insertPlan();
                    Toast.makeText(EditDealActivity.this, getText(R.string.common_create_plan), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        String formatSQL = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdfSQL = new SimpleDateFormat(formatSQL, Locale.US);
        eDate.setText(sdf.format(myCalendar.getTime()));
        saveSQL = sdfSQL.format(myCalendar.getTime());

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();

    }

}
