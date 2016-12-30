package com.ibm.pmo.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ibm.pmo.employee.*;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.ibm.pmo.employee.Employeegetset;
import com.ibm.pmo.utils.CloudantUtilization;
import com.ibm.pmo.utils.CloudantUtilization.Row.Key;
import com.ibm.pmo.utils.DBConnection;
import com.ibm.pmo.utils.EmployeePojo;
import com.ibm.pmo.employee.CloudantEmployee;

import com.google.gson.JsonObject;


import com.ibm.pmo.utils.Workstream;
import com.ibm.pmo.utils.Workstream.Row;

public class PMOUtilizationHelperCloudant {

	private static String defaultUtilization = "{\"NOV\":0.0,\"OCT\":0.0,\"MAY\":0.0,\"APR\":0.0,\"DEC\":0.0,\"JAN\":0.0,\"JUL\":0.0,\"JUN\":0.0,\"SEP\":0.0,\"AUG\":0.0,\"FEB\":0.0,\"MAR\":0.0}";
	String qtd="{\"q1\":0.0,\"q2\":0.0,\"q3\":0.0,\"q4\":0.0,\"ytd\":0.0,\"count\":0.0}"; //adding team level data
	HashMap<String, JSONObject> empdetails = new HashMap<>();
	
	/*public static void main(String args[]) throws JSONException, IOException{
		PMOUtilizationHelperCloudant obj = new PMOUtilizationHelperCloudant();
	//	obj.display(obj.getCloudantUtilization());
	//	obj.display(obj.getCloudantEmployee());
		obj.getUtilization();
		
	}*/
	/*public static CloudantClient getConnection() {
		String url = "https://e73401b4-0b36-4cf0-9c97-966350085029-bluemix.cloudant.com/employee/_all_docs";
		String userName = "e73401b4-0b36-4cf0-9c97-966350085029-bluemix";
		String password = "Bluemix4me";
		CloudantClient client = new CloudantClient(url,userName,password);
		System.out.println(client);
		return client;
	}*/

	public CloudantUtilization getCloudantUtilization() throws JSONException, IOException{
		JsonObject credentials = CloudantEmployee.getConnectionObject();
		String username = credentials.get("username").toString();
		System.out.println(username);
        String password = credentials.get("password").toString();
        System.out.println(password);
	    String url = credentials.get("url").toString();
	    System.out.println(url);
	    username = username.replaceAll("^\"|\"$", "");
	    password = password.replaceAll("^\"|\"$", "");
	    url = url.replaceAll("^\"|\"$", "");
	    System.out.println("connection establishment");
		String uri = url +"/utilization/_design/util/_view/util?reduce=true&group=true";
		URL urlresource = new URL(uri);
		String loginPassword = username+ ":" + password;
		@SuppressWarnings("restriction")
		String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes());
		URLConnection conn = urlresource.openConnection();
		conn.setRequestProperty ("Authorization", "Basic " + encoded);
	    InputStream input = conn.getInputStream();
	    Reader reader = new InputStreamReader(input,"UTF-8");
	    CloudantUtilization cloudantUtilization = new Gson().fromJson(reader, CloudantUtilization.class);
	    System.out.println("CloudantUtilization object content size : "+cloudantUtilization.rows.length);
		
