package com.server.data;

import java.sql.SQLException;

import com.server.model.User;


public class DataController {


	static UserDataController uDC;
	public DataController(){
		uDC = new UserDataController();
	}
	@SuppressWarnings("static-access")
	public static User getUserId(String id) throws SQLException {
		return uDC.getUserId(id);
	}
	
	@SuppressWarnings("static-access")
	public static void DeleteUser(String id) throws SQLException {	
		try {
			uDC.DeleteUser(id);
			System.out.println("Success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	@SuppressWarnings("static-access")
	public static void InsertUser(User u) throws SQLException {	
		try {
			uDC.InsertUser(u);
			 System.out.println("insert success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
