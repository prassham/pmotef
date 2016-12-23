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

@Path("/employeeStatistics")
public class StatisticsResource {

	@GET
	@Path("/{data}")
	@Produces("application/json")
	public InputStream bandmixpercentage(@PathParam("data") String data) throws Exception {
	PMOTest validation = new PMOTest();
		Response res = validation.getEmployee();
		System.out.print(res.getStatus());
		if(res.getStatus()!=200){
			throw new RuntimeException("Failed : HTTP error code : "+ res.getStatus());
		}
		InputStream inputStream = null;
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
		}
		String ins = "{\"Message\" : \"Invalid Request\"}";
		String uri=null;
		switch (data) {
        case "bandmixpercentage": 
        	uri = prop.getProperty("band");
        	break;
        case "offonmix":  
        	uri = prop.getProperty("offonmix");
            break;
        case "diversitymix":  
        	uri = prop.getProperty("diversitymix");
        	break;
        case "employeetype":  
        	uri = prop.getProperty("type");
        	break;
        case "total":  
        	uri = prop.getProperty("total");
        	break;
        default: 
        	InputStream stream = new ByteArrayInputStream(ins.getBytes(StandardCharsets.UTF_8));
        	return stream;
		}
        
		URL url = new URL(uri);
		String loginPassword = userName+ ":" + password;
		@SuppressWarnings("restriction")
		String encoded = new sun.misc.BASE64Encoder().encode (loginPassword.getBytes());
		URLConnection conn = url.openConnection();
		conn.setRequestProperty ("Authorization", "Basic " + encoded);
	    InputStream input = conn.getInputStream();
	    System.out.println(input);
		return input;
	}
}