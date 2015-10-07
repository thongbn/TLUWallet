package com.server.model;

public class Deal {
	private String dealID;
	public String moneyDeal;
	public String dealDetail;
	public String date;
	public String walletID;
	public String groupID;
	public Deal(String dealID, String moneyDeal, String dealDetail, String date, String walletID, String groupID){
		
		this.dealID = dealID;
		this.moneyDeal = moneyDeal;
		this.dealDetail = dealDetail;
		this.date = date;
		this.walletID = walletID;
		this.groupID = groupID;
	}
	
	public String getdealID(){
		return this.dealID;
	}
	
	public void setdealID(String dealID){
		this.dealID = dealID;
	}
	
	public String getmoneyDeal(){
		return this.moneyDeal;
	}
	
	public void setmoneyDeal(String moneyDeal){
		this.moneyDeal = moneyDeal;
	}
	public String getdealDetail(){
		return this.dealDetail;
	}
	
	public void setdealDetail(String dealDetail){
		this.dealDetail = dealDetail;
	}
	public String getdate(){
		return this.date;
	}
	
	public void setdate(String date){
		this.date = date;
	}
	public String getwalletID(){
		return this.walletID;
	}
	
	public void setwalletID(String walletID){
		this.walletID = walletID;
	}
	public String getgroupID(){
		return this.groupID;
	}
	
	public void setgroupID(String groupID){
		this.groupID = groupID;
	}

}
