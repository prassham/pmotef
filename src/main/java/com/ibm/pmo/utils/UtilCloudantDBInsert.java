package com.ibm.pmo.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.lightcouch.CouchDbException;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.ibm.pmo.modal.EmployUtilizationBean;

import com.ibm.pmo.employee.CloudantEmployee;

public class UtilCloudantDBInsert {

	public static CloudantClient getConnection() {
		/*JsonObject credentials = CloudantEmployee.getConnectionObject();
		String username = credentials.get("username").toString();
		System.out.println(username);
        String password = credentials.get("password").toString();
        System.out.println(password);
	    String url = credentials.get("url").toString();
	    System.out.println(url);
	    username = username.replaceAll("^\"|\"$", "");
	    password = password.replaceAll("^\"|\"$", "");
	    url = url.replaceAll("^\"|\"$", "");
	    System.out.println("connection establishment");*/
		String url = "https://88f3cfbf-6d6f-4344-9d7c-72ba212722bb-bluemix:e23fef1522cfdb4df1417089d27c4409eb9d90238d1c373e19ef3aea852de5c3@88f3cfbf-6d6f-4344-9d7c-72ba212722bb-bluemix.cloudant.com";
		String username = "88f3cfbf-6d6f-4344-9d7c-72ba212722bb-bluemix";
		String password = "e23fef1522cfdb4df1417089d27c4409eb9d90238d1c373e19ef3aea852de5c3";
		CloudantClient client = new CloudantClient(url,username,password);
		System.out.println(client);
		return client;
	}
	
	
//	public static void main(String[] args) {
//		UtilCloudantDBInsert obj = new UtilCloudantDBInsert();
//		CloudantClient connection = getConnection();
//		Database dbutilization = connection.database("utilization", false);
//		dbutilization.createIndex("{\"index\": { \"fields\": [\"emp_id\"] }, \"type\": \"json\"}");
//	System.out.println(dbutilization.findByIndex("\"selector\": { \"emp_id\" : \""+"014276"+"\"}",JsonFormatter.class)); 
//		System.out.println(dbutilization.listIndices().toString());
//		System.out.println(dbutilization.findByIndex("\"selector\": {\"emp_id\": {\"$eq\":"+"014276"+"}}", JsonFormatter.class).toString());
//		
//	}
//	
	
	private HashMap<String,Float> mapMerger(HashMap<String, Float> dbMap,HashMap<String, Float> empInnerMap){
		
		if(dbMap.isEmpty()){			
			float tof = 0F;
			dbMap.put("JAN", tof);
			dbMap.put("FEB", tof);
			dbMap.put("MAR", tof);
			dbMap.put("APR", tof);
			dbMap.put("MAY", tof);
			dbMap.put("JUN", tof);
			dbMap.put("JUL", tof);
			dbMap.put("AUG", tof);
			dbMap.put("SEP", tof);
			dbMap.put("OCT", tof);
			dbMap.put("NOV", tof);
			dbMap.put("DEC", tof);
			dbMap.putAll(empInnerMap);
		}
		else{			
			dbMap.putAll(empInnerMap);
		}
	
		return dbMap;	
	}
	
