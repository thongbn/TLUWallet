package com.server.model;

public class Group {
	private String groupID;
	public String typeGroup;
	public Group(String groupID, String typeGroup){
		
		this.groupID = groupID;
		this.typeGroup = typeGroup;
	}
	
	public String getgroupID(){
		return this.groupID;
	}
	
	public void setgroupID(String groupID){
		this.groupID = groupID;
	}
	
	public String gettypeGroup(){
		return this.typeGroup;
	}
	
	public void settypeGroup(String typeGroup){
		this.typeGroup = typeGroup;
	}
}
