package com.client.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.client.R;
import com.client.database.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by quang on 04/01/2016.
 */
public class DealActivity extends Activity {
    private Button btAdd;
    private EditText deal_Group, deal_Money, deal_Detail, deal_Date, deal_Wallet;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private String dealGroup, dealMoney, dealDetail, dealDate, idWallet;
    private boolean isUpdate;
    int day,month,year;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    Calendar dateCalendar = Calendar.getInstance(); ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deal);

        //get Instance of Database Adapter
        dataBaseHelper = new DataBaseHelper(DealActivity.this);
        dataBaseHelper.open();

        deal_Group = (EditText) findViewById(R.id.edit_Group);
        deal_Money = (EditText) findViewById(R.id.edit_Money);
        deal_Detail = (EditText) findViewById(R.id.edit_Detail);
        dateCalendar.set(year, month, day);


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
    /**
     * xử lý DatePickerDialog
     */
    private DatePickerDialog.OnDateSetListener dateChange= new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year1, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            year=year1;
            month=monthOfYear;
            day=dayOfMonth;
            deal_Date =(EditText) findViewById(R.id.edit_Date);
            deal_Date.setText(day + "-" + (month + 1) + "-" + year);
        }
    };
    public void setCurrentDateOnView()
    {
        deal_Date = (EditText) findViewById(R.id.edit_Date);
        Calendar cal=Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        deal_Date.setText(day+"-"+(month+1)+"-"+year);
    }
}