	public  void dbinsert(HashMap<String,EmployUtilizationBean> util){
			
			CloudantClient connection = getConnection();
			Database dbutilization = connection.database("utilization", false);
			   try{
			
			   	Set entryset = util.entrySet();
				Iterator it = entryset.iterator();
				int count=0;
				// iterating over the emputilmap  for each empID			
				while(it.hasNext()){
					count++;
					Map.Entry me = (Map.Entry)it.next();
			//		dbutilization.createIndex("{\"index\": { \"fields\": [\"emp_id\"] }, \"type\": \"json\"}");
					List<JsonFormatter> jsfromcloudant = dbutilization.findByIndex("\"selector\": { \"emp_id\" : \""+me.getKey().toString()+"\"}",JsonFormatter.class);
	            	/*for (JsonFormatter jsonFormatter : jsfromcloudant) {
						System.out.println("_id"+jsonFormatter.get_id());
						System.out.println("_rev"+jsonFormatter.get_rev());
					} */
					
	            	
	            	int row_count = 0;
	            	JsonFormatter jsonformatter = new JsonFormatter();
	            	System.out.println("size of list from cloudantdb : "+jsfromcloudant.size());
	            	for (JsonFormatter jsonFormatter2 : jsfromcloudant) {
					  
	            		++row_count;
	            	    // Get data from the current row from result set 
	            	    // if not null then update data from emputilmap 
	//            	    System.out.println("utilization : "+resultSet.getObject("utilization"));
	            	    
	            	    if(!(jsonFormatter2.equals(null)||jsonFormatter2.equals("")||
	            	    		jsonFormatter2.toString().length()==0)){
	            	    	
	            	    	jsonformatter.set_id(jsonFormatter2.get_id());
	            	    	jsonformatter.set_rev(jsonFormatter2.get_rev());
	            	    	jsonformatter.setEmp_id(jsonFormatter2.getEmp_id());	
		            		Map<String,Float> innermap =  (Map<String, Float>)((EmployUtilizationBean)me.getValue()).getAvail_hours();
		            		jsonformatter.setAvailHours(mapMerger(jsonFormatter2.getAvailHours(),(HashMap<String, Float>) innermap));
		            		innermap =  (Map<String, Float>)((EmployUtilizationBean)me.getValue()).getYear_map();
		            		jsonformatter.setUtilization(mapMerger(jsonFormatter2.getUtilization(),(HashMap<String, Float>) innermap));		            		
		            				         		
		            		System.out.println("utilization 1: "+jsonformatter.getUtilization()+"available hours :"+jsonformatter.getAvailHours());
		            		
	            	    }
	            	    // Get data from the current row from result set since its null
	            	    //   then update data from emputilmap + month_map
	            	    else 
//	            	    	if((resultSet.getObject("utilization").equals(null)||resultSet.getObject("utilization").equals("")||
//	            	    		resultSet.getObject("utilization").toString().length()==0))
	            	    	{
	            	    	jsonformatter.setEmp_id((String)(me.getKey()));
	            	    	
	            	    	Map<String,Float> innermap =  (Map<String, Float>)((EmployUtilizationBean)me.getValue()).getAvail_hours();
		            		jsonformatter.setAvailHours(mapMerger(new HashMap<String,Float>(),(HashMap<String, Float>) innermap));
		            		innermap =  (Map<String, Float>)((EmployUtilizationBean)me.getValue()).getYear_map();
		            		jsonformatter.setUtilization(mapMerger(new HashMap<String,Float>(),(HashMap<String, Float>) innermap));	
		            		System.out.println("utilization 2: "+jsonformatter.getUtilization()+"available hours :"+jsonformatter.getAvailHours());
		            					         			                	
	            	    }
	            	    }// for each ends
	            	
	            	
	            	// Running the update query into the cloudantDB
	            	if(row_count==0){
	            		// this employ id is not in employee table still inserting ..
	            	System.out.println(me.getKey().toString()+" This Employee id is not in Employee table");
	            	jsonformatter.setEmp_id((String)(me.getKey()));
        	    	Map<String,Float> innermap =  (Map<String, Float>)((EmployUtilizationBean)me.getValue()).getAvail_hours();
            		jsonformatter.setAvailHours(mapMerger(new HashMap<String,Float>(),(HashMap<String, Float>) innermap));
            		innermap =  (Map<String, Float>)((EmployUtilizationBean)me.getValue()).getYear_map();
            		jsonformatter.setUtilization(mapMerger(new HashMap<String,Float>(),(HashMap<String, Float>) innermap));	
            		
            		Gson gson = new Gson();
            		String ohj= gson.toJson(jsonformatter);           		
            		JsonObject jsonobj = gson.fromJson(ohj, JsonObject.class);
            		dbutilization.post(jsonobj);
				    System.out.println("execute statement : "+ jsonobj.toString());            	
	            	}
	            	else if(row_count==1){	
	          		// json string update query execution into utilization column into employee table 
	            		System.out.println("update to follow id"+jsonformatter.get_id()+""+jsonformatter.get_rev());
	            		/*Gson gson = new Gson();
	            		String ohj= gson.toJson(jsonformatter);           		
	            		JsonObject jsonobj = gson.fromJson(ohj, JsonObject.class);*/
	            		dbutilization.update(jsonformatter);
					  //  System.out.println("execute statement row count 1: "+ jsonobj.toString());
		            		          
	            	}
	            	}
				
				System.out.println("total employee_id read count "+count);         
				
		     
		   }catch(CouchDbException e){
			  e.printStackTrace();			   
		   }
		   System.out.println("Goodbye dbinsert method!");
	  }

}
	class JsonFormatter{
		@SerializedName("_id")
		String _id;
		@SerializedName("_rev")
		String _rev;
		public String get_rev() {
			return _rev;
		}
		public void set_rev(String _rev) {
			this._rev = _rev;
		}
		String emp_id;				
		HashMap<String,Float> availHours;
		HashMap<String,Float> utilization;
		
		public String get_id() {
			return _id;
		}
		public void set_id(String _id) {
			this._id = _id;
		}
		public String getEmp_id() {
			return emp_id;
		}
		public void setEmp_id(String emp_id) {
			this.emp_id = emp_id;
		}
		public HashMap<String, Float> getAvailHours() {
			return availHours;
		}
		public void setAvailHours( HashMap<String, Float> availHours) {
			this.availHours = availHours;
		}
		public HashMap<String, Float> getUtilization() {
			return utilization;
		}
		public void setUtilization(HashMap<String, Float> utilization) {
			this.utilization = utilization;
		}
		}

