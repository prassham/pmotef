package com.ibm.pmo.startoftheweek;

import com.google.gson.annotations.SerializedName;

public class GetSowDetails {
@SerializedName("_id")
String _id;
@SerializedName("_rev")
String _rev;
@SerializedName("weekendcallouts")
String weekendcallouts;
@SerializedName("sunrisechecks")
String sunrisechecks;
@SerializedName("projectsdeployed")
String projects;
@SerializedName("defectsdeployed")
String defectsdeployed;
@SerializedName("upcomingdeployments")
String upcomingdeployments;
@SerializedName("anythingelsetohighlight")
String anythingelsetohighlight;
@SerializedName("date")
String date;
@SerializedName("team")
String team;

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
public String getweekendcallouts(){
	return weekendcallouts;
}
public void setweekendcallouts(String weekendcallouts){
	this.weekendcallouts= weekendcallouts;
}
public String getsunrisechecks(){
	return sunrisechecks;
}
public void setname(String sunrisechecks){
	this.sunrisechecks = sunrisechecks;
}
public String getprojects(){
	return projects;
}
public void setprojects(String projects){
	this.projects = projects;
}
public String getdefectsdeployed(){
	return defectsdeployed;
}
public void setdefectsdeployed(String defectsdeployed){
	this.defectsdeployed = defectsdeployed;
}
public String getupcomingdeployments(){
	return upcomingdeployments;
}
public void setupcomingdeployments(String upcomingdeployments){
	this.upcomingdeployments = upcomingdeployments;
}
public String getanythingelsetohighlight(){
	return anythingelsetohighlight;
}
public void setanythingelsetohighlight(String anythingelsetohighlight){
	this.anythingelsetohighlight = anythingelsetohighlight;
}
public String getdate(){
	return date;
}
public void setdate(String date){
	this.date = date;
}
public String getteam(){
	return team;
}
public void setteam(String team){
	this.team = team;
}
}
