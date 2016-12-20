package com.ibm.pmo.utilization;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response; 

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.ibm.pmo.*;
import com.ibm.pmo.modal.EmployUtilizationBean;
import com.ibm.pmo.modal.UtilizationRowBean;
import com.ibm.pmo.utils.*;


/* This class parses the CSV file containing employees utilization ,
 * calculates the utilization hours and
 * inserts into data base in json string format {"NOV":0.0,"OCT":0.0,"MAY":0.0,"APR":0.0,"DEC":0.0,"JAN":495.0,"JUL":0.0,"JUN":0.0,"SEP":0.0,"AUG":0.0,"FEB":0.0,"MAR":0.0} 
 * 
 * A HashMap named year_map present in EmployeeUtilizationBean is used to maintain and calculate utilization on monthly basis.
 * hoursCalculation is the helper method for utilization calculation.
 * empUtilMap{emp_id,EmployeeUtilizationBean Object}
 * EmployeeUtilizationBean{empID, empName, chrgMethodCd,hours,month,weekEndDate,counter,year_map{month,hours}}
 * year_map{month, hours}
 *  
 * 
 * 
 * */

@Path("/PMOFileUpload")

public class ReadUtilization {
    
    @POST
	@Path("/utilizationFileUpload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
		public Response uploadFile(
				@FormDataParam("file") InputStream uploadedInputStream,
				@FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {

			String uploadedFileLocation = fileDetail.getFileName();
			  File file = new File(fileDetail.getFileName());
			  ReadUtilization obj = new ReadUtilization();
		//	  System.out.println(obj.startEndDateInitializer().toString());
			HashMap<String, EmployUtilizationBean> empUtilMap= obj.readFile(uploadedInputStream);
			obj.printMap(empUtilMap);
			
			UtilCloudantDBInsert dbupdate = new UtilCloudantDBInsert();
			dbupdate.dbinsert(empUtilMap);

			// save it
		//	writeToFile(uploadedInputStream, uploadedFileLocation);

			String output = "File uploaded to : " + uploadedFileLocation;

			return Response.status(200).entity(output).build();

		} 
    private final Pattern csvPattern = Pattern.compile("\"([^\"]*)\"|(?<=,|^)([^,]*)(?:,|$)");
	private  HashMap<String,EmployUtilizationBean> empUtilMap = new HashMap<>();
	private  HashMap<String, ArrayList<String>> startEndDate = new HashMap<String, ArrayList<String>>();
	
	public HashMap<String, ArrayList<String>> startEndDateInitializer() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		String sql_select = "Select emp_id,DOJ_O2,END_DATE_GBSTIMESTAMP from employee";
		
		try {
			System.out.println("Stablishing a DB Connection to Employee");
			conn= DBConnection.getConnectionForPMO();
			ps=conn.prepareStatement(sql_select);
			resultSet = ps.executeQuery();
			while(resultSet.next()){
				ArrayList<String> arrlist= new ArrayList<>();
				arrlist.add(resultSet.getString(2));
				arrlist.add(resultSet.getString(3));
				startEndDate.put(resultSet.getString(1),arrlist);
			}
			ps.close();
			resultSet.close();
			conn.close();
			
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("Could Not Read from DB Employee");
		}
		
		return startEndDate; 
		
	}
	
	/*public static void main(String[] args) throws Exception {
		  String csvFile = "/home/amritanshu/pmo_tef/sampleutilization.csv";
		  //sampleutilization.csv , Sample_Telefonica.csv
		  File file = new File(csvFile);
		  ReadUtilization obj = new ReadUtilization();
	//	  System.out.println(obj.startEndDateInitializer().toString());
		HashMap<String, EmployUtilizationBean> empUtilMap= obj.readFile(new FileInputStream(file));
		obj.printMap(empUtilMap);
		UtilCloudantDBInsert dbupdate = new UtilCloudantDBInsert();
		dbupdate.dbinsert(empUtilMap);
	  }*/
	
	public  HashMap<String,EmployUtilizationBean > readFile(InputStream file)throws Exception {	
		BufferedReader br = new BufferedReader(new InputStreamReader(file));		
		String line = br.readLine();
		String cvsSplitBy = ",";
		String[] headerLine = line.split(cvsSplitBy);		
//		System.out.println("empID : "+headerLine[8]+ " empName :"+headerLine[11] + "Chrg Method Cd :"+ headerLine[23]
//				+" hours : "+headerLine[19] +" week end Date :"+headerLine[21]);					
		String [][]columnheader = columnMatcher(headerLine);
		while ((line = br.readLine()) != null) {
			String[] row = parse(line);			
//Reading 	second row  from the CSV File and creating a UtilizationRowBean for each row encountered.	
			UtilizationRowBean utilbean = utilRowBeanReader(row,columnheader);
//			System.out.println(utilbean.getHours() + "  " +utilbean.getMonth() + " "+ utilbean.getWeekEndDate());
			
/* Addition to hashmap empUtilMap based on empid and EmployUtilizationBean
 * check if exists in hashmap or not .
 * non cta and cta should lie B/w starting and end date
 * not cta leads to availhours increment and not util hours
 * cta is added  util hours 
 * noncta and cta both scenario date needs to be maintained as history
 * counter counts cta values only .
 *
 * */
		if(empUtilMap.containsKey(utilbean.getEmpID())){
			EmployUtilizationBean employUtilBean = employUtilizationUpdater(utilbean);
			empUtilMap.put(employUtilBean.getEmpID(), employUtilBean);
	//		System.out.println(employUtilBean.getEmpID());
		}
		else{
			// Need to initialize the things from DB
			
			EmployUtilizationBean employUtilBean = employUtilizationIntilizer(utilbean);
			empUtilMap.put(employUtilBean.getEmpID(), employUtilBean);
	//		System.out.println(employUtilBean.getEmpID());
		}
		}
		System.out.println("file uploading Done");
		return empUtilMap;
	}
	
	private EmployUtilizationBean employUtilizationUpdater(UtilizationRowBean utilbean) throws ParseException {
		EmployUtilizationBean employUtilizationBean = new EmployUtilizationBean();
		employUtilizationBean.setEmpID(utilbean.getEmpID());
		employUtilizationBean.setEmpName(utilbean.getEmpName());
		employUtilizationBean.setStartDate(empUtilMap.get(utilbean.getEmpID()).getStartDate());// DB
		employUtilizationBean.setEndDate(empUtilMap.get(utilbean.getEmpID()).getEndDate());// get from emputilmap
		employUtilizationBean.setAvail_hours(availHoursCalculate(utilbean));
		employUtilizationBean.setWeekEndingDate(empUtilMap.get(utilbean.getEmpID()).getWeekEndingDate());
		employUtilizationBean.setYear_map(utilizationCalculate(utilbean));
	//	System.out.println("2"+employUtilizationBean.getWeekEndingDate().toString()+ "hours count"+ employUtilizationBean.getAvail_hours().toString()
	//			+"weekDate"+utilbean.getWeekEndDate());
	//	System.out.println(utilbean.getMonth());
		return employUtilizationBean;
	}

	private EmployUtilizationBean employUtilizationIntilizer(UtilizationRowBean utilbean) throws ParseException {
		EmployUtilizationBean employUtilizationBean = new EmployUtilizationBean();
		
		employUtilizationBean.setEmpID(utilbean.getEmpID());
		employUtilizationBean.setEmpName(utilbean.getEmpName());
		employUtilizationBean.setStartDate(startEndDateRetriver(1,utilbean));// DB
		employUtilizationBean.setEndDate(startEndDateRetriver(2, utilbean));// DB
		
		employUtilizationBean.setWeekEndingDate(weekEndingDateCalculate(utilbean));
		employUtilizationBean.setAvail_hours(availHoursCalculate(utilbean));
		employUtilizationBean.setYear_map(utilizationCalculate(utilbean));
		
	//	System.out.println("1"+employUtilizationBean.getWeekEndingDate().toString()+"hours count"+ employUtilizationBean.getAvail_hours().toString()
	//			+"weekDate"+utilbean.getWeekEndDate());
		return employUtilizationBean;
	}

	private Date startEndDateRetriver(int i, UtilizationRowBean utilbean) throws ParseException {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_YEAR, 1);
		now.set(Calendar.MILLISECOND, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.HOUR_OF_DAY, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
    	Date startdate = now.getTime();
    //	System.out.println(startdate);
    	now.set(Calendar.MONTH,Calendar.DECEMBER);
    	now.set(Calendar.DAY_OF_MONTH,31);
    	 now.set(Calendar.HOUR_OF_DAY, 23);
    	 now.set(Calendar.MILLISECOND, 0);
         now.set(Calendar.SECOND, 59);
         now.set(Calendar.MINUTE, 59);
    	Date enddate = now.getTime(); 
   // 	System.out.println(enddate);
    	ArrayList<String> arrDate;
    	if(startEndDate.containsKey(utilbean.getEmpID())){
    		arrDate = startEndDate.get(utilbean.getEmpID());
    		startdate = sdf.parse(arrDate.get(0));
    		enddate = sdf.parse(arrDate.get(1));  		
    	}
		if(i==1){
		return startdate;	
		}
		else{
		return enddate;	
		}		
	}

	private ArrayList<Date> weekEndingDateCalculate(UtilizationRowBean utilbean) {
		ArrayList<Date> date = new ArrayList<>();
		Boolean flag = false;
		EmployUtilizationBean empUtilBean;
		if(empUtilMap.containsKey(utilbean.getEmpID())){
			empUtilBean = empUtilMap.get(utilbean.getEmpID()); 
			date = empUtilBean.getWeekEndingDate();
			if(!date.contains(utilbean.getWeekEndDate())){
				date.add(utilbean.getWeekEndDate());
				flag = true;
				empUtilBean.setWeekEndingDate(date);
				empUtilMap.put(utilbean.getEmpID(), empUtilBean);
				
			}else{// week end date is present so don't add
					flag = false;
				}
		}	
		else {
			date.add(utilbean.getWeekEndDate());
			flag = true ; 
		}		
		return date;
	}

	private HashMap<String, Float> utilizationCalculate(UtilizationRowBean utilbean) throws ParseException {
		/* utilization to calculate is based upon
		 * CTA and NON CTA values
		 * Need to check dates starting and ending before addition
		 * */
		HashMap<String, Float> availHours = new HashMap<>();
	//	System.out.println(utilbean.getHours() + "" + utilbean.getMonth() + "" + utilbean.getWeekEndDate());				
		if(empUtilMap.containsKey(utilbean.getEmpID())){
			availHours = empUtilMap.get(utilbean.getEmpID()).getYear_map();
			if(utilbean.getChrgMethodCd().equalsIgnoreCase("CTA")){
				Date start = empUtilMap.get(utilbean.getEmpID()).getStartDate();
				Date end = empUtilMap.get(utilbean.getEmpID()).getEndDate();
				if((utilbean.getWeekEndDate().after(start)||utilbean.getWeekEndDate().equals(start))&&
						(utilbean.getWeekEndDate().before(end)||utilbean.getWeekEndDate().equals(end)) ){
					// available hours addition
	//				System.out.println("Within tester 2");
					availHours = hashUtilizationHoursAddition(availHours,utilbean);
	//				System.out.println("Within tester 2"+availHours.toString());
				}
			
			}//NON CTA so no calculation further 			
		}
		
		else{ // Since not present in empUtilMap
			if(utilbean.getChrgMethodCd().equalsIgnoreCase("CTA")){
				Date start = startEndDateRetriver(1,utilbean);
				Date end = startEndDateRetriver(2,utilbean);
				if((utilbean.getWeekEndDate().after(start)||utilbean.getWeekEndDate().equals(start))&&
						(utilbean.getWeekEndDate().before(end)||utilbean.getWeekEndDate().equals(end)) ){
					// available hours addition
	//				System.out.println("Within tester 1");
					availHours = hashUtilizationHoursAddition(availHours,utilbean);
	//				System.out.println("Within tester 1"+availHours.toString());
				}
			
			}//NON CTA so no calculation further 						
			}
		
		
		return availHours;
	}

	private HashMap<String, Float> hashUtilizationHoursAddition(HashMap<String, Float> availHours,
			UtilizationRowBean utilbean) {
	//	System.out.println(utilbean.getHours() + "  " + utilbean.getMonth() + "  " + utilbean.getWeekEndDate());
		float hours = 0F;
		String month=null;
		if(!availHours.isEmpty()){
			if(availHours.containsKey(utilbean.getMonth())){
				hours=availHours.get(utilbean.getMonth());
			//	System.out.println("previous count "+ hours);
				hours+=utilbean.getHours();
			//	System.out.println("current count "+ hours);
				month = utilbean.getMonth();
				
			}
			else{
			//	System.out.println("for new month not in hashmap ");
				
				hours = utilbean.getHours();
				month = utilbean.getMonth();
				
			}		
		availHours.put(month, hours);
		
		}else{//	System.out.println("hashmap is empty");
			availHours.put(utilbean.getMonth(),utilbean.getHours());
		}
		
	//	System.out.println("Whithin hashUtilizationHoursAddition "+availHours.toString());
		return availHours;
	}

	private HashMap<String, Float> hashMapAddition(HashMap<String, Float> availHours, UtilizationRowBean utilbean) {
		
		float hours = 0F;
		String month = null;
		if(!availHours.isEmpty()){		
			if(availHours.containsKey(utilbean.getMonth())){
				hours=availHours.get(utilbean.getMonth());
			//	System.out.println("previous count "+ hours);
				hours+=utilbean.weekhours();
			//	System.out.println("current count "+ hours);
				
				month = utilbean.getMonth();
			}
			else{
				hours = utilbean.weekhours();
				month = utilbean.getMonth();
			}
		availHours.put(month, hours);
		}else{
			availHours.put(utilbean.getMonth(), utilbean.weekhours());
		}
		return availHours;
	}

	private HashMap<String, Float> availHoursCalculate(UtilizationRowBean utilbean)throws ParseException {
		
		/* availHours to calculate is based upon start date and end date only
		 * Nothing to do with CTA and NON CTA values
		 * Need to check dates in arraylist
		 * */
		HashMap<String, Float> availHours = new HashMap<>();
		ArrayList<Date> dateHistoric = new ArrayList<>();		
		if(empUtilMap.containsKey(utilbean.getEmpID())){
			availHours = empUtilMap.get(utilbean.getEmpID()).getAvail_hours();
			Date start = empUtilMap.get(utilbean.getEmpID()).getStartDate();
			Date end = empUtilMap.get(utilbean.getEmpID()).getEndDate();
			if((utilbean.getWeekEndDate().after(start)||utilbean.getWeekEndDate().equals(start))&&
					(utilbean.getWeekEndDate().before(end)||utilbean.getWeekEndDate().equals(end)) ){
				dateHistoric = empUtilMap.get(utilbean.getEmpID()).getWeekEndingDate();				
				if(!dateHistoric.contains(utilbean.getWeekEndDate())){
					// Needs to add hours
					dateHistoric=weekEndingDateCalculate(utilbean);// date addition has been taken care off
					availHours = hashMapAddition(availHours,utilbean);
	//				empUtilMap.get(utilbean.getEmpID()).setWeekEndingDate(dateHistoric);
	//				System.out.println("Should RUN more than once"+availHours.toString() + " Date : "+ utilbean.getWeekEndDate());
				}// else do nothing already exists there in arraylist
			}
				
			
		}
		else{// Since not present in empUtilMap
			Date start = startEndDateRetriver(1,utilbean);
			Date end = startEndDateRetriver(2,utilbean);
			if((utilbean.getWeekEndDate().after(start)||utilbean.getWeekEndDate().equals(start))&&
					(utilbean.getWeekEndDate().before(end)||utilbean.getWeekEndDate().equals(end)) ){
				// Since dateHistoric is null
	//			dateHistoric = empUtilMap.get(utilbean.getEmpID()).getWeekEndingDate();	
	//			if(dateHistoric.contains(utilbean.getWeekEndDate())){
					// Needs to add hours					
					availHours = hashMapAddition(availHours,utilbean);
	//				empUtilMap.get(utilbean.getEmpID()).setWeekEndingDate(dateHistoric);
	//				System.out.println("Should RUN only once"+availHours.toString() + " Date : "+ utilbean.getWeekEndDate());
				}// else do nothing already is there in arraylist
				}
		
		return availHours;
	}

	private UtilizationRowBean utilRowBeanReader(String[] row,String [][] columnheader) throws ParseException {
		
		UtilizationRowBean utilBean = new UtilizationRowBean();
		if(row[Integer.parseInt(columnheader[0][1])].trim().length()<= 6){
			switch(row[Integer.parseInt(columnheader[0][1])].trim().length()){
			
			case 4 :utilBean.setEmpID("00"+row[Integer.parseInt(columnheader[0][1])].trim());
					break;
			case 5 :utilBean.setEmpID("0"+row[Integer.parseInt(columnheader[0][1])].trim());
					break;
			default :utilBean.setEmpID(row[Integer.parseInt(columnheader[0][1])].trim());
			}
		}		
		utilBean.setEmpName(row[Integer.parseInt(columnheader[1][1])].trim());
		utilBean.setChrgMethodCd(row[Integer.parseInt(columnheader[4][1])].trim());
		utilBean.setHours(row[Integer.parseInt(columnheader[2][1])].trim());
		utilBean.setWeekEndDate(row[Integer.parseInt(columnheader[3][1])].trim());
		System.out.println("utilBean : "+utilBean.getEmpID());
		return utilBean;
	}

	private  String[] parse(String csvLine) {
	       ArrayList<String> allMatches = new ArrayList<String>();    
	       Matcher matcher = null;
	       String match = null;
	       int size;
	       matcher = csvPattern.matcher(csvLine);
	       while (matcher.find()) {
	           match = matcher.group(1);
	           if (match!=null) {
	               allMatches.add(match);
	           }
	           else {
	               allMatches.add(matcher.group(2));
	           }
	       }        size = allMatches.size();        
	       if (size > 0) {
	           return allMatches.toArray(new String[size]);
	       }
	       else {
	           return new String[0];
	       }            
	   }  
	  private static String [][] columnMatcher(String arr[]){
//		System.out.println("empID column 8 : "+ row[8]+ " empName colummn 11 :"+row[11] + "Chrg Method Cd  colummn 23 :"+ row[23]
//		+" hours colummn 19 : "+row[19] +" week end Date colummn 21 :"+row[21]);
		String [][] cloumnnumbers = new String[5][2];
		int count =0;
		cloumnnumbers[0][0]="Emp Ser Num";
		cloumnnumbers[1][0]="Emp Last Name";
		cloumnnumbers[2][0]="Hours";
		cloumnnumbers[3][0]="Week Ending Date";
		cloumnnumbers[4][0]="Chrg Method Cd";
		cloumnnumbers[0][1]="8";
		cloumnnumbers[1][1]="11";
		cloumnnumbers[2][1]="19";
		cloumnnumbers[3][1]="21";
		cloumnnumbers[4][1]="23";
		
		for (String strings : arr) {			
			if((strings.trim()).equalsIgnoreCase(cloumnnumbers[0][0])){
			cloumnnumbers[0][1]=Integer.toString(count);
			}else if((strings.trim()).equalsIgnoreCase(cloumnnumbers[1][0])){
				cloumnnumbers[1][1]=Integer.toString(count);
			}else if((strings.trim()).equalsIgnoreCase(cloumnnumbers[2][0])){
				cloumnnumbers[2][1]=Integer.toString(count);
			}else if((strings.trim()).equalsIgnoreCase(cloumnnumbers[3][0])){
				cloumnnumbers[3][1]=Integer.toString(count);
			}else if((strings.trim()).equalsIgnoreCase(cloumnnumbers[4][0])){
				cloumnnumbers[4][1]=Integer.toString(count);
			}
			count++;
		}
		
		return cloumnnumbers;
	}
	public static void printMap(HashMap<String, EmployUtilizationBean> empUtilMap2) {
		float hours=0F;
		for (Map.Entry<String, EmployUtilizationBean> entry : empUtilMap2.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue().toString());
			EmployUtilizationBean inner =(EmployUtilizationBean)entry.getValue();
			System.out.println("empid : "+inner.getEmpID()+" empname : "+ inner.getEmpName());
			
			for (Map.Entry<String, Float> month : inner.getYear_map().entrySet()){
				System.out.print(month.getKey() + month.getValue()+ "  ");
				hours+=month.getValue();
			}
			System.out.println("total hours "+hours );
			hours=0F;
			
			for (Map.Entry<String, Float> month : inner.getAvail_hours().entrySet()){
				System.out.print(month.getKey() + month.getValue()+ "  ");
				hours+=month.getValue();
			}
			System.out.println("total hours "+hours );
			hours=0F;
			if(entry.getKey().equals("09289A")){
			System.out.println("dates array"+inner.getWeekEndingDate().toString());
		}}
		System.out.println("\n"+empUtilMap2.size());
	    }

}

	
