package com.ibm.pmo.O2Observer;
import java.util.HashMap;
import java.util.Map;

import org.lightcouch.Attachment;

import com.google.gson.annotations.SerializedName;

public class ObserverPojo {
	
	@SerializedName("_id")
	private String _id;
	@SerializedName("_rev")
	private String _rev;
	private String name;
	private String creation_date;
	private Map<String, Attachment> _attachments;

	public Map<String, Attachment> getAttachments() {
		return _attachments;
	}
	public void setAttachments(Map<String, Attachment> _attachments) {
		this._attachments = _attachments;
	}
	public String getId(){
		return _id;
	}
	public void setID(String _id){
		this._id = _id;
	}
	public String getrev(){
		return _rev;
	}
	public void setrev(String _rev){
		this._rev = _rev;
	}
	
	public String getname(){
		return name;
	}
	public void setname(String name){
		this.name = name;
	}
	public String getdate(){
		return creation_date;
	}
	public void setdate(String creation_date){
		this.creation_date = creation_date;
	}
	
		
}

