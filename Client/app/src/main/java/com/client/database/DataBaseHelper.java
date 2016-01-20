package com.client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.client.CustomWalletList.CustomWalletList;
import com.client.database.model.Deal;
import com.client.database.model.MyWallet;
import com.client.database.model.User;
import com.client.database.model.UserFB;
import com.client.database.model.Wallet;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye on 11/12/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;

    public static final String DATABASE_NAME = "quanlychitieu.db";
    public static final int DATABASE_VERSION = 1;


    public static final String WALLET_TABLE = "wallet",
                        WALLET_ID = "idWallet",
                        WALLET_NAME = "walletName",
                        WALLET_MONEY = "walletMoney",
                        WALLET_TYPE_MONEY = "moneyType",
                        WALLET_USER_ID = "idUser",
                        WALLET_FB_ID = "idFB";

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
                        DEAL_MONEY = "dealMoney",
                        DEAL_DEATAIL = "dealDetail",
                        DEAL_DATE = "dealDate",
                        DEAL_WALLET_ID = "walletID";

    static final String DATABASE_CREATE_TABLE_NGUOIDUNG = "create table " +  NGUOIDUNG_TABLE
            + "(" + USER_ID + " integer primary key autoincrement,"
            +  PASSWORD + " text," +  EMAIL + " text);";
    static final String DATABASE_CREATE_TABLE_WALLET = "create table " +  WALLET_TABLE
            + "(" + WALLET_ID + " integer primary key autoincrement," +  WALLET_NAME + " varchar(100) not null, "
            + WALLET_MONEY + " text not null," +  WALLET_TYPE_MONEY + " text not null, "
            + WALLET_USER_ID + " integer constraint " + WALLET_USER_ID + " references " + WALLET_TABLE + "(" + USER_ID + ") on delete cascade, "
            + WALLET_FB_ID + " string " + WALLET_FB_ID + " references " + WALLET_TABLE + "(" + FB_ID + ") on delete cascade);";

    static final String DATABASE_CREATE_TABLE_FACEBOOK = "create table " + FACEBOOK_TABLE
            + "(" + FB_ID + " string primary key,"
            + FB_EMAIL + " text not null, " + FB_NAME + " text);";
    static final String DATABASE_CREATE_TABLE_DEAL = "create table " + DEAL_TABLE
            + "(" + DEAL_ID + " integer primary key autoincrement, "
            + DEAL_GROUP + " integer not null, "
            + DEAL_MONEY + " numeric not null, "
            + DEAL_DEATAIL + " text, "
            + DEAL_DATE + " datetime not null, "
            + DEAL_WALLET_ID + " integet constraint " + DEAL_WALLET_ID + " references " + DEAL_TABLE + "(" + WALLET_ID + ") on delete cascade);";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(DATABASE_CREATE_TABLE_NGUOIDUNG);
        db.execSQL(DATABASE_CREATE_TABLE_WALLET);
        db.execSQL(DATABASE_CREATE_TABLE_FACEBOOK);
        db.execSQL(DATABASE_CREATE_TABLE_DEAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXITS " + WALLET_TABLE);
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

    public int deleteWallet(){
        String where = DataBaseHelper.WALLET_ID + " = ? ";
        int num = db.delete(DataBaseHelper.WALLET_TABLE, where, new String[]{Wallet.getIdWallet()});

        return num;
    }

    public int deleteDeal(){
        String where = DataBaseHelper.DEAL_ID + " = ? ";
        int num = db.delete(DataBaseHelper.DEAL_TABLE, where, new String[]{Deal.getIdDeal()});

        return num;
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
        MyWallet.listWalletMoney.clear();
        MyWallet.listWalletName.clear();
        MyWallet.listWalletMoneyType.clear();
        getDataWalletByUserID(User.getIdNguoiDung());
        db.close();
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
        MyWallet.listWalletMoney.clear();
        MyWallet.listWalletName.clear();
        MyWallet.listWalletMoneyType.clear();
        getDataWalletByFbID(UserFB.getFacebookID());
        db.close();
        return userFB;
    }

    public void insertWallet(){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.WALLET_NAME, Wallet.getWalletName());
        values.put(DataBaseHelper.WALLET_MONEY, Wallet.getWalletMoney());
        values.put(DataBaseHelper.WALLET_TYPE_MONEY, Wallet.getWalletType());
        values.put(DataBaseHelper.WALLET_USER_ID, Wallet.getUser().getIdNguoiDung());

        MyWallet.listWalletMoney.clear();
        MyWallet.listWalletName.clear();
        MyWallet.listWalletMoneyType.clear();
        getDataWalletByUserID(User.getIdNguoiDung());
        MyWallet.listWalletName.add(Wallet.getWalletName());
        MyWallet.listWalletMoney.add(Wallet.getWalletMoney());
        MyWallet.listWalletMoneyType.add(Wallet.getWalletType());

        db.insert(DataBaseHelper.WALLET_TABLE, null, values);
    }
    public void insertWalletByFB(){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.WALLET_NAME, Wallet.getWalletName());
        values.put(DataBaseHelper.WALLET_MONEY, Wallet.getWalletMoney());
        values.put(DataBaseHelper.WALLET_TYPE_MONEY, Wallet.getWalletType());
        values.put(DataBaseHelper.WALLET_FB_ID, Wallet.getUserFB().getFacebookID());

        MyWallet.listWalletMoney.clear();
        MyWallet.listWalletName.clear();
        MyWallet.listWalletMoneyType.clear();
        getDataWalletByFbID(UserFB.getFacebookID());
        MyWallet.listWalletName.add(Wallet.getWalletName());
        MyWallet.listWalletMoney.add(Wallet.getWalletMoney());
        MyWallet.listWalletMoneyType.add(Wallet.getWalletType());

        db.insert(DataBaseHelper.WALLET_TABLE, null, values);
    }

    public void insertDeal(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.DEAL_GROUP, Deal.getDealGroup());
        contentValues.put(DataBaseHelper.DEAL_MONEY, Deal.getDealMoney());
        contentValues.put(DataBaseHelper.DEAL_DEATAIL, Deal.getDealDetail());
        contentValues.put(DataBaseHelper.DEAL_DATE, Deal.getDealDate());
        contentValues.put(DataBaseHelper.DEAL_WALLET_ID, Deal.getWallet().getIdWallet());

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

    public void updateWallet(){
        ContentValues upWallet = new ContentValues();
        upWallet.put(DataBaseHelper.WALLET_NAME, Wallet.getWalletName());
        upWallet.put(DataBaseHelper.WALLET_MONEY, Wallet.getWalletMoney());
        upWallet.put(DataBaseHelper.WALLET_TYPE_MONEY, Wallet.getWalletType());

        String where = DataBaseHelper.WALLET_ID + "= ?";
        db.update(DataBaseHelper.WALLET_TABLE, upWallet, where, new String[]{Wallet.getIdWallet()});
    }

    public void updateDeal(){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.DEAL_GROUP, Deal.getDealGroup());
        values.put(DataBaseHelper.DEAL_MONEY, Deal.getDealMoney());
        values.put(DataBaseHelper.DEAL_DEATAIL, Deal.getDealDetail());
        values.put(DataBaseHelper.DEAL_DATE, Deal.getDealDate());

        String where = DataBaseHelper.DEAL_ID + " = ? ";
        db.update(DataBaseHelper.DEAL_TABLE, values, where, new String[]{Deal.getIdDeal()});
    }


    private Cursor getWalletValuesbyUserID(String userID) {
        db = this.getWritableDatabase();
        String from[] = { WALLET_NAME, WALLET_MONEY, WALLET_TYPE_MONEY  };
        String where = WALLET_USER_ID + "=?";
        String[] whereArgs = new String[]{userID+""};
        Cursor cursor = db.query(WALLET_TABLE, from, where, whereArgs, null, null, null, null);
        return cursor;
    }

    public void getDataWalletByUserID(String id) {

        Cursor c = getWalletValuesbyUserID(id);

        if(c != null)
        {
            while(c.moveToNext()){
                String name = c.getString(c.getColumnIndex(WALLET_NAME));
                String money = c.getString(c.getColumnIndex(WALLET_MONEY));
                String typemoney = c.getString(c.getColumnIndex(WALLET_TYPE_MONEY));
                MyWallet.listWalletName.add(name);
                MyWallet.listWalletMoney.add(money);
                MyWallet.listWalletMoneyType.add(typemoney);
            }
        }
    }

    private Cursor getWalletValuesbyFbID(String userID) {
        db = this.getWritableDatabase();
        String from[] = { WALLET_NAME, WALLET_MONEY, WALLET_TYPE_MONEY };
        String where = WALLET_FB_ID + "=?";
        String[] whereArgs = new String[]{userID+""};
        Cursor cursor = db.query(WALLET_TABLE, from, where, whereArgs, null, null, null, null);
        return cursor;
    }

    public void getDataWalletByFbID(String id) {

        Cursor c = getWalletValuesbyFbID(id);

        if(c != null)
        {
            while(c.moveToNext()){
                String name = c.getString(c.getColumnIndex(WALLET_NAME));
                String money = c.getString(c.getColumnIndex(WALLET_MONEY));
                String typemoney = c.getString(c.getColumnIndex(WALLET_TYPE_MONEY));
                MyWallet.listWalletName.add(name);
                MyWallet.listWalletMoney.add(money);
                MyWallet.listWalletMoneyType.add(typemoney);
            }
        }
    }

}
