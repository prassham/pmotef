package com.ibm.pmo.employee;

import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.lightcouch.CouchDbException;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ibm.pmo.resources.PMOTest;
import com.ibm.pmo.vacation.VacationResource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Path("/employee")
public class EmployeeResource {
	
	public CloudantClient getConnection() throws IOException {
		/*InputStream inputStream = null;
		String url="";
		String userName ="";
		String password ="";
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			// get the property value and print it out
			url = prop.getProperty("url");
			userName = prop.getProperty("userName");
			password = prop.getProperty("password");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}*/
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
		CloudantClient client = new CloudantClient(url,username,password);
		System.out.println("connection done");
		System.out.println(client);
		return client;
	}
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject geEmployee(){
		/*PMOTest validation = new PMOTest();
		Response res = validation.getEmployee();
		System.out.print(res.getStatus());
		JSONObject jsonObj = null;
		if(res.getStatus()!=200){
			throw new RuntimeException("Failed : HTTP error code : "+ res.getStatus());
		}*/
		JSONObject jsonObj = null;
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONObject responseObject = new JSONObject();
		Database db = con.database("employee", false);
		List<Employeegetset> list3=null;
		String json3=null;
		try{
		list3 = db.findByIndex("\"selector\": {\"_id\": {\"$gt\": 0},\"NOTES_ID\": {\"$regex\":\"^\"},\"STATUS\": {\"$eq\":\"Active\"}}", Employeegetset.class);
		}
		 catch (CouchDbException e){
				System.out.println(e);
		}
		if(!list3.isEmpty()){
			Gson gson = new Gson();
			 json3 = gson.toJson(list3);
			 jsonObj = new JSONObject(json3);
			 System.out.println(json3);
			 }
		else{
			System.out.println("User does not exist");
		}
			 return jsonObj;
		}
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	/*@Produces(MediaType.APPLICATION_JSON)*/
	public Response insert(String data) throws JSONException {
		VacationResource vr = new VacationResource();
		JSONObject responseObject = new JSONObject();
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = vr.login(res);
		if(result.contains("User does not exist")){
			responseObject.put("Error : ", "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.");
			return Response.status(403).entity(responseObject.toString()).build();
		}*/
		System.out.println("abcd");
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("employee", false);
		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(data, JsonObject.class);
		//String emp_id = jobj.get("emp_id").toString();
		try{
			/*List<EmployeeBean> list3 = db.findByIndex("\"selector\": {\"emp_id\": {\"$eq\":"+emp_id+"}}", EmployeeBean.class);
			if(list3.isEmpty()){*/
			System.out.println("a");
			db.post(jobj);
			System.out.println("abcdE");
			responseObject.put("message","Employee record submitted successfully");
			return Response.status(200).entity(responseObject.toString()).build();
			/*}
			else{
			responseObject.put("message","Employee record already exist!");
			return Response.status(200).entity(responseObject.toString()).build();
			}*/
		 	}
		catch (CouchDbException e){
			responseObject.put("message",e);
			return Response.status(400).entity(responseObject.toString()).build();
		}
	}
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(String data) throws JSONException {
		VacationResource vr = new VacationResource();
		JSONObject responseObject = new JSONObject();
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = vr.login(res);
		if(result.contains("User does not exist")){
			responseObject.put("Error : ", "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.");
			return Response.status(403).entity(responseObject.toString()).build();
		}*/
		String json3=null;
				CloudantClient con = null;
				try {
					con = getConnection();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				Database db;
				try{
						Gson gson = new Gson();
						JsonObject jobj = gson.fromJson(data, JsonObject.class);
						String status = jobj.get("STATUS").toString();
						System.out.println("Status of"+ status);
						if(status.equals("\"Active\"")){
							db = con.database("employee", false);
							db.update(jobj);
							responseObject.put("message","Employee record updated successfully");
							return Response.status(200).entity(responseObject.toString()).build();
						}
						else{
							db = con.database("employee", false);
							//db.remove(jobj);
							db.update(jobj);
							db = con.database("employee_history", false);
							System.out.println("Connected");
							jobj.remove("_id");
							jobj.remove("_rev");
							db.post(jobj);
							responseObject.put("message","Employee record updated successfully");
							return Response.status(200).entity(responseObject.toString()).build();
						}
									
				}
				catch (CouchDbException e){
					responseObject.put("message",e);
					return Response.status(400).entity(responseObject.toString()).build();
				}
		}
	@GET
	@Path("/delete/{i}/{r}")
	@Produces("text/html")
	public Response delete(@PathParam("i") String id, @PathParam("r") String rev) throws CouchDbException {
		VacationResource vr = new VacationResource();
		JSONObject responseObject = new JSONObject();
		PMOTest validation = new PMOTest();
		/*String res = validation.getEmployee();
		String result = vr.login(res);
		if(result.contains("User does not exist")){
			responseObject.put("Error : ", "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.");
			return Response.status(403).entity(responseObject.toString()).build();
		}*/
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("employee", false);
		try{
			System.out.println("abcf");
			db.remove(id, rev);
			System.out.println("abcdg");
			responseObject.put("message","Employee Record Deleted Successfully!!");
			return Response.status(200).entity(responseObject.toString()).build();
		}
		catch (CouchDbException e){
			responseObject.put("message",e);
			return Response.status(400).entity(responseObject.toString()).build();
		}
	}
	@GET
	@Path("/getEmployee/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmployeeId(@PathParam("i") String empid) throws CouchDbException {
		VacationResource vr = new VacationResource();
		JSONObject responseObject = new JSONObject();
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = vr.login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
		String json3=null;
		
		System.out.println(empid);
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("employee", false);
		List<Employeegetset> list3=null;
		try{
		list3 = db.findByIndex("\"selector\": {\"_id\": {\"$gt\": 0},\"EMP_ID\": {\"$eq\":\""+empid+"\"}}", Employeegetset.class);
		}
		 catch (CouchDbException e){
				System.out.println(e);
		}
		if(!list3.isEmpty()){
			Gson gson = new Gson();
			 json3 = gson.toJson(list3);
			 System.out.println(json3);
			 }
		else{
			System.out.println("User does not exist");
			Gson gson = new Gson();
			json3 = gson.toJson("user does not exist");
		}
		
			 return json3;
		}
	
	@GET
	@Path("/getws_manager/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmployeeName(@PathParam("i") String empname) throws CouchDbException {
		VacationResource vr = new VacationResource();
		JSONObject responseObject = new JSONObject();
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = vr.login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
		String json3=null;
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(empname);
		Database db = con.database("employee", false);
		List<Employeegetset> list3=null;
		try{
		list3 = db.findByIndex("\"selector\": {\"_id\": {\"$gt\": 0},\"WS_MANAGER\": {\"$eq\":\""+empname+"\"}}", Employeegetset.class);
		}
		 catch (CouchDbException e){
				System.out.println(e);
		}
		if(!list3.isEmpty()){
			Gson gson = new Gson();
			 json3 = gson.toJson("exist");
			 System.out.println(json3);
			 }
		else{
			Gson gson = new Gson();
			 json3 = gson.toJson("not exist");
			System.out.println("not exist");
		}
		
			 return json3;
		}
	@GET
	@Path("/history")
	@Produces(MediaType.APPLICATION_JSON)
	public String getHistory(){
		VacationResource vr = new VacationResource();
		JSONObject responseObject = new JSONObject();
		PMOTest validation = new PMOTest();
		/*String res = validation.getEmployee();
		String result = vr.login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
			System.out.println("Inside history");
		String json3=null;
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("employee_history", false);
		List<Employeegetset> history=null;
		try{
		history = db.findByIndex("\"selector\": {\"_id\": {\"$gt\": 0},\"NOTES_ID\": {\"$regex\":\"^\"}}", Employeegetset.class);
		}
		 catch (CouchDbException e){
				System.out.println(e);
		}
		if(!history.isEmpty()){
			Gson gson = new Gson();
			 json3 = gson.toJson(history);
			 System.out.println(json3);
			 }
		else{
			System.out.println("User does not exist");
		}
			 return json3;
		}
	@GET
	@Path("/getws_manager/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getId(@PathParam("i") String email) throws CouchDbException {
		VacationResource vr = new VacationResource();
		JSONObject responseObject = new JSONObject();
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = vr.login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
		String json3=null;
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(email);
		Database db = con.database("employee", false);
		List<Employeegetset> list3=null;
		try{
		list3 = db.findByIndex("\"selector\": {\"_id\": {\"$gt\": 0},\"EMAIL\": {\"$eq\":\""+email+"\"}}", Employeegetset.class);
		}
		 catch (CouchDbException e){
				System.out.println(e);
		}
		if(!list3.isEmpty()){
			Gson gson = new Gson();
			 json3 = gson.toJson(list3);
			 System.out.println("User record"+list3);
			 System.out.println(json3);
			 return json3;
			 }
		else{
			System.out.println("User does not exist");
		}
		
			 return json3;
		}
}
