package com.client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.client.database.model.Deal;
import com.client.database.model.MyDeal;
import com.client.database.model.User;
import com.client.database.model.UserFB;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by nguye on 11/12/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;

    public static final String DATABASE_NAME = "quanlychitieu.db";
    public static final int DATABASE_VERSION = 1;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.ENGLISH);

    public static final String NGUOIDUNG_TABLE = "nguoidung",
            USER_ID = "idNguoiDung",
            EMAIL = "Email",
            PASSWORD = "Pass";

    public static final String FACEBOOK_TABLE = "facebook",
            FB_ID = "idFacebook",
            FB_EMAIL = "facebookEmail",
            FB_NAME = "facebookName";

    public static final String DEAL_TABLE = "deal",
            DEAL_ID = "idDeal",
            DEAL_GROUP = "dealGroup",
            DEAL_GROUP_DETAILS = "dealGroupDetails",
            DEAL_GROUP_ICON = "dealGroupIcon",
            DEAL_MONEY = "dealMoney",
            DEAL_TYPE_MONEY = "dealTypeMoney",
            DEAL_DETAIL = "dealDetail",
            DEAL_DATE = "dealDate",
            DEAL_USER_ID = "dealUserID",
            DEAL_FB_ID = "dealFbID";

    static final String DATABASE_CREATE_TABLE_NGUOIDUNG = "create table " +  NGUOIDUNG_TABLE
            + "(" + USER_ID + " integer primary key autoincrement,"
            +  PASSWORD + " text," +  EMAIL + " text);";

    static final String DATABASE_CREATE_TABLE_FACEBOOK = "create table " + FACEBOOK_TABLE
            + "(" + FB_ID + " string primary key,"
            + FB_EMAIL + " text not null, " + FB_NAME + " text);";
    static final String DATABASE_CREATE_TABLE_DEAL = "create table " + DEAL_TABLE
            + "(" + DEAL_ID + " integer primary key autoincrement, "
            + DEAL_GROUP + " integer not null, "
            + DEAL_GROUP_DETAILS + " integer not null, "
            + DEAL_GROUP_ICON + " int not null, "
            + DEAL_MONEY + " text not null, "
            + DEAL_TYPE_MONEY + " text not null, "
            + DEAL_DETAIL + " text, "
            + DEAL_DATE + " text not null, "
            + DEAL_USER_ID + " integet constraint " + DEAL_USER_ID + " references " + DEAL_TABLE + "(" + USER_ID + ") on delete cascade, "
            + DEAL_FB_ID + " string " + DEAL_FB_ID + " references " + DEAL_TABLE + "(" + FB_ID + ") on delete cascade);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(DATABASE_CREATE_TABLE_NGUOIDUNG);
        db.execSQL(DATABASE_CREATE_TABLE_FACEBOOK);
        db.execSQL(DATABASE_CREATE_TABLE_DEAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXITS " + NGUOIDUNG_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + FACEBOOK_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + DEAL_TABLE);

        onCreate(db);
    }

    public DataBaseHelper open() throws SQLException
    {
        db = this.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }


    /* Login helper */

    public boolean checkemail(String email){
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.NGUOIDUNG_TABLE + " WHERE " + DataBaseHelper.EMAIL + " = ?", new String[]{email});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return false;
            }
        }

        return true;
    }

    public void insertEntry(){
        ContentValues newValues = new ContentValues();
        //assign values for each row

        newValues.put(DataBaseHelper.PASSWORD, User.getPassword());
        newValues.put(DataBaseHelper.EMAIL, User.getEmail());

        //Insert the row into your table
        db.insert(DataBaseHelper.NGUOIDUNG_TABLE, null, newValues);

    }

    public void insertFacebookEntry(String facebookID, String email, String name){
        ContentValues newValues = new ContentValues();

        newValues.put(DataBaseHelper.FB_ID, facebookID);
        newValues.put(DataBaseHelper.FB_EMAIL, email);
        newValues.put(DataBaseHelper.FB_NAME, name);

        db.insert(DataBaseHelper.FACEBOOK_TABLE, null, newValues);
    }

    public boolean checkFBiD (String facebookID) throws SQLException{
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.FACEBOOK_TABLE + " WHERE " + DataBaseHelper.FB_ID + "=?", new String[]{facebookID});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return false;
            }
        }

        return true;
    }

    public int deleteEntry(String email){
        //String id = String.valueOf(idNguoiDung);
        String where = DataBaseHelper.EMAIL + "=?";
        int numberOFEntriesDeleted = db.delete(DataBaseHelper.NGUOIDUNG_TABLE, where, new String[]{email});

        return numberOFEntriesDeleted;
    }


    public void deleteDeal(String id){
        String where = DataBaseHelper.DEAL_ID + " = ? ";
        db.delete(DataBaseHelper.DEAL_TABLE, where, new String[]{id});

    }

    public User login(String Email, String Password) throws SQLException {
        String query = "SELECT " + DataBaseHelper.USER_ID + ", " + DataBaseHelper.EMAIL + "," + DataBaseHelper.PASSWORD + " FROM " + DataBaseHelper.NGUOIDUNG_TABLE + " WHERE " + DataBaseHelper.EMAIL + "=? AND " + DataBaseHelper.PASSWORD + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery(query, new String[]{Email, Password});
        User user = new User();
        if (mCursor.moveToFirst()) {
            mCursor.moveToFirst();
            user.setIdNguoiDung(mCursor.getString(0));
            user.setEmail(mCursor.getString(1));
            user.setPassword(mCursor.getString(2));
            mCursor.close();
        } else {
            user = null;
        }
        return user;
    }

    public UserFB loginFB (String fbEmail, String fbName) throws SQLException{
        UserFB userFB = new UserFB();
        String query = "Select " + DataBaseHelper.FB_ID + ", " + DataBaseHelper.FB_EMAIL + "," + DataBaseHelper.FB_NAME + " from " + DataBaseHelper.FACEBOOK_TABLE + " where " + DataBaseHelper.FB_EMAIL + "=? And " + DataBaseHelper.FB_NAME + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{fbEmail, fbName});
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            userFB.setFacebookID(cursor.getString(0));
            userFB.setEmailFB(cursor.getString(1));
            userFB.setNameFB(cursor.getString(2));
            cursor.close();
        } else {
            userFB = null;
        }
        return userFB;
    }

    public void insertDeal(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.DEAL_GROUP, Deal.getDealGroup());
        contentValues.put(DataBaseHelper.DEAL_GROUP_DETAILS, Deal.getDealGroupDetailsPos());
        contentValues.put(DataBaseHelper.DEAL_GROUP_ICON, Deal.getDealGroupIcon());
        contentValues.put(DataBaseHelper.DEAL_MONEY, Deal.getDealMoney());
        contentValues.put(DataBaseHelper.DEAL_TYPE_MONEY, Deal.getDealTypeMoney());
        contentValues.put(DataBaseHelper.DEAL_DETAIL, Deal.getDealDetail());
        contentValues.put(DataBaseHelper.DEAL_DATE, Deal.getDealDate());
        contentValues.put(DataBaseHelper.DEAL_USER_ID, Deal.getUser().getIdNguoiDung());

        db.insert(DataBaseHelper.DEAL_TABLE, null, contentValues);
    }

    public void insertDealbyFB(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.DEAL_GROUP, Deal.getDealGroup());
        contentValues.put(DataBaseHelper.DEAL_GROUP_DETAILS, Deal.getDealGroupDetailsPos());
        contentValues.put(DataBaseHelper.DEAL_GROUP_ICON, Deal.getDealGroupIcon());
        contentValues.put(DataBaseHelper.DEAL_MONEY, Deal.getDealMoney());
        contentValues.put(DataBaseHelper.DEAL_TYPE_MONEY, Deal.getDealTypeMoney());
        contentValues.put(DataBaseHelper.DEAL_DETAIL, Deal.getDealDetail());
        contentValues.put(DataBaseHelper.DEAL_DATE, Deal.getDealDate());
        contentValues.put(DataBaseHelper.DEAL_USER_ID, Deal.getUserFB().getFacebookID());

        db.insert(DataBaseHelper.DEAL_TABLE, null, contentValues);
    }


    public void updateEntry(String email, String password){
        //define the update row content
        ContentValues updateValues = new ContentValues();
        //Assign values for each row
        updateValues.put(DataBaseHelper.PASSWORD, password);

        String where = DataBaseHelper.EMAIL + " = ?";
        db.update(DataBaseHelper.NGUOIDUNG_TABLE, updateValues, where, new String[]{email});
    }

    public void updateDeal(String id){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.DEAL_GROUP, Deal.getDealGroup());
        values.put(DataBaseHelper.DEAL_GROUP_DETAILS, Deal.getDealGroupDetailsPos());
        values.put(DataBaseHelper.DEAL_MONEY, Deal.getDealMoney());
        values.put(DataBaseHelper.DEAL_TYPE_MONEY, Deal.getDealTypeMoney());
        values.put(DataBaseHelper.DEAL_DETAIL, Deal.getDealDetail());
        values.put(DataBaseHelper.DEAL_DATE, (Deal.getDealDate()));
        values.put(DataBaseHelper.DEAL_GROUP_ICON, Deal.getDealGroupIcon());

        String where = DataBaseHelper.DEAL_ID + " = ? ";
        db.update(DataBaseHelper.DEAL_TABLE, values, where, new String[]{id});
    }

    private Cursor getDealbyID(String dID) {
        db = this.getWritableDatabase();
        String from[] = { DEAL_ID, DEAL_MONEY, DEAL_TYPE_MONEY, DEAL_DATE, DEAL_DETAIL, DEAL_GROUP, DEAL_GROUP_DETAILS, DEAL_GROUP_ICON };
        String where = DEAL_USER_ID + "=?";
        String[] whereArgs = new String[]{dID+""};
        Cursor cursor = db.query(DEAL_TABLE, from, where, whereArgs, null, null, null, null);
        return cursor;
    }

    public void getDeal (String dID) {
        Cursor c = getDealbyID(dID);

        if(c != null)
        {
            while(c.moveToNext()){
                String dealID = c.getString(c.getColumnIndex(DEAL_ID));
                String dealMoney = c.getString(c.getColumnIndex(DEAL_MONEY));
                String dealTypeMoney = c.getString(c.getColumnIndex(DEAL_TYPE_MONEY));
                String dealDate = c.getString(c.getColumnIndex(DEAL_DATE));
                String dealDetail = c.getString(c.getColumnIndex(DEAL_DETAIL));
                Integer dealGroup = c.getInt(c.getColumnIndex(DEAL_GROUP));
                Integer dealGroupDetails = c.getInt(c.getColumnIndex(DEAL_GROUP_DETAILS));
                Integer dealGroupIcon = c.getInt(c.getColumnIndex(DEAL_GROUP_ICON));

                MyDeal.listDealiD.add(dealID);
                MyDeal.listDealMoney.add(dealMoney);
                MyDeal.listDealTypeMoney.add(dealTypeMoney);
                MyDeal.listDealDate.add(dealDate);
                MyDeal.listDealDetails.add(dealDetail);
                MyDeal.listDealGroup.add(dealGroup);
                MyDeal.listDealGroupDetailsPos.add(dealGroupDetails);
                MyDeal.listDealGroupIcon.add(dealGroupIcon);
            }
        }
    }

    private Cursor getDealbyFbID(String dID) {
        db = this.getWritableDatabase();
        String from[] = { DEAL_ID, DEAL_MONEY, DEAL_TYPE_MONEY, DEAL_DATE, DEAL_DETAIL, DEAL_GROUP, DEAL_GROUP_DETAILS, DEAL_GROUP_ICON };
        String where = DEAL_FB_ID + "=?";
        String[] whereArgs = new String[]{dID+""};
        Cursor cursor = db.query(DEAL_TABLE, from, where, whereArgs, null, null, null, null);
        return cursor;
    }

    public void getDealbyFB (String dID) {
        Cursor c = getDealbyFbID(dID);

        if(c != null)
        {
            while(c.moveToNext()){
                String dealID = c.getString(c.getColumnIndex(DEAL_ID));
                String dealMoney = c.getString(c.getColumnIndex(DEAL_MONEY));
                String dealTypeMoney = c.getString(c.getColumnIndex(DEAL_TYPE_MONEY));
                String dealDate = c.getString(c.getColumnIndex(DEAL_DATE));
                String dealDetail = c.getString(c.getColumnIndex(DEAL_DETAIL));
                Integer dealGroup = c.getInt(c.getColumnIndex(DEAL_GROUP));
                Integer dealGroupDetails = c.getInt(c.getColumnIndex(DEAL_GROUP_DETAILS));
                Integer dealGroupIcon = c.getInt(c.getColumnIndex(DEAL_GROUP_ICON));

                MyDeal.listDealiD.add(dealID);
                MyDeal.listDealMoney.add(dealMoney);
                MyDeal.listDealTypeMoney.add(dealTypeMoney);
                MyDeal.listDealDate.add(dealDate);
                MyDeal.listDealDetails.add(dealDetail);
                MyDeal.listDealGroup.add(dealGroup);
                MyDeal.listDealGroupDetailsPos.add(dealGroupDetails);
                MyDeal.listDealGroupIcon.add(dealGroupIcon);
            }
        }
    }

}
