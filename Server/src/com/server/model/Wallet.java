package com.server.model;

public class Wallet {
	private String walletID;
	public String walletName;
	private String Money;
	private String userID;
	private String moneyID;
	public <List> Wallet(String walletID, String walletName, String Money, String userID, String moneyID){
		
		this.walletID = walletID;
		this.walletName = walletName;
		this.Money = Money;
		this.userID = userID;
		this.moneyID = moneyID;
	}
	
	public String getwalletID(){
		return this.walletID;
	}
	
	public void setwalletID(String walletID){
		this.walletID = walletID;
	}
	
	public String getwalletName(){
		return this.walletName;
	}
	
	public void setwalletName(String walletName){
		this.walletName = walletName;
	}
	public String getMoney(){
		return this.Money;
	}
	
	public void setMoney(String Money){
		this.Money = Money;
	}
	public String getuserID(){
		return this.userID;
	}
	
	public void setuserID(String userID){
		this.userID = userID;
	}
	public String getmoneyID(){
		return this.moneyID;
	}
	
	public void setmoneyID(String moneyID){
		this.moneyID = moneyID;
	}
}
