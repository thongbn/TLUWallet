package com.server.model;

public class User {
	private String userID;
	public String userName;
	private String password;
	
	public User(String userID, String userName, String password){
		
		this.userID = userID;
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getUserID(){
		return this.userID;
	}
	
	public void setUserID(String userID){
		this.userID = userID;
	}
	public String getPassWord(){
		return this.password;
	}
	
	public void setPassWord(String password){
		this.password = password;
	}
}
