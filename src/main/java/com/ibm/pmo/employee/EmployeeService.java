package com.ibm.pmo.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Path("/employeeone")
public class EmployeeService {
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
		System.out.println("Employee Service page");
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
	@Path("employeeCount")
	@Produces({"application/json"})
	public String getEmployeeCount() {
		
		int count = 0;
		String result =null;
		CloudantClient con = null;
		try{
			con = getConnection();
			Database db = con.database("employee", false);
			List<Employeegetset> employeecount = db.findByIndex("\"selector\": {\"_id\": {\"$gt\": 0},\"NOTES_ID\": {\"$regex\":\"^\"},\"STATUS\": {\"$eq\":\"Active\"}}", Employeegetset.class);  

			if(!employeecount.isEmpty()){
				
				count = employeecount.size();
				result = Long.toString(count);
				System.out.println("Employee List" + result);
				
			}
			else{
				System.out.println("No records found");
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}

		return result;

	}
	
	@GET
	@Path("employeeDetails/{currentPage}")
	@Produces({"application/json"})
	public String getEmployee(@PathParam("currentPage") int currentPage) {

		int numPerPage = 20;
		String result=null;
		Gson gson = new Gson();
		int limitValue = numPerPage;
		int skipValue = currentPage * limitValue;
		System.out.println("limit value"+ limitValue);
		System.out.println("skip value"+ skipValue);
		CloudantClient con = null;
		List<Employeegetset> list3=null;
		try{
			con = getConnection();
			Database db = con.database("employee", false);
			List<Employeegetset> employeeList = db.findByIndex("\"selector\": {\"_id\": {\"$gt\": 0},\"NOTES_ID\": {\"$regex\":\"^\"},\"STATUS\": {\"$eq\":\"Active\"}},\"skip\":"+skipValue+",\"limit\":"+limitValue+"", Employeegetset.class);  

			if(!employeeList.isEmpty()){
				
				result = gson.toJson(employeeList);
				System.out.println("Employee List" + currentPage + result);
				
			}
			else{
				System.out.println("No records found");
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}

		return result;

	}
}
