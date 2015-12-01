package com.client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                        WALLET_USER_ID = "idUser";

    public static final String NGUOIDUNG_TABLE = "nguoidung",
                        USER_ID = "idNguoiDung",
                        EMAIL = "Email",
                        PASSWORD = "Pass";



    static final String DATABASE_CREATE_TABLE_NGUOIDUNG = "create table " +  NGUOIDUNG_TABLE
            + "(" + USER_ID + " integer primary key autoincrement,"
            +  PASSWORD + " text," +  EMAIL + " text);";
    static final String DATABASE_CREATE_TABLE_WALLET = "create table " +  WALLET_TABLE
            + "(" + WALLET_ID + " integer primary key autoincrement," +  WALLET_NAME + " varchar(100) not null, "
            + WALLET_MONEY + " text not null," +  WALLET_TYPE_MONEY + " text not null, "
            + WALLET_USER_ID + " integer not null constraint " + WALLET_USER_ID + " references " + WALLET_TABLE + "(" + USER_ID + ") on delete cascade);";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(DATABASE_CREATE_TABLE_NGUOIDUNG);
        db.execSQL(DATABASE_CREATE_TABLE_WALLET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXITS " + WALLET_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + NGUOIDUNG_TABLE);

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

    public void insertEntry(String email, String password){
        ContentValues newValues = new ContentValues();
        //assign values for each row

        newValues.put(DataBaseHelper.PASSWORD, password);
        newValues.put(DataBaseHelper.EMAIL, email);

        //Insert the row into your table
        db.insert(DataBaseHelper.NGUOIDUNG_TABLE, null, newValues);

    }

    public int deleteEntry(String email){
        //String id = String.valueOf(idNguoiDung);
        String where = DataBaseHelper.EMAIL + "=?";
        int numberOFEntriesDeleted = db.delete(DataBaseHelper.NGUOIDUNG_TABLE, where, new String[]{email});

        return numberOFEntriesDeleted;
    }

    public boolean login(String email, String password) throws SQLException
    {

        Cursor mCursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.NGUOIDUNG_TABLE + " WHERE " + DataBaseHelper.EMAIL + "=? AND " + DataBaseHelper.PASSWORD + "=?", new String[]{email, password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public void updateEntry(String email, String password){
        //define the update row content
        ContentValues updateValues = new ContentValues();
        //Assign values for each row
        updateValues.put(DataBaseHelper.PASSWORD, password);

        String where = DataBaseHelper.EMAIL + " = ?";
        db.update(DataBaseHelper.NGUOIDUNG_TABLE, updateValues, where, new String[]{email});
    }

}
