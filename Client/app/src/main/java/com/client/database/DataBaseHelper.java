package com.client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.client.model.Deal;
import com.client.model.MyDeal;
import com.client.model.MyPlan;
import com.client.model.Plan;
import com.client.model.User;
import com.client.model.UserFB;
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
            PASSWORD = "Pass",
            MONEYTYPE = "MoneyType";

    public static final String FACEBOOK_TABLE = "facebook",
            FB_ID = "idFacebook",
            FB_EMAIL = "facebookEmail",
            FB_NAME = "facebookName",
            FB_MONEYTYPE = "facebookMoneyType";

    public static final String DEAL_TABLE = "deal",
            DEAL_ID = "idDeal",
            DEAL_GROUP = "dealGroup",
            DEAL_GROUP_DETAILS = "dealGroupDetails",
            DEAL_GROUP_ICON = "dealGroupIcon",
            DEAL_MONEY = "dealMoney",
            DEAL_DETAIL = "dealDetail",
            DEAL_DATE = "dealDate",
            DEAL_USER_ID = "dealUserID",
            DEAL_FB_ID = "dealFbID";

    public static final String PLAN_TABLE = "plan",
            PLAN_ID = "idPlan",
            PLAN_GROUP = "planGroup",
            PLAN_GROUP_DETAILS = "planGroupDetails",
            PLAN_GROUP_ICON = "planGroupIcon",
            PLAN_MONEY = "planMoney",
            PLAN_DETAIL = "planDetail",
            PLAN_DATE = "planDate",
            PLAN_USER_ID = "planUserID",
            PLAN_FB_ID = "planFbID";

    static final String DATABASE_CREATE_TABLE_NGUOIDUNG = "create table " +  NGUOIDUNG_TABLE
            + "(" + USER_ID + " integer primary key autoincrement,"
            +  PASSWORD + " text," +  EMAIL + " text, "
            + MONEYTYPE + " string);";

    static final String DATABASE_CREATE_TABLE_FACEBOOK = "create table " + FACEBOOK_TABLE
            + "(" + FB_ID + " string primary key,"
            + FB_EMAIL + " text not null, " + FB_NAME + " text, "
            + FB_MONEYTYPE + " string);";
    static final String DATABASE_CREATE_TABLE_DEAL = "create table " + DEAL_TABLE
            + "(" + DEAL_ID + " integer primary key autoincrement, "
            + DEAL_GROUP + " integer not null, "
            + DEAL_GROUP_DETAILS + " integer not null, "
            + DEAL_GROUP_ICON + " int not null, "
            + DEAL_MONEY + " text not null, "
            + DEAL_DETAIL + " text, "
            + DEAL_DATE + " text not null, "
            + DEAL_USER_ID + " integet constraint " + DEAL_USER_ID + " references " + DEAL_TABLE + "(" + USER_ID + ") on delete cascade, "
            + DEAL_FB_ID + " string " + DEAL_FB_ID + " references " + DEAL_TABLE + "(" + FB_ID + ") on delete cascade);";

    static final String DATABASE_CREATE_TABLE_PLAN = "create table " + PLAN_TABLE
            + "(" + PLAN_ID + " integer primary key autoincrement, "
            + PLAN_GROUP + " integer not null, "
            + PLAN_GROUP_DETAILS + " integer not null, "
            + PLAN_GROUP_ICON + " int not null, "
            + PLAN_MONEY + " text not null, "
            + PLAN_DETAIL + " text, "
            + PLAN_DATE + " text not null, "
            + PLAN_USER_ID + " integet constraint " + PLAN_USER_ID + " references " + PLAN_TABLE + "(" + USER_ID + ") on delete cascade, "
            + PLAN_FB_ID + " string " + PLAN_FB_ID + " references " + PLAN_TABLE + "(" + FB_ID + ") on delete cascade);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(DATABASE_CREATE_TABLE_NGUOIDUNG);
        db.execSQL(DATABASE_CREATE_TABLE_FACEBOOK);
        db.execSQL(DATABASE_CREATE_TABLE_DEAL);
        db.execSQL(DATABASE_CREATE_TABLE_PLAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXITS " + NGUOIDUNG_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + FACEBOOK_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + DEAL_TABLE);
        db.execSQL("DROP TABLE IF EXITS " + PLAN_TABLE);

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

    public User login(String Email, String Password) throws SQLException {
        String query = "SELECT " + DataBaseHelper.USER_ID + ", " + DataBaseHelper.EMAIL + "," + DataBaseHelper.PASSWORD + " ," + DataBaseHelper.MONEYTYPE + " FROM " + DataBaseHelper.NGUOIDUNG_TABLE + " WHERE " + DataBaseHelper.EMAIL + "=? AND " + DataBaseHelper.PASSWORD + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery(query, new String[]{Email, Password});
        User user = new User();
        if (mCursor.moveToFirst()) {
            mCursor.moveToFirst();
            user.setIdNguoiDung(mCursor.getString(0));
            user.setEmail(mCursor.getString(1));
            user.setPassword(mCursor.getString(2));
            user.setIdMoneyType(mCursor.getString(3));
            mCursor.close();
        } else {
            user = null;
        }
        return user;
    }

    public UserFB loginFB (String fbEmail, String fbName) throws SQLException{
        UserFB userFB = new UserFB();
        String query = "Select " + DataBaseHelper.FB_ID + ", " + DataBaseHelper.FB_EMAIL + "," + DataBaseHelper.FB_NAME + " ," + DataBaseHelper.FB_MONEYTYPE + " from " + DataBaseHelper.FACEBOOK_TABLE + " where " + DataBaseHelper.FB_EMAIL + "=? And " + DataBaseHelper.FB_NAME + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{fbEmail, fbName});
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            userFB.setFacebookID(cursor.getString(0));
            userFB.setEmailFB(cursor.getString(1));
            userFB.setNameFB(cursor.getString(2));
            userFB.setIdMoneyTypebyFB(cursor.getString(3));
            cursor.close();
        } else {
            userFB = null;
        }
        return userFB;
    }

    public void updateEntry(String email, String password){
        //define the update row content
        ContentValues updateValues = new ContentValues();
        //Assign values for each row
        updateValues.put(DataBaseHelper.PASSWORD, password);

        String where = DataBaseHelper.EMAIL + " = ?";
        db.update(DataBaseHelper.NGUOIDUNG_TABLE, updateValues, where, new String[]{email});
    }

    public void inserMoney(String id){
        db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();

        newValues.put(DataBaseHelper.MONEYTYPE, User.getIdMoneyType());

        String where = DataBaseHelper.USER_ID + " = ? ";
        db.update(DataBaseHelper.NGUOIDUNG_TABLE, newValues, where, new String[]{id});
    }

    public void inserMoneyFB(String id){
        db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();

        newValues.put(DataBaseHelper.FB_MONEYTYPE, UserFB.getIdMoneyTypebyFB());

        String where = DataBaseHelper.FB_ID + " = ? ";
        db.update(DataBaseHelper.FACEBOOK_TABLE, newValues, where, new String[]{id});
    }

    /* Deal helper */

    public void insertDeal(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.DEAL_GROUP, Deal.getDealGroup());
        contentValues.put(DataBaseHelper.DEAL_GROUP_DETAILS, Deal.getDealGroupDetailsPos());
        contentValues.put(DataBaseHelper.DEAL_GROUP_ICON, Deal.getDealGroupIcon());
        contentValues.put(DataBaseHelper.DEAL_MONEY, Deal.getDealMoney());
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
        contentValues.put(DataBaseHelper.DEAL_DETAIL, Deal.getDealDetail());
        contentValues.put(DataBaseHelper.DEAL_DATE, Deal.getDealDate());
        contentValues.put(DataBaseHelper.DEAL_FB_ID, Deal.getUserFB().getFacebookID());

        db.insert(DataBaseHelper.DEAL_TABLE, null, contentValues);
    }

    public void updateDeal(String id){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.DEAL_GROUP, Deal.getDealGroup());
        values.put(DataBaseHelper.DEAL_GROUP_DETAILS, Deal.getDealGroupDetailsPos());
        values.put(DataBaseHelper.DEAL_MONEY, Deal.getDealMoney());
        values.put(DataBaseHelper.DEAL_DETAIL, Deal.getDealDetail());
        values.put(DataBaseHelper.DEAL_DATE, (Deal.getDealDate()));
        values.put(DataBaseHelper.DEAL_GROUP_ICON, Deal.getDealGroupIcon());

        String where = DataBaseHelper.DEAL_ID + " = ? ";
        db.update(DataBaseHelper.DEAL_TABLE, values, where, new String[]{id});
    }

    public void deleteDeal(String id){
        String where = DataBaseHelper.DEAL_ID + " = ? ";
        db.delete(DataBaseHelper.DEAL_TABLE, where, new String[]{id});

    }

    private Cursor getDealbyID(String dID) {
        db = this.getWritableDatabase();
        String from[] = { DEAL_ID, DEAL_MONEY, DEAL_DATE, DEAL_DETAIL, DEAL_GROUP, DEAL_GROUP_DETAILS, DEAL_GROUP_ICON };
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
                String dealDate = c.getString(c.getColumnIndex(DEAL_DATE));
                String dealDetail = c.getString(c.getColumnIndex(DEAL_DETAIL));
                Integer dealGroup = c.getInt(c.getColumnIndex(DEAL_GROUP));
                Integer dealGroupDetails = c.getInt(c.getColumnIndex(DEAL_GROUP_DETAILS));
                Integer dealGroupIcon = c.getInt(c.getColumnIndex(DEAL_GROUP_ICON));

                MyDeal.listDealiD.add(dealID);
                MyDeal.listDealMoney.add(dealMoney);
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
        String from[] = { DEAL_ID, DEAL_MONEY, DEAL_DATE, DEAL_DETAIL, DEAL_GROUP, DEAL_GROUP_DETAILS, DEAL_GROUP_ICON };
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
                String dealDate = c.getString(c.getColumnIndex(DEAL_DATE));
                String dealDetail = c.getString(c.getColumnIndex(DEAL_DETAIL));
                Integer dealGroup = c.getInt(c.getColumnIndex(DEAL_GROUP));
                Integer dealGroupDetails = c.getInt(c.getColumnIndex(DEAL_GROUP_DETAILS));
                Integer dealGroupIcon = c.getInt(c.getColumnIndex(DEAL_GROUP_ICON));

                MyDeal.listDealiD.add(dealID);
                MyDeal.listDealMoney.add(dealMoney);
                MyDeal.listDealDate.add(dealDate);
                MyDeal.listDealDetails.add(dealDetail);
                MyDeal.listDealGroup.add(dealGroup);
                MyDeal.listDealGroupDetailsPos.add(dealGroupDetails);
                MyDeal.listDealGroupIcon.add(dealGroupIcon);
            }
        }
    }

    public void getAllIncome (String dID, String gID) {
        Cursor c = getAllIncomeData(dID, gID);

        if (c != null){
            while (c.moveToNext()){
                String dealMoney = c.getString(c.getColumnIndex(DEAL_MONEY));

                MyDeal.listAllIncome.add(dealMoney);
            }
        }
    }

    public Cursor getAllIncomeData (String dID, String gID) {
        db = this.getWritableDatabase();
        String query = "Select " + DataBaseHelper.DEAL_MONEY + " from " + DataBaseHelper.DEAL_TABLE + " where " + DataBaseHelper.DEAL_USER_ID + "=? And " + DataBaseHelper.DEAL_GROUP + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{dID, gID});
        return cursor;
    }

    public void getAllIncomebyFB (String dID, String gID) {
        Cursor c = getAllIncomeDatabyFB(dID, gID);

        if (c != null){
            while (c.moveToNext()){
                String dealMoney = c.getString(c.getColumnIndex(DEAL_MONEY));

                MyDeal.listAllIncome.add(dealMoney);
            }
        }
    }

    public Cursor getAllIncomeDatabyFB (String dID, String gID) {
        db = this.getWritableDatabase();
        String query = "Select " + DataBaseHelper.DEAL_MONEY + " from " + DataBaseHelper.DEAL_TABLE + " where " + DataBaseHelper.DEAL_FB_ID + "=? And " + DataBaseHelper.DEAL_GROUP + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{dID, gID});
        return cursor;
    }

    public void getAllOutcomebyFB (String dID, String gID) {
        Cursor c = getAllOutcomeDatabyFB(dID, gID);

        if (c != null){
            while (c.moveToNext()){
                String dealMoney = c.getString(c.getColumnIndex(DEAL_MONEY));

                MyDeal.listAllOutcome.add(dealMoney);
            }
        }
    }

    public Cursor getAllOutcomeDatabyFB (String dID, String gID) {
        db = this.getWritableDatabase();
        String query = "Select " + DataBaseHelper.DEAL_MONEY + " from " + DataBaseHelper.DEAL_TABLE + " where " + DataBaseHelper.DEAL_FB_ID + "=? And " + DataBaseHelper.DEAL_GROUP + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{dID, gID});
        return cursor;
    }

    public void getAllOutcome (String dID, String gID) {
        Cursor c = getAllOutcomeData(dID, gID);

        if (c != null){
            while (c.moveToNext()){
                String dealMoney = c.getString(c.getColumnIndex(DEAL_MONEY));

                MyDeal.listAllOutcome.add(dealMoney);
            }
        }
    }

    public Cursor getAllOutcomeData (String dID, String gID) {
        db = this.getWritableDatabase();
        String query = "Select " + DataBaseHelper.DEAL_MONEY + " from " + DataBaseHelper.DEAL_TABLE + " where " + DataBaseHelper.DEAL_USER_ID + "=? And " + DataBaseHelper.DEAL_GROUP + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{dID, gID});
        return cursor;
    }


    /* Plan helper */

    public void insertPlan(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.PLAN_GROUP, Plan.getPlanGroup());
        contentValues.put(DataBaseHelper.PLAN_GROUP_DETAILS, Plan.getPlanGroupDetailsPos());
        contentValues.put(DataBaseHelper.PLAN_GROUP_ICON, Plan.getPlanGroupIcon());
        contentValues.put(DataBaseHelper.PLAN_MONEY, Plan.getPlanMoney());
        contentValues.put(DataBaseHelper.PLAN_DETAIL, Plan.getPlanDetail());
        contentValues.put(DataBaseHelper.PLAN_DATE, Plan.getPlanDate());
        contentValues.put(DataBaseHelper.PLAN_USER_ID, Plan.getUser().getIdNguoiDung());

        db.insert(DataBaseHelper.PLAN_TABLE, null, contentValues);
    }

    public void insertPlanbyFB(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.PLAN_GROUP, Plan.getPlanGroup());
        contentValues.put(DataBaseHelper.PLAN_GROUP_DETAILS, Plan.getPlanGroupDetailsPos());
        contentValues.put(DataBaseHelper.PLAN_GROUP_ICON, Plan.getPlanGroupIcon());
        contentValues.put(DataBaseHelper.PLAN_MONEY, Plan.getPlanMoney());
        contentValues.put(DataBaseHelper.PLAN_DETAIL, Plan.getPlanDetail());
        contentValues.put(DataBaseHelper.PLAN_DATE, Plan.getPlanDate());
        contentValues.put(DataBaseHelper.PLAN_FB_ID, Plan.getUserFB().getFacebookID());

        db.insert(DataBaseHelper.PLAN_TABLE, null, contentValues);
    }

    public void updatePlan(String id){
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.PLAN_GROUP, Plan.getPlanGroup());
        values.put(DataBaseHelper.PLAN_GROUP_DETAILS, Plan.getPlanGroupDetailsPos());
        values.put(DataBaseHelper.PLAN_MONEY, Plan.getPlanMoney());
        values.put(DataBaseHelper.PLAN_DETAIL, Plan.getPlanDetail());
        values.put(DataBaseHelper.PLAN_DATE, Plan.getPlanDate());
        values.put(DataBaseHelper.PLAN_GROUP_ICON, Plan.getPlanGroupIcon());

        String where = DataBaseHelper.PLAN_ID + " = ? ";
        db.update(DataBaseHelper.PLAN_TABLE, values, where, new String[]{id});
    }

    public void deletePlan(String id){
        String where = DataBaseHelper.PLAN_ID + " = ? ";
        db.delete(DataBaseHelper.PLAN_TABLE, where, new String[]{id});

    }

    private Cursor getPlanbyID(String pID) {
        db = this.getWritableDatabase();
        String from[] = { PLAN_ID, PLAN_MONEY, PLAN_DATE, PLAN_DETAIL, PLAN_GROUP, PLAN_GROUP_DETAILS, PLAN_GROUP_ICON };
        String where = PLAN_USER_ID + "=?";
        String[] whereArgs = new String[]{pID+""};
        Cursor cursor = db.query(PLAN_TABLE, from, where, whereArgs, null, null, null, null);
        return cursor;
    }

    public void getPlan (String pID) {
        Cursor c = getPlanbyID(pID);

        if(c != null)
        {
            while(c.moveToNext()){
                String planID = c.getString(c.getColumnIndex(PLAN_ID));
                String planMoney = c.getString(c.getColumnIndex(PLAN_MONEY));
                String planDate = c.getString(c.getColumnIndex(PLAN_DATE));
                String planDetail = c.getString(c.getColumnIndex(PLAN_DETAIL));
                Integer planGroup = c.getInt(c.getColumnIndex(PLAN_GROUP));
                Integer planGroupDetails = c.getInt(c.getColumnIndex(PLAN_GROUP_DETAILS));
                Integer planGroupIcon = c.getInt(c.getColumnIndex(PLAN_GROUP_ICON));

                MyPlan.listPlaniD.add(planID);
                MyPlan.listPlanMoney.add(planMoney);
                MyPlan.listPlanDate.add(planDate);
                MyPlan.listPlanDetails.add(planDetail);
                MyPlan.listPlanGroup.add(planGroup);
                MyPlan.listPlanGroupDetailsPos.add(planGroupDetails);
                MyPlan.listPlanGroupIcon.add(planGroupIcon);
            }
        }
    }

    private Cursor getPlanbyFbID(String pID) {
        db = this.getWritableDatabase();
        String from[] = { PLAN_ID, PLAN_MONEY, PLAN_DATE, PLAN_DETAIL, PLAN_GROUP, PLAN_GROUP_DETAILS, PLAN_GROUP_ICON };
        String where = PLAN_FB_ID + "=?";
        String[] whereArgs = new String[]{pID+""};
        Cursor cursor = db.query(PLAN_TABLE, from, where, whereArgs, null, null, null, null);
        return cursor;
    }

    public void getPlanbyFB (String pID) {
        Cursor c = getPlanbyFbID(pID);

        if(c != null)
        {
            while(c.moveToNext()){
                String planID = c.getString(c.getColumnIndex(PLAN_ID));
                String planMoney = c.getString(c.getColumnIndex(PLAN_MONEY));
                String planDate = c.getString(c.getColumnIndex(PLAN_DATE));
                String planDetail = c.getString(c.getColumnIndex(PLAN_DETAIL));
                Integer planGroup = c.getInt(c.getColumnIndex(PLAN_GROUP));
                Integer planGroupDetails = c.getInt(c.getColumnIndex(PLAN_GROUP_DETAILS));
                Integer planGroupIcon = c.getInt(c.getColumnIndex(PLAN_GROUP_ICON));

                MyPlan.listPlaniD.add(planID);
                MyPlan.listPlanMoney.add(planMoney);
                MyPlan.listPlanDate.add(planDate);
                MyPlan.listPlanDetails.add(planDetail);
                MyPlan.listPlanGroup.add(planGroup);
                MyPlan.listPlanGroupDetailsPos.add(planGroupDetails);
                MyPlan.listPlanGroupIcon.add(planGroupIcon);
            }
        }
    }

    public void getAllPlanIncome (String pID, String gID) {
        Cursor c = getAllPlanIncomeData(pID, gID);

        if (c != null){
            while (c.moveToNext()){
                String planMoney = c.getString(c.getColumnIndex(PLAN_MONEY));

                MyPlan.listAllIncome.add(planMoney);
            }
        }
    }

    public Cursor getAllPlanIncomeData (String pID, String gID) {
        db = this.getWritableDatabase();
        String query = "Select " + DataBaseHelper.PLAN_MONEY + " from " + DataBaseHelper.PLAN_TABLE + " where " + DataBaseHelper.PLAN_USER_ID + "=? And " + DataBaseHelper.PLAN_GROUP + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{pID, gID});
        return cursor;
    }

    public void getAllPlanIncomebyFB (String pID, String gID) {
        Cursor c = getAllPlanIncomeDatabyFB(pID, gID);

        if (c != null){
            while (c.moveToNext()){
                String planMoney = c.getString(c.getColumnIndex(PLAN_MONEY));

                MyPlan.listAllIncome.add(planMoney);
            }
        }
    }

    public Cursor getAllPlanIncomeDatabyFB (String pID, String gID) {
        db = this.getWritableDatabase();
        String query = "Select " + DataBaseHelper.PLAN_MONEY + " from " + DataBaseHelper.PLAN_TABLE + " where " + DataBaseHelper.PLAN_FB_ID + "=? And " + DataBaseHelper.PLAN_GROUP + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{pID, gID});
        return cursor;
    }

    public void getAllPlanOutcomebyFB (String pID, String gID) {
        Cursor c = getAllPlanOutcomeDatabyFB(pID, gID);

        if (c != null){
            while (c.moveToNext()){
                String planMoney = c.getString(c.getColumnIndex(PLAN_MONEY));

                MyPlan.listAllOutcome.add(planMoney);
            }
        }
    }

    public Cursor getAllPlanOutcomeDatabyFB (String pID, String gID) {
        db = this.getWritableDatabase();
        String query = "Select " + DataBaseHelper.PLAN_MONEY + " from " + DataBaseHelper.PLAN_TABLE + " where " + DataBaseHelper.PLAN_FB_ID + "=? And " + DataBaseHelper.PLAN_GROUP + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{pID, gID});
        return cursor;
    }

    public void getAllPlanOutcome (String pID, String gID) {
        Cursor c = getAllPlanOutcomeData(pID, gID);

        if (c != null){
            while (c.moveToNext()){
                String planMoney = c.getString(c.getColumnIndex(PLAN_MONEY));

                MyPlan.listAllOutcome.add(planMoney);
            }
        }
    }

    public Cursor getAllPlanOutcomeData (String pID, String gID) {
        db = this.getWritableDatabase();
        String query = "Select " + DataBaseHelper.PLAN_MONEY + " from " + DataBaseHelper.PLAN_TABLE + " where " + DataBaseHelper.PLAN_USER_ID + "=? And " + DataBaseHelper.PLAN_GROUP + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{pID, gID});
        return cursor;
    }

}
