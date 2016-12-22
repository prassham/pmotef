package com.ibm.pmo.vacation;

import com.google.gson.annotations.SerializedName;

public class VacationPOJO {
@SerializedName("_id")
String _id;
@SerializedName("_rev")
String _rev;
@SerializedName("empid")
String empid;
@SerializedName("name")
String name;
@SerializedName("email")
String email;
@SerializedName("reason")
String reason;
@SerializedName("vacationtype")
String vacationtype;
@SerializedName("startDate")
String startDate;
@SerializedName("endDate")
String endDate;
@SerializedName("no_of_days")
String no_of_days;
@SerializedName("team")
String team;
@SerializedName("approver")
String approver;
@SerializedName("requeststate")
String requeststate;
@SerializedName("requestedDate")
String requestedDate;

public String getId(){
	return _id;
}
public void setID(String _id){
	this._id = _id;
}
public String getRev(){
	return _rev;
}
public void setRev(String _rev){
	this._rev = _rev;
}
public String getempid(){
	return empid;
}
public void setempid(String empid){
	this.empid= empid;
}
public String getname(){
	return name;
}
public void setname(String name){
	this.name = name;
}
public String getemail(){
	return email;
}
public void setemail(String email){
	this.email = email;
}
public String getreason(){
	return reason;
}
public void setreason(String reason){
	this.reason = reason;
}
public String getvacationtype(){
	return vacationtype;
}
public void setvacationtype(String vacationtype){
	this.vacationtype = vacationtype;
}
public String getstartDate(){
	return startDate;
}
public void setstartDate(String startDate){
	this.startDate = startDate;
}
public String getendDate(){
	return endDate;
}
public void setendDate(String endDate){
	this.endDate = endDate;
}
public String getno_of_days(){
	return no_of_days;
}
public void setno_of_days(String no_of_days){
	this.no_of_days = no_of_days;
}
public String getteam(){
	return team;
}
public void setteam(String team){
	this.team = team;
}
public String getapprover(){
	return approver;
}
public void setapprover(String approver){
	this.approver = approver;
}
public String getrequeststate(){
	return requeststate;
}
public void setrequeststate(String requeststate){
	this.requeststate =requeststate;
}
public String getrequestedDate(){
	return requestedDate;
}
public void setrequestedDate(String requestedDate){
	this.requestedDate = requestedDate;
}
}
