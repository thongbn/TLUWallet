package com.client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.client.database.model.User;
import com.client.database.model.Wallet;

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

    static final String DATABASE_CREATE_TABLE_NGUOIDUNG = "create table " +  NGUOIDUNG_TABLE
            + "(" + USER_ID + " integer primary key autoincrement,"
            +  PASSWORD + " text," +  EMAIL + " text);";
    static final String DATABASE_CREATE_TABLE_WALLET = "create table " +  WALLET_TABLE
            + "(" + WALLET_ID + " integer primary key autoincrement," +  WALLET_NAME + " varchar(100) not null, "
            + WALLET_MONEY + " text not null," +  WALLET_TYPE_MONEY + " text not null, "
            + WALLET_USER_ID + " integer constraint " + WALLET_USER_ID + " references " + WALLET_TABLE + "(" + USER_ID + ") on delete cascade, "
            + WALLET_FB_ID + " integer constraint " + WALLET_FB_ID + " references " + WALLET_TABLE + "(" + FB_ID + ") on delete cascade);";

    static final String DATABASE_CREATE_TABLE_FACEBOOK = "create table " + FACEBOOK_TABLE
            + "(" + FB_ID + " integer primary key,"
            + FB_EMAIL + " text not null, " + FB_NAME + " text);";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(DATABASE_CREATE_TABLE_NGUOIDUNG);
        db.execSQL(DATABASE_CREATE_TABLE_WALLET);
        db.execSQL(DATABASE_CREATE_TABLE_FACEBOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXITS " + WALLET_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + NGUOIDUNG_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + FACEBOOK_TABLE);

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
        db.close();
        return user;
    }

    public void insertWallet(){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.WALLET_NAME, Wallet.getWalletName());
        values.put(DataBaseHelper.WALLET_MONEY, Wallet.getWalletMoney());
        values.put(DataBaseHelper.WALLET_TYPE_MONEY, Wallet.getWalletType());
        values.put(DataBaseHelper.WALLET_USER_ID, Wallet.getUser().getIdNguoiDung());

        db.insert(DataBaseHelper.WALLET_TABLE, null, values);
    }
    public void insertWalletByFB(){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.WALLET_NAME, Wallet.getWalletName());
        values.put(DataBaseHelper.WALLET_MONEY, Wallet.getWalletMoney());
        values.put(DataBaseHelper.WALLET_TYPE_MONEY, Wallet.getWalletType());
        values.put(DataBaseHelper.WALLET_FB_ID, Wallet.getUserFB().getFacebookID());

        db.insert(DataBaseHelper.WALLET_TABLE, null, values);
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

}
