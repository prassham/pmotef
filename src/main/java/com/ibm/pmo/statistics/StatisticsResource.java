package com.ibm.pmo.statistics;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.ibm.pmo.resources.PMOTest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.ibm.pmo.employee.CloudantEmployee;
import com.google.gson.JsonObject;

@Path("/employeeStatistics")
public class StatisticsResource {

	@GET
	@Path("/{data}")
	@Produces("application/json")
	public InputStream bandmixpercentage(@PathParam("data") String data) throws Exception {
	/*PMOTest validation = new PMOTest();
		Response res = validation.getEmployee();
		System.out.print(res.getStatus());
		if(res.getStatus()!=200){
			throw new RuntimeException("Failed : HTTP error code : "+ res.getStatus());
		}*/
		/*InputStream inputStream = null;
		Properties prop = new Properties();
		String userName ="";
		String password ="";
		try {
			
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			// get the property value and print it out
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
		
		String ins = "{\"Message\" : \"Invalid Request\"}";
		String uri=null;
		switch (data) {
        case "bandmixpercentage": 
        	url = url +"/employee/_design/band/_view/band?reduce=true&group=true";
        	uri = url;
        	break;
        case "offonmix": 
        	url = url +"employee/_design/offonmix/_view/offonmix?reduce=true&group=true";
        	uri = url;
            break;
        case "diversitymix": 
        	url = url +"employee/_design/diversitymix/_view/diversitymix?reduce=true&group=true";
        	uri = url;
        	break;
        case "employeetype":  
        	url = url +"employee/_design/type/_view/type?reduce=true&group=true";
        	uri = url;
        	break;
        case "total":  
        	url = url +"employee/_design/type/_view/type?reduce=true";
        	uri = url;
        	break;
        default: 
        	InputStream stream = new ByteArrayInputStream(ins.getBytes(StandardCharsets.UTF_8));
        	return stream;
		}
        
		URL url = new URL(uri);
		String loginPassword = username+ ":" + password;
		@SuppressWarnings("restriction")
		String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes());
		URLConnection conn = url.openConnection();
		conn.setRequestProperty ("Authorization", "Basic " + encoded);
	    InputStream input = conn.getInputStream();
	    System.out.println(input);
		return input;
	}
}