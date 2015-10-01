package com.server.model;

public class TypeMoney {
	private String typeID;
	public String typeName;
	public TypeMoney(String typeID, String typeName){
		
		this.typeID = typeID;
		this.typeName = typeName;
	}
	
	public String gettypeID(){
		return this.typeID;
	}
	
	public void settypeID(String typeID){
		this.typeID = typeID;
	}
	
	public String gettypeName(){
		return this.typeName;
	}
	
	public void settypeName(String typeName){
		this.typeName = typeName;
	}
}
