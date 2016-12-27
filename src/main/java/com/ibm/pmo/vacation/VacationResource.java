package com.ibm.pmo.vacation;
import com.ibm.pmo.employee.*;
import com.ibm.pmo.resources.PMOTest;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Path("/vacation")
public class VacationResource {
	Gson gson = new Gson();

	public CloudantClient getConnection() throws IOException {
		InputStream inputStream = null;
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
		}
		CloudantClient client = new CloudantClient(url,userName,password);
		System.out.println(client);
		return client;
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String data) throws CouchDbException {
		/*PMOTest validation = new PMOTest();
		Response res = validation.getEmployee();
		System.out.print(res.getStatus());
		if(res.getStatus()!=200){
			throw new RuntimeException("Failed : HTTP error code : "+ res.getStatus());
		}*/
		System.out.println("Inside login" +data);
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("employee", false);
		String json3=null;
		List<Employeegetset> list3=null;;
		/*JsonObject jobj = gson.fromJson(data, JsonObject.class);
		String id = jobj.get("email").toString();*/
		try{
		list3 = db.findByIndex("\"selector\": {\"EMAIL\": {\"$eq\":"+data+"}}", Employeegetset.class);
		}
		catch (CouchDbException e){
			System.out.println(e);
		}
		if(!list3.isEmpty()){
			try{
			 json3 = gson.toJson(list3);
			 System.out.println(json3);
			 }
			 catch (CouchDbException e){
			System.out.println(e);
				}
		}
		else{
			System.out.println("User does not exist");
			json3 = "[{\"Message\" : \"User does not exist\"}]";
		}
			 return json3;
	}
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	/*@Produces(MediaType.APPLICATION_JSON)*/
	public Response insert(String data) throws JSONException {
		JSONObject responseObject = new JSONObject();
		PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = login(res);
		if(result.contains("User does not exist")){
			responseObject.put("Error : ", "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.");
			return Response.status(403).entity(responseObject.toString()).build();
		}
		
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("vacation_request", false);
		
		JsonObject jobj = gson.fromJson(data, JsonObject.class);
		String email = jobj.get("email").toString();
		String startDate = jobj.get("startDate").toString();
		String endDate = jobj.get("endDate").toString();
		System.out.println(email);
		System.out.println(startDate);
		System.out.println(endDate);
		try{
		List<VacationPOJO> list3 = db.findByIndex("\"selector\":{\"$and\":[{\"_id\":{\"$gt\":null}},{\"$or\":[{\"$and\":[{\"startDate\":{\"$lte\":"+startDate+"}},{\"endDate\":{\"$gte\":"+startDate+"}}]},{\"$and\":[{\"startDate\":{\"$lte\":"+endDate+"}},{\"endDate\":{\"$gte\":"+endDate+"}}]}]},{\"email\":{\"$eq\":"+email+"}}]}", VacationPOJO.class);
		if(list3.isEmpty()){
			db.post(jobj);
			responseObject.put("message","Vacation request submitted successfully");
			return Response.status(200).entity(responseObject.toString()).build();
		 	}
		else{
			responseObject.put("message","Vacation Request Already exists!!");
			return Response.status(400).entity(responseObject.toString()).build();
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
		PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		System.out.println(res);
		String result = login(res);
		System.out.println("Checking result : " +result);
		JSONObject responseObject = new JSONObject();
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("vacation_request", false);
		try{
			db.remove(id, rev);
			responseObject.put("message","Vacation Request Deleted Successfully!!");
			return Response.status(200).entity(responseObject.toString()).build();
		}
		catch (CouchDbException e){
			responseObject.put("message",e);
			return Response.status(400).entity(responseObject.toString()).build();
		}
	}
	
	@GET
	@Path("/update/{i}")
	@Produces("text/html")
	public String update(@PathParam("i") String id) throws CouchDbException {
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
		JSONObject responseObject = new JSONObject();
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("vacation_request", false);
		List<VacationPOJO> list3 = new ArrayList<VacationPOJO>();
		VacationPOJO vp = new VacationPOJO();
		String json3=null;
		try{
			vp = db.find(VacationPOJO.class, id);
			list3.add(vp);
			 json3 = gson.toJson(list3);
			 System.out.println(json3);
			return json3;
		}
		catch (CouchDbException e){
			responseObject.put("message",e);
			return responseObject.toString();
		}
	}
	@GET
	@Path("/employeeonleave")
	@Produces(MediaType.APPLICATION_JSON)
	public String employeeonleave() throws CouchDbException {
		PMOTest validation = new PMOTest();
		/*String res = validation.getEmployee();
		String result = login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("vacation_request", false);
		
		String json2=null;
		Date date = Calendar.getInstance().getTime();
		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 String today = formatter.format(date);
		 System.out.println(today);
		 try{
		 List<VacationPOJO> list2 = db.findByIndex("\"selector\": {\"$and\": [{\"startDate\":{\"$lte\": \""+today+"\"}, \"endDate\":{\"$gte\":\""+today+"\"},\"requeststate\":{\"$ne\":\"Rejected\"}}]},\"sort\": [{\"startDate\": \"asc\"}]", VacationPOJO.class);
		 if(!list2.isEmpty())
		 json2 = gson.toJson(list2);
		 System.out.println(json2);
		 }
		 catch (CouchDbException e){
		System.out.println(e);
			}
		 return json2;
	}
	@GET
	@Path("/vacationhistory/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public String vacationhistory(@PathParam("i") String email) throws CouchDbException {
		PMOTest validation = new PMOTest();
		/*String res = validation.getEmployee();
		String result = login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Database db = con.database("vacation_request", false);
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		 System.out.println(year);
		 List<VacationPOJO> list2 = db.findByIndex("\"selector\": {\"$and\": [{\"email\":{\"$eq\": \""+email+"\"}, \"startDate\":{\"$gte\":\"01-01-"+year+"\"}}]},\"sort\": [{\"startDate\": \"asc\"}]", VacationPOJO.class);
		 String json2 = gson.toJson(list2);
		 System.out.println(json2);
		 return json2;
	}
	@GET
	@Path("/pendingapprovals/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public String pendingapprovals(@PathParam("i") String email) throws CouchDbException, IOException {
		PMOTest validation = new PMOTest();
		/*String res = validation.getEmployee();
		String result = login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
		CloudantClient con = getConnection();
		Database db = con.database("vacation_request", false);
		
		System.out.println(email);
		String json2;
		 List<VacationPOJO> list2 = db.findByIndex("\"selector\":{\"approver\":{\"$eq\":\""+email+"\"},\"requeststate\": {\"$eq\":\"Pending\"}}", VacationPOJO.class);
		 if(!list2.isEmpty()){
			 json2 = gson.toJson(list2);
			 System.out.println(json2);
		 }
		 else{
			 json2 = gson.toJson("No Pending Approvals to display");
			 System.out.println(json2);
		 }
		 return json2;
	}
	@GET
	@Path("/vacationapproval/{i}/{j}/{k}/{l}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vacationapproval(@PathParam("i") String email,
			@PathParam("j") String startDate, 
			@PathParam("k") String endDate, 
			@PathParam("l") String status) throws CouchDbException {
		JSONObject responseObject = new JSONObject();
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = login(res);
		if(result.contains("User does not exist")){
			responseObject.put("Error : ", "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.");
			return Response.status(403).entity(responseObject.toString()).build();
		}*/
		System.out.println(status);
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String vstatus = null;
		Database db = con.database("vacation_request", false);
		
		 List<VacationPOJO> list2 = db.findByIndex("\"selector\": {\"email\": {\"$eq\":\""+email+"\"},\"startDate\": { \"$eq\":\""+startDate+"\"},\"endDate\": {\"$eq\":\""+endDate+"\"}},\"fields\": [\"_id\", \"_rev\"]", VacationPOJO.class);
		String json2 = gson.toJson(list2);
		 String id =json2.replaceAll("\"","").replace("[{_id:", "").replaceAll(",_rev:.*}]","");
		 System.out.println(json2);
		 System.out.println(id);
		 VacationPOJO vac = db.find(VacationPOJO.class, id);
		 System.out.println(status);
		 if(status.contains("Approve")){
			 vstatus = "Approved";
			 System.out.println("In approval code");
			 vac.setrequeststate(vstatus);
		 }
		 else if(status.contains("Reject")){
			 vstatus = "Rejected";
			 System.out.println("In reject code");
			 vac.setrequeststate(vstatus);
		 }
		 else{
			 System.out.println("Not approving");
		 }
		 db.update(vac);
		 responseObject.put("message","Vacation "+vstatus+" Successfully!!");
			return Response.status(200).entity(responseObject.toString()).build();
	}
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateVacationStatus(String data) throws JSONException {
		VacationResource vr = new VacationResource();
		JSONObject responseObject = new JSONObject();
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = vr.login(res);
		if(result.contains("User does not exist")){
			responseObject.put("Error : ", "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.");
			return Response.status(403).entity(responseObject.toString()).build();
		}*/
		String json2=null;
		CloudantClient con = null;
		try {
			con = getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Database db = con.database("vacation_request", false);
		try{
			Gson gson = new Gson();
			JsonObject jobj = gson.fromJson(data, JsonObject.class);
			db.update(jobj);
			responseObject.put("message","Pending status updated successfully");
			return Response.status(200).entity(responseObject.toString()).build();
			
		}
		catch (CouchDbException e){
			responseObject.put("message",e);
			return Response.status(400).entity(responseObject.toString()).build();
		}
		}
		@GET
	@Path("/getnotesid/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getnotesid(@PathParam("i") String email) throws CouchDbException, IOException {
			/*PMOTest validation = new PMOTest();
			String res = validation.getEmployee();
			String result = login(res);
			if(result.contains("User does not exist")){
				String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
			   return json3;
			}*/
		CloudantClient con = getConnection();
		Database db = con.database("employee", false);
		System.out.println(email);
		List<Employeegetset> list2 = db.findByIndex("\"selector\": {\"EMAIL\": {\"$eq\":\""+email+"\"} },  \"fields\": [\"NOTES_ID\"]", Employeegetset.class);
		String json2 = gson.toJson(list2);
		System.out.println(json2);
		String NOTES_ID =json2.replaceAll("\"","").replace("[{NOTES_ID:", "").replaceAll("}]","").split("/", 2)[0];
		System.out.println(NOTES_ID);
		List<Employeegetset> list3 = db.findByIndex("\"selector\": {\"WS_MANAGER\": {\"$eq\":\""+NOTES_ID+"\"} },  \"fields\": [\"EMAIL\", \"NAME\", \"UTILIZATION\"]", Employeegetset.class);
		String json3 = gson.toJson(list3);
		 System.out.println(json3);
		/*JsonObject jobj = gson.fromJson(json2, JsonObject.class);
		String NOTES_ID = jobj.get("NOTES_ID").toString();*/
		/* System.out.println(NOTES_ID);*/
		
		 return json3;
	}
	@GET
	@Path("/getLeaverecord/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getLeaverecord(@PathParam("i") String email) throws CouchDbException, IOException {
		/*PMOTest validation = new PMOTest();
		String res = validation.getEmployee();
		String result = login(res);
		if(result.contains("User does not exist")){
			String json3 = "You are not authorized to view this page. If you believe you should be able to view the page, kindly contact system admin to grant access.";
		   return json3;
		}*/
		CloudantClient con = getConnection();
		Database db = con.database("vacation_request", false);
		System.out.println(email);
		List<VacationPOJO> list2 = db.findByIndex("\"selector\":{\"$and\":[{\"email\":{\"$eq\": \""+email+"\"}},{\"requeststate\":{ \"$ne\": \"Rejected\"}}]}", VacationPOJO.class);
		String json2 = gson.toJson(list2);
		System.out.println(json2);
		 return json2;
	}
}