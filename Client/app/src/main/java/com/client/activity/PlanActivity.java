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
import com.client.model.MyPlan;
import com.client.model.Plan;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class PlanActivity extends Activity{
    private com.rengwuxian.materialedittext.MaterialEditText plan_Money, plan_Detail, eDate, pickGroup;
    private ImageView imgGroup;
    private String planMoney, planDetail, idUser, saveSQL;
    DataBaseHelper dataBaseHelper;
    private RelativeLayout saveButton, clearButton;
    Calendar myCalendar = Calendar.getInstance();
    private String typeMoney;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        overridePendingTransition(R.anim.card_flip_left_in, R.anim.card_flip_right_out);

        FacebookSdk.sdkInitialize(getApplicationContext());
        dataBaseHelper = new DataBaseHelper(PlanActivity.this);
        dataBaseHelper.open();

        plan_Money = (com.rengwuxian.materialedittext.MaterialEditText) findViewById(R.id.edit_Money);
        plan_Detail = (com.rengwuxian.materialedittext.MaterialEditText) findViewById(R.id.edit_Detail);
        eDate=(com.rengwuxian.materialedittext.MaterialEditText) findViewById(R.id.edit_Date);
        imgGroup = (ImageView) findViewById(R.id.imageGroup);
        pickGroup = (com.rengwuxian.materialedittext.MaterialEditText) findViewById(R.id.pickGroup);

        if(AccessToken.getCurrentAccessToken() != null){
          typeMoney = UserFB.getIdMoneyTypebyFB();
        }else {
          typeMoney = User.getIdMoneyType();
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
        plan_Money.setHint(getResources().getString(R.string.common_edit_money)+ " (" + typeMoney + ")");
        plan_Money.addTextChangedListener(new NumberTextWatcher(plan_Money));

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
                new DatePickerDialog(PlanActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        saveButton = (RelativeLayout) findViewById(R.id.savePlan_action_text);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                planMoney = plan_Money.getText().toString();
                planDetail = plan_Detail.getText().toString();
                if (eDate != null)
                    Plan.setPlanDate(saveSQL);

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
                if (planMoney.equals("") || planDetail.equals("") || pickGroup.equals(getResources().getString(R.string.common_pick_group))) {
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
