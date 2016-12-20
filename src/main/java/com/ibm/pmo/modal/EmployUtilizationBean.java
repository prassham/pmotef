package com.ibm.pmo.modal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.ibm.pmo.modal.*;

public class EmployUtilizationBean {
	
	private String empID, empName ;
	private Date startDate, endDate;
	// Need a Default value for Start and End Dates 
	private HashMap<String, Float> year_map = new HashMap<>();
	private HashMap<String, Float> avail_hours = new HashMap<>();
	private ArrayList<Date> weekEndingDate = new ArrayList<Date>();	
	
	public String getEmpID() {
		return empID;
	}
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public HashMap<String, Float> getYear_map() {
		return year_map;
	}
	public void setYear_map(HashMap<String, Float> year_map) {
		this.year_map = year_map;
	}
	public HashMap<String, Float> getAvail_hours() {
		return avail_hours;
	}
	public void setAvail_hours(HashMap<String, Float> avail_hours) {
		this.avail_hours = avail_hours;
	}
	public ArrayList<Date> getWeekEndingDate() {
		return weekEndingDate;
	}
	public void setWeekEndingDate(ArrayList<Date> weekEndingDate) {
		this.weekEndingDate = weekEndingDate;
	}
	

}
