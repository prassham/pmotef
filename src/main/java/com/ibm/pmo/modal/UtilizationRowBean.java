package com.ibm.pmo.modal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.ibm.pmo.modal.PropertiesCache;



public class UtilizationRowBean {
	
	private String empID, empName, chrgMethodCd;
	private float hours;
	private Date weekEndDate;
	private String month;
	private int counter=0;	
	private final float weekhours = Float.parseFloat(PropertiesCache.getInstance().getProperty("weekhours"));
	
	public float weekhours() {
		return weekhours;
	}
	
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
	public String getChrgMethodCd() {
		return chrgMethodCd;
	}
	public void setChrgMethodCd(String chrgMethodCd) {
		this.chrgMethodCd = chrgMethodCd;
	}
	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}
	public void setHours(String hours) {
		this.hours = Float.parseFloat(hours);
	}
	public Date getWeekEndDate() {
		return weekEndDate;
	}
	public void setWeekEndDate(String weekEndDate)throws ParseException {
		
		try {
			SimpleDateFormat cvsfileformat = new SimpleDateFormat("dd/MM/yyyy");
		//	SimpleDateFormat cvsfileformat = new SimpleDateFormat("dd-MMM-yy");
			Date date = cvsfileformat.parse(weekEndDate);		
			SimpleDateFormat propertyfileformat = new SimpleDateFormat("dd/MM/yyyy");		
			String cvsToPro = propertyfileformat.format(date);		
			if(PropertiesCache.getInstance().containsKey(cvsToPro)){
				this.month = PropertiesCache.getInstance().getProperty(cvsToPro);
			}
			else{
				this.month = "EvaluateManually";
			}		
		this.weekEndDate = date;
		
	}catch(Exception ex) {
		System.out.println("culprint value is "+weekEndDate);
		ex.printStackTrace();
	}

	}
	public void setWeekEndDate(Date weekEndDate ){
		try {
		SimpleDateFormat propertyfileformat = new SimpleDateFormat("dd/MM/yyyy");		
		String cvsToPro = propertyfileformat.format(weekEndDate);		
		if(PropertiesCache.getInstance().containsKey(cvsToPro)){
			this.month = PropertiesCache.getInstance().getProperty(cvsToPro);
		}
		else{
			this.month = "EvaluateManually";
		}	
		this.weekEndDate = weekEndDate;
	}catch(Exception ex) {
		System.out.println("culprint value is "+weekEndDate.toString());
		ex.printStackTrace();
	}

	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public String toString() {
		return "["+empID+"]["+empName+"]["+weekEndDate+"]["+chrgMethodCd+"]["+hours+"]";
	}
}
