package com.client.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter {
	
	static final String DATABASE_NAME = "quanlychitieu.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	//ToDo: Create public field for each column in your table.
	//SQL Statement to create a new database.
	static final String DATABASE_CREATE = "create table "+"nguoidung"+ 
						"(" +"idNguoiDung"+" integer primary key autoincrement,"+ "userName text, Pass text, Email text);";
	
	//Variable to hold the database instance
	public SQLiteDatabase db;
	//Context of the application using the database
	private final Context context;
	private DataBaseHelper dbHelper;
	
	public LoginDataBaseAdapter(Context _context){
		context = _context;
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public LoginDataBaseAdapter open() throws SQLException{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		db.close();
	}
	
	public SQLiteDatabase getDatabaseInstance(){
		return db;
	}
	
	public void insertEntry(String userName, String password, String email){
		ContentValues newValues = new ContentValues();
		//assign values for each row
		
		newValues.put("userName", userName);
		newValues.put("Pass", password);
		newValues.put("Email",email);
		
		//Insert the row into your table
		db.insert("nguoidung", null, newValues);
		
	}
	
	public int deleteEntry(String userName){
		//String id = String.valueOf(idNguoiDung);
		String where = "userName=?";
		int numberOFEntriesDeleted = db.delete("nguoidung", where, new String[]{userName});
		
		return numberOFEntriesDeleted;
	}
	
	public boolean Login(String username, String password) throws SQLException   
    {  
        Cursor mCursor = db.rawQuery("SELECT * FROM " + "nguoidung" + " WHERE userName=? AND Pass=?", new String[]{username,password});
        if (mCursor != null) {             
            if(mCursor.getCount() > 0)  
            {  
                return true;  
            }  
        }  
     return false;  
    }  
	
	public void updateEntry(String userName, String password, String email){
		//define the update row content
		ContentValues updateValues = new ContentValues();
		//Assign values for each row
		updateValues.put("userName", userName);
		updateValues.put("Pass", password);
		updateValues.put("Email",email);
		
		String where = "userName = ?";
		db.update("nguoidung", updateValues, where, new String[]{userName});
	}
	
}
