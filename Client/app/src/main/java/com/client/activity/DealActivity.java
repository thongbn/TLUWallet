package com.client.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.client.CustomAdapter.NumberTextWatcher;
import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.model.Deal;
import com.client.model.MyDeal;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DealActivity extends Activity {
    private com.rengwuxian.materialedittext.MaterialEditText deal_Money, deal_Detail, eDate , pickGroup;
    private ImageView imgGroup;
    private String dealMoney, dealDetail, idUser, saveSQL;
    DataBaseHelper dataBaseHelper;
    private String typeMoney;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deal);

        overridePendingTransition(R.anim.card_flip_left_in, R.anim.card_flip_right_out);

        FacebookSdk.sdkInitialize(getApplicationContext());
        dataBaseHelper = new DataBaseHelper(DealActivity.this);
        dataBaseHelper.open();

        deal_Money = (com.rengwuxian.materialedittext.MaterialEditText) findViewById(R.id.edit_Money);
        deal_Detail = (com.rengwuxian.materialedittext.MaterialEditText) findViewById(R.id.edit_Detail);
        eDate=(com.rengwuxian.materialedittext.MaterialEditText) findViewById(R.id.edit_Date);
        pickGroup = (com.rengwuxian.materialedittext.MaterialEditText) findViewById(R.id.pickGroup);
        imgGroup = (ImageView) findViewById(R.id.imageGroup);

        if(AccessToken.getCurrentAccessToken() != null){
          typeMoney = UserFB.getIdMoneyTypebyFB();
        }else {
          typeMoney = User.getIdMoneyType();
        }

        //pick group


        pickGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DealActivity.this, PickGroupActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        //money
        deal_Money.setHint(getResources().getString(R.string.common_edit_money)+ " (" + typeMoney + ")");
        deal_Money.addTextChangedListener(new NumberTextWatcher(deal_Money));

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        String formatSQL = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdfSQL = new SimpleDateFormat(formatSQL, Locale.US);
        eDate.setText(sdf.format(myCalendar.getTime()));
        saveSQL = sdfSQL.format(myCalendar.getTime());

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DealActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        RelativeLayout addButton = (RelativeLayout) findViewById(R.id.saveDeal_action_text);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dealMoney = deal_Money.getText().toString();
                dealDetail = deal_Detail.getText().toString();
                if (eDate != null)
                    Deal.setDealDate(saveSQL);

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
                if (dealMoney.equals("") || dealDetail.equals("") || pickGroup.equals(getResources().getString(R.string.common_pick_group))) {
                    deal_Money.setError(getText(R.string.common_error_field_not_set));
                    deal_Detail.setError(getText(R.string.common_error_field_not_set));
                    return;
                } else {
                    if(AccessToken.getCurrentAccessToken() != null){
                        dataBaseHelper.insertDealbyFB();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }else {
                        dataBaseHelper.insertDeal();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }



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

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null){
            if (resultCode == RESULT_OK){
                String groupName = data.getStringExtra("GroupName");
                Integer groupIcon = data.getExtras().getInt("GroupImg");
                pickGroup.setText(groupName);
                imgGroup.setImageResource(groupIcon);
            }

            if (resultCode == RESULT_CANCELED){
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