		return cloudantUtilization; 
	} 
	
	public Workstream getCloudantWorkstream() throws JSONException, IOException{
		JsonObject credentials = CloudantEmployee.getConnectionObject();
		String username = credentials.get("username").toString();
		System.out.println(username);
        String password = credentials.get("password").toString();
        System.out.println(password);
	    String url = credentials.get("url").toString();
	    System.out.println(url);
	    username = username.replaceAll("^\"|\"$", "");
	    password = password.replaceAll("^\"|\"$", "");
	    url = url.replaceAll("^\"|\"$", "");
	    System.out.println("connection establishment");
		String uri = url +"/employee/_design/workstream/_view/workstream?reduce=true&group=true";
		URL urlresource = new URL(uri);
		String loginPassword = username+ ":" + password;
		@SuppressWarnings("restriction")
		String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes());
		URLConnection conn = urlresource.openConnection();
		conn.setRequestProperty ("Authorization", "Basic " + encoded);
	    InputStream input = conn.getInputStream();
	    Reader reader = new InputStreamReader(input,"UTF-8");
	    Workstream workstream = new Gson().fromJson(reader, Workstream.class);
	    System.out.println("Workstream object content size : "+workstream.rows.length);
	    return workstream;
	} 
	public EmployeePojo getCloudantEmployee() throws JSONException, IOException{
		JsonObject credentials = CloudantEmployee.getConnectionObject();
		String username = credentials.get("username").toString();
		System.out.println(username);
        String password = credentials.get("password").toString();
        System.out.println(password);
	    String url = credentials.get("url").toString();
	    System.out.println(url);
	    username = username.replaceAll("^\"|\"$", "");
	    password = password.replaceAll("^\"|\"$", "");
	    url = url.replaceAll("^\"|\"$", "");
	    System.out.println("connection establishment");
		String uri = url +"/employee/_design/employeeDetails/_view/employeeDetails?reduce=true&group=true";
		URL url = new URL(uri);
		String loginPassword = username+ ":" + password;
		@SuppressWarnings("restriction")
		String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes());
		URLConnection conn = url.openConnection();
		conn.setRequestProperty ("Authorization", "Basic " + encoded);
	    InputStream input = conn.getInputStream();
	    Reader reader = new InputStreamReader(input,"UTF-8");
	    EmployeePojo emppojo = new Gson().fromJson(reader, EmployeePojo.class);	   
	    return emppojo;
	} 
	 
	public Response getUtilization() throws JSONException, IOException {
		/*CloudantClient connection = getConnection();
		Database dbutilization = connection.database("employee", false);*/
		
		PMOUtilizationHelperCloudant pmoutil = new PMOUtilizationHelperCloudant();
		Workstream workstream = pmoutil.getCloudantWorkstream();
		CloudantUtilization cloudantUtilization = pmoutil.getCloudantUtilization();
	//	pmoutil.display(cloudantUtilization);
		EmployeePojo employee = pmoutil.getCloudantEmployee();
	//	pmoutil.display(employee);
		empdetails=pmoutil.insertEmpDetailsMap(employee);// Hash map initialization
		
		
		JSONObject responseObject = new JSONObject();
		JSONObject totalObject = new JSONObject();
		JSONArray utilArray = new JSONArray();
		JSONArray workStreamArray =new JSONArray();		
		JSONObject workStreamObject = new JSONObject();
		
		
		
		
		int flagunknown = 0;
		
		for (com.ibm.pmo.utils.CloudantUtilization.Row element1 : cloudantUtilization.rows) {
			JSONObject utilObj = new JSONObject();
//			select  EMPLOYEE.EMP_ID, EMPLOYEE.NAME, UTILIZATION.UTILIZATION, EMPLOYEE.WORKSTREAM, EMPLOYEE.TYPE from EMPLOYEE inner "
//					+ "join UTI
			utilObj.put("emp_id", element1.getKey().getEmp_id());
//			List<Employeegetset> emp = dbutilization.findByIndex("\"selector\": { \"EMP_ID\" : \""+element1.getKey().getEmp_id()+"\"}",Employeegetset.class);        	
			
			if(empdetails.containsKey(element1.getKey().getEmp_id())){
				
				if(empdetails.get(element1.getKey().getEmp_id()).getString("emptype").equalsIgnoreCase("CT")){
					utilObj.put("utilization", new JSONObject(element1.getKey().getUtilization().getUtilHours()));
					utilObj.put("availHours",new JSONObject(element1.getKey().getUtilization().getUtilHours()));
				}else{
					utilObj.put("utilization", new JSONObject(element1.getKey().getUtilization().getUtilHours()));
					utilObj.put("availHours",new JSONObject(element1.getKey().getAvailHours().getAvailHours()));
				}
				
				utilObj.put("name",empdetails.get(element1.getKey().getEmp_id()).getString("name"));				
				utilObj.put("workstream", empdetails.get(element1.getKey().getEmp_id()).getString("workstream"));
				
			}
			else{
				
				utilObj.put("name", "Unknown"+flagunknown);
				utilObj.put("workstream", "SG2");
				utilObj.put("utilization", new JSONObject(element1.getKey().getUtilization().getUtilHours()));
				utilObj.put("availHours",new JSONObject(element1.getKey().getAvailHours().getAvailHours()));
				flagunknown++;
			}			
			
			utilArray.put(utilObj);
		}//for 
		totalObject.put("utilization", utilArray);
		
		for (Row iterable_element : workstream.rows) {
			workStreamObject.put(iterable_element.getKey(), new JSONObject(qtd));
		}		
		workStreamArray.put(workStreamObject);
		totalObject.put("workstream", workStreamArray);
	//	System.out.println("workstream :"+totalObject.toString());
		System.out.println(totalObject.toString());
		
		return Response.status(200).entity(totalObject.toString()).build();
	}	

	public void display(CloudantUtilization cloudant)throws JSONException{
		
		System.out.println("We are inside diaplay function");
		for (com.ibm.pmo.utils.CloudantUtilization.Row iterable : cloudant.getRows()) {
			System.out.println("emp_id :"+iterable.getKey().getEmp_id());			
			System.out.println("AvailHours :"+iterable.getKey().getAvailHours().getAvailHours());
			System.out.println("Utilization :"+iterable.getKey().getUtilization().getUtilHours());
		}
	}
	
	public void display(EmployeePojo emp)throws JSONException{
		
		System.out.println("We are inside diaplay function");
		for (com.ibm.pmo.utils.EmployeePojo.Rows iterable : emp.rows) {
			System.out.println("emp_id :"+iterable.getKey().getEMP_ID());	
		}
	}
	public HashMap<String, JSONObject> insertEmpDetailsMap(EmployeePojo emp)throws JSONException{
		
		for (com.ibm.pmo.utils.EmployeePojo.Rows iterable : emp.rows) {	
			JSONObject empDetailsjson = new JSONObject();
			empDetailsjson.put("emp_id",iterable.getKey().getEMP_ID());
			empDetailsjson.put("name", iterable.getKey().getNAME());
			empDetailsjson.put("workstream",iterable.getKey().getWORKSTREAM());
			empDetailsjson.put("emptype", iterable.getKey().getTYPE());
	//		System.out.println("json object formed : "+empDetailsjson.toString());
			empdetails.put(iterable.getKey().getEMP_ID(), empDetailsjson);
		}
		return empdetails;
	}
}


