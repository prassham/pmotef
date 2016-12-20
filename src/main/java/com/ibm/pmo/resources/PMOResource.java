package com.ibm.pmo.resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.pmo.utils.DBConnection;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

import com.ibm.pmo.resources.*;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam; 

@Path("/PMOResource")
public class PMOResource {
	
	
	@GET
	@Path("/login/{i}/{j}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("i") String email,
			@PathParam("j") String password) throws JSONException {
		
		System.out.println("empid ===" + email +"==== stream " + password);
					
		JSONObject responseObject = new JSONObject();
		String role = null;
		Connection conn = null;
		try{

			conn = DBConnection.getConnectionForPMO();

			Statement st = conn.createStatement();
			ResultSet resultSet = st.executeQuery("select role from USER where email ='"+email.trim()+
					"' and password='"+password.trim()+"' and STATE = 'Active'");
			
			if (resultSet.next())
			{
				role = resultSet.getString(1);
				responseObject.put("role",role);
			}
			else
			{
				responseObject.put("message","Authentication Failed!!");
			}
			
			resultSet.close();
			st.close();
		} catch (Exception e)
		{
			System.out.println(e);
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return Response.status(200).entity(responseObject.toString()).build();
	}	
	
	
	@POST
	@Path("/addUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(String data) throws JSONException{
		
		
		System.out.println("=== data =====" + data);
		
		String email = "",password = "",role="";
		
		if (data != null)
		{
			
			try {
				JSONObject obj = new JSONObject(data);
				
				email = obj.getString("email");
				password = obj.getString("password");
				role = obj.getString("role");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		JSONObject responseObject = new JSONObject();
		
		Connection conn = null;
		try{

			conn = DBConnection.getConnectionForPMO();


			String query = "insert into user(EMAIL,PASSWORD,STATE,ROLE) values(?,?,?,?) ";
			
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, email.trim());
			pst.setString(2, password.trim());
			pst.setString(3, "Active");
			pst.setString(4, role.trim());
			
			pst.executeUpdate();
			
			responseObject.put("message","User Created Successfully!!");
			
			pst.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
				}
			}
		}
		
		return Response.status(200).entity(responseObject.toString()).build();
	}
	
	@GET
	@Path("/getUsers/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@PathParam("i") String email)
			throws JSONException {
		
		System.out.println("empid ===" + email);
					
		JSONArray jsonArray = new JSONArray();
		JSONObject responseObject = new JSONObject();
		String role = null;
		Connection conn = null;
		try{

			conn = DBConnection.getConnectionForPMO();

			Statement st = conn.createStatement();
			String query = "select USER_ID,EMAIL,ROLE,STATE from user";
			if (email != null && 
					email.trim().length() > 0 &&
					!email.trim().contains("All") )
			{
				query = query + " where email = '" +email.trim()+ "'";
			}
			
			ResultSet resultSet = st.executeQuery(query);
			
		
			while (resultSet.next()) {
				//int total_rows = resultSet.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
				
				obj.put("USER_ID", resultSet.getInt(1));
				obj.put("EMAIL", resultSet.getString(2));
				obj.put("ROLE",  resultSet.getString(3));
				obj.put("STATE", resultSet.getString(4));
					
				/*for (int i = 0; i < total_rows; i++) {

					obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
							.toLowerCase(), resultSet.getObject(i + 1));

				}*/
				
				jsonArray.put(obj);
			}

			
			resultSet.close();
			st.close();
		} catch (Exception e)
		{
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return Response.status(200).entity(jsonArray.toString()).build();
	}	
	
	@GET
	@Path("/getEmployee/{i}/{j}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployee(@PathParam("i") String empid,
			@PathParam("j") String stream)
			throws JSONException {
		
		System.out.println("empid ===" + empid + "Stream ===" + stream);
					
		JSONArray jsonArray = new JSONArray();
		JSONObject responseObject = new JSONObject();
		String role = null;
		Connection conn = null;
		try{

			conn = DBConnection.getConnectionForPMO();

			Statement st = conn.createStatement();
			String query = "select * from employee";
			if (empid != null && 
					empid.trim().length() > 0 &&
					!empid.trim().contains("All") )
			{
				query = query + " and EMP_ID = '" +empid.trim()+ "'";
				
				if (stream != null && stream.trim().length() > 0
						&& !stream.trim().contains("All"))
				{
					query = query + " and WORKSTREAM='"+stream.trim()+"'";
				}
			} else
			{
				if (stream != null && stream.trim().length() > 0
						&& !stream.trim().contains("All"))
				{
					query = query + " and WORKSTREAM='"+stream.trim()+"'";
				}
			}
			
			System.out.println("Query ====" + query);
			
			ResultSet resultSet = st.executeQuery(query);
			
		
			while (resultSet.next()) {
				int total_rows = resultSet.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
								
				for (int i = 0; i < total_rows; i++) {

					obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
							.toLowerCase(), resultSet.getObject(i + 1));

				}
				
	/*			obj.put("emp_id", resultSet.getString("emp_id"));
				obj.put("name", resultSet.getString("name"));
				obj.put("mobile",  resultSet.getString("mobile"));
				obj.put("type", resultSet.getString("type"));
				obj.put("workstream", resultSet.getString("workstream"));
				obj.put("work_location", resultSet.getString("work_location"));*/
				
				//obj.put("state", true);
				jsonArray.put(obj);
			}

			
			resultSet.close();
			st.close();
		} catch (Exception e)
		{
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return Response.status(200).entity(jsonArray.toString()).build();
	}	
	
	@GET
	@Path("/getEmployeeByID/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeeByID(@PathParam("i") String empid)
			throws JSONException {
		
		System.out.println("empid ===" + empid);
					
		JSONArray jsonArray = new JSONArray();
		JSONObject responseObject = new JSONObject();
		String role = null;
		Connection conn = null;
		try{

			conn = DBConnection.getConnectionForPMO();

			Statement st = conn.createStatement();
			String query = "select * from employee";
			if (empid != null && 
					empid.trim().length() > 0 &&
					!empid.trim().contains("All") )
			{
				query = query + " where EMP_ID = '" +empid.trim()+ "'";
			}
			
			ResultSet resultSet = st.executeQuery(query);
			
		
			while (resultSet.next()) {
				int total_rows = resultSet.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
								
				for (int i = 0; i < total_rows; i++) {

					obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
							.toLowerCase(), resultSet.getObject(i + 1));

				}
				
				jsonArray.put(obj);
			}

			
			resultSet.close();
			st.close();
		} catch (Exception e)
		{
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return Response.status(200).entity(jsonArray.toString()).build();
	}
	
	@GET
	@Path("/getEmployeePhotoByID/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeePhotoByID(@PathParam("i") String empid)
			throws JSONException {
		
		System.out.println("empid ===" + empid);
					
		JSONArray jsonArray = new JSONArray();
		JSONObject responseObject = new JSONObject();
		JSONObject imageObj = new JSONObject();
		String role = null;
		Connection conn = null;
		try{

			conn = DBConnection.getConnectionForPMO();

			Statement st = conn.createStatement();
			String query = "select IMAGE from EMPIMAGE";
			if (empid != null && 
					empid.trim().length() > 0 &&
					!empid.trim().contains("All") )
			{
				query = query + " where EMP_ID = '" +empid.trim()+ "'";
			}
			
			ResultSet resultSet = st.executeQuery(query);
			
		
			while (resultSet.next()) {
				/*int total_rows = resultSet.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
								
				for (int i = 0; i < total_rows; i++) {

					obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
							.toLowerCase(), resultSet.getObject(i + 1));

				}
				
				jsonArray.put(obj);*/
				
				//byte[] imageBytes = resultSet.getBytes("image");
				byte[] imageBytes =  getContentFromBlob(resultSet.getBlob("image"));
				//byte[] imageBytes = extractBytes(empid.trim());
				String imageBase64 = DatatypeConverter.printBase64Binary(imageBytes);
				imageObj.put("image", imageBase64);
			}

			
			resultSet.close();
			st.close();
		} catch (Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return Response.status(200).entity(imageObj.toString()).build();
	}
	
	public byte[] extractBytes (String ImageName) throws IOException {
		
        File file = new File("C:\\pmo-img\\"+ImageName+".jpg");
        
        FileInputStream fis = new FileInputStream(file);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
 
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum); 
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            //Logger.getLogger(ConvertImage.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        }
 
        byte[] bytes = bos.toByteArray();
		
		
		return bytes;
		
	}
	
	public  byte [] getContentFromBlob(Blob blob) throws Exception {
		System.out.println("getContentFromBlob : " +blob);
		InputStream is=null;
		ByteArrayOutputStream out = null;
		byte [] content = null;
		try {
			is = blob.getBinaryStream();
		    out = new ByteArrayOutputStream();
			int c =0;
	        while ( ( c = is.read() )!=-1 ) {
	            out.write( c );	
	        }
	        content=out.toByteArray();
		}catch(Exception ex) {
			//logger.info("ERROR reading blob content "+ex.getMessage());
			ex.getStackTrace();
		}finally {
			try {
				if(out!=null)out.close();
				if(is!=null)is.close();
			}catch(Exception ex){ex.printStackTrace();}
		}
		return content;
	} 
	
	@POST
	@Path("/removeEmployeeByID/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeEmployeeByID(@PathParam("i") String empid)
			throws JSONException {
		
		System.out.println("empid ===" + empid);
					
		JSONArray jsonArray = new JSONArray();
		JSONObject responseObject = new JSONObject();
		String role = null;
		Connection conn = null;
		try{

			conn = DBConnection.getConnectionForPMO();
			
			Statement st = conn.createStatement();
			String query = "delete from employee ";
			if (empid != null && 
					empid.trim().length() > 0 &&
					!empid.trim().contains("All") )
			{
				query = query + " where EMP_ID = '" +empid.trim()+ "'";
			}
			
			st.executeUpdate(query);
			responseObject.put("message","Employee Removed!!");
			
			st.close();
		} catch (Exception e)
		{
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return Response.status(200).entity(jsonArray.toString()).build();
	}
	
	@POST
	@Path("/addEmployee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEmployee(String data) throws JSONException{
		
		
		System.out.println("=== data =====" + data);
		
		JSONObject obj = null;
		
		if (data != null)
		{
			obj = new JSONObject(data);
		}
		
		
		JSONObject responseObject = new JSONObject();
		
		Connection conn = null;
		try{
			int id = new Double(System.currentTimeMillis()).intValue();
			conn = DBConnection.getConnectionForPMO();
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery("select count(*) from EMPLOYEE where emp_id='"+obj.getString("emp_id")+"'");
			if (rs.next())
			{
				int count = rs.getInt(1);
				System.out.println("ID Count ====" + count);
				if (count > 0)
				{
					responseObject.put("message","Employee ID Already Exist!!");
					return Response.status(400).entity(responseObject.toString()).build();	
				}
			}
			
			ResultSet rset = st.executeQuery("select max(SN_ID) from EMPLOYEE");
			if (rset.next())
			{
				id = rset.getInt(1);
			}
			
			String query = "insert into employee (SN_ID,EMP_ID,REVISED_EMP_ID,NAME,NOTES_ID,MOBILE,TYPE,WORK_LOCATION,BUILDING,ON_OFF_SHORE,WORKSTREAM,SKILL_SET,CURRENT_ROLE,WS_MANAGER,PEM,DOJ_IBM,DOJ_O2,TENURE,AGE_TENURE,START_DATE,RANGE_EXP,ELTP,BAND,GENDER,LAPTOP,END_DATE_GBSTIMESTAMP,EXPIRES,OPEN_SEAT_NO,REMARKS,welcome_text,welcome_email) " +
					" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			
			
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			pst.setString(2, obj.getString("emp_id"));
			pst.setString(3, obj.getString("revised_emp_id"));
			pst.setString(4, obj.getString("name"));
			pst.setString(5, obj.getString("notes_id"));
			pst.setString(6, obj.getString("mobile"));
			pst.setString(7, obj.getString("type"));
			pst.setString(8, obj.getString("work_location"));
			pst.setString(9, obj.getString("building"));
			pst.setString(10, obj.getString("on_off_shore"));
			pst.setString(11, obj.getString("workstream"));
			pst.setString(12, obj.getString("skill_set"));
			pst.setString(13, obj.getString("current_role"));
			pst.setString(14, obj.getString("ws_manager"));
			pst.setString(15, obj.getString("pem"));
			pst.setString(16, obj.getString("doj_ibm"));
			pst.setString(17, obj.getString("doj_o2"));
			
			double tenure = getDaysFromToday(obj.getString("doj_o2"));
			double tenure1 = tenure * 12;
			pst.setDouble(18,tenure1);
			pst.setString(19,getAgeOfTenure(tenure1));
			
			pst.setString(20, obj.getString("start_date"));
			
			pst.setString(21, getExpRange(tenure));
			
			pst.setString(22, obj.getString("eltp"));
			pst.setString(23, obj.getString("band"));
			pst.setString(24, obj.getString("gender"));
			pst.setString(25, obj.getString("laptop"));
			pst.setString(26, obj.getString("end_date_gbstimestamp"));
			
			if (obj.getString("end_date_gbstimestamp") != null 
					&& obj.getString("end_date_gbstimestamp").trim().length() > 0) {
			int expire = new Double(getDaysFromToday(obj.getString("end_date_gbstimestamp"))).intValue();
			pst.setInt(27, expire);
			}
			else
			{
				pst.setInt(27, 0);
			}
			pst.setInt(28, obj.getInt("open_seat_no"));
			pst.setString(29, obj.getString("remarks"));
			
			pst.setString(30, obj.getString("welcome_text"));
			pst.setString(31, obj.getString("welcome_email"));
						
			pst.executeUpdate();
			
			String emailFlag = obj.getString("welcome_email");
			System.out.println("=== emailFlag =====" + emailFlag);
			if (emailFlag != null && emailFlag.equalsIgnoreCase("Yes"))
			{
				sendWelcomeEmail(obj);
			}
			
			responseObject.put("message","Employee Created Successfully!!");
			
			pst.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
			//responseObject.put("message",);
			return Response.status(400).entity("Sorry, Please try again!!").build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
				}
			}
		}
		
		return Response.status(200).entity(responseObject.toString()).build();
	}
	
	
	private void sendWelcomeEmail(JSONObject obj) {
		System.out.println("==== Sending Email=====");
		String subject = "Welcome to Telefonica - UK";
		
		try
		{

		    SendGrid sendgrid = new SendGrid("FAKNwwecFi", "AOGo4hivsXoz7289");
	 
			SendGrid.Email email = new SendGrid.Email();
			
			String toAddress = obj.getString("notes_id");
			
			System.out.println("==== Sending Email TO =====" + toAddress);
			 if (toAddress != null && toAddress.trim().length() >0)
			{
				 email.addTo(toAddress.trim());
			}
		    
			//TODO - Configure @ DB later
		    email.setFrom("pmo-admin@in.ibm.com");
		    
		  	//email.setTemplateId("");//TODO
		    
		    String welcomeMailtext = getWelcomeText(obj);
		    if (welcomeMailtext != null && welcomeMailtext.trim().length() > 0)
		    {
		    		email.setText(welcomeMailtext);
		    		email.setHtml(welcomeMailtext);
		    }
			    
			    email.setSubject(subject);
		   
		    
		    try {
		      SendGrid.Response response = sendgrid.send(email);
		     System.out.println(response.getMessage());
		    }
		    catch (SendGridException e) {
		      System.err.println(e);
		    }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	
		
	}


	private String getWelcomeText(JSONObject obj) throws JSONException {
		
		String welcomeText;
		
		String name = obj.getString("name");
		String workStream = obj.getString("workstream");
		String moreText = obj.getString("welcome_text");
		
		welcomeText = "<p> Dear All<br> <br>" +
		"Today, it gives us immense pleasure to inform you that, we have added one more member to our <br>"+
		"Telefonica- UK team.  "+name+" joined the "+workStream+" Team.<br><br>" +
		"<br>We welcome "+name+", and look forward for long-term association with us.</p>";
		
		if (moreText != null && moreText.trim().length() > 0)
		{
			welcomeText =  welcomeText + "<br><p>"+ moreText +"</p>";
		}
		
		return welcomeText;
	}


	private String getExpRange(double tenure) {
		String expRange ="";
		
		if (tenure > 5) { expRange = ">5 years"; }
		if (tenure > 4) { expRange = "4-5 years"; }
		if (tenure > 3) { expRange = "3-4 years"; }
		if (tenure > 2) { expRange = "2-3 years"; }
		
		if (tenure > 1) { expRange = "1-2 years"; }
		else 	{ expRange = "1 year"; 	}
		
		return expRange;
	}


	private String getAgeOfTenure(double tenure) {
		
		String ageOFTenure = "";
						
		if (tenure < 18 ) { ageOFTenure = "< 18 Months"; }
		if (tenure == 18) { ageOFTenure = "18 Months"; }
		if (tenure > 18 && tenure < 24) {ageOFTenure = "18 - 24 Months";}
		if (tenure > 24 && tenure <= 48) {ageOFTenure = "24 - 48 Months";}
		if (tenure > 48) { ageOFTenure = "> 48 Months"; }
		
		return ageOFTenure;
	}


	@POST
	@Path("/updateEmployee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEmployee(String data) throws JSONException{
		
		
		System.out.println("=== data =====" + data);
		
		JSONObject obj = null;
		
		if (data != null)
		{
			obj = new JSONObject(data);
		}
		
		
		JSONObject responseObject = new JSONObject();
		
		Connection conn = null;
		try{
			int id = new Double(System.currentTimeMillis()).intValue();
			conn = DBConnection.getConnectionForPMO();
			Statement st = conn.createStatement();
			
			ResultSet rset = st.executeQuery("select max(SN_ID) from EMPLOYEE");
			if (rset.next())
			{
				id = rset.getInt(1);
			}
			
			String query = "update EMPLOYEE set REVISED_EMP_ID=?,NAME=?,NOTES_ID=?,MOBILE=?,TYPE=?,WORK_LOCATION=?,BUILDING=?,ON_OFF_SHORE=?,WORKSTREAM=?,SKILL_SET=?,CURRENT_ROLE=?,WS_MANAGER=?,PEM=?,DOJ_IBM=?,DOJ_O2=?,TENURE=?,AGE_TENURE=?,START_DATE=?,RANGE_EXP=?,ELTP=?,BAND=?,GENDER=?,LAPTOP=?,END_DATE_GBSTIMESTAMP=?,EXPIRES=?,OPEN_SEAT_NO=?,REMARKS=?,welcome_text=?,welcome_email=? where EMP_ID = ? ";
			
			
			PreparedStatement pst = conn.prepareStatement(query);
			//pst.setInt(1, id);
			//pst.setString(2, obj.getString("EMP_ID"));
			pst.setString(1, obj.getString("revised_emp_id"));
			pst.setString(2, obj.getString("name"));
			pst.setString(3, obj.getString("notes_id"));
			pst.setString(4, obj.getString("mobile"));
			pst.setString(5, obj.getString("type"));
			pst.setString(6, obj.getString("work_location"));
			pst.setString(7, obj.getString("building"));
			pst.setString(8, obj.getString("on_off_shore"));
			pst.setString(9, obj.getString("workstream"));
			pst.setString(10, obj.getString("skill_set"));
			pst.setString(11, obj.getString("current_role"));
			pst.setString(12, obj.getString("ws_manager"));
			pst.setString(13, obj.getString("pem"));
			pst.setString(14, obj.getString("doj_ibm"));
			pst.setString(15, obj.getString("doj_o2"));
			
			double tenure = getDaysFromToday(obj.getString("doj_o2"));
			double tenure1 = tenure * 12;
			pst.setDouble(16,tenure1);
			pst.setString(17,getAgeOfTenure(tenure1));
			
			pst.setString(18, obj.getString("start_date"));
			
			pst.setString(19, getExpRange(tenure));
			pst.setString(20, obj.getString("eltp"));
			pst.setString(21, obj.getString("band"));
			pst.setString(22, obj.getString("gender"));
			pst.setString(23, obj.getString("laptop"));
			pst.setString(24, obj.getString("end_date_gbstimestamp"));
			
			if (obj.getString("end_date_gbstimestamp") != null 
					&& obj.getString("end_date_gbstimestamp").trim().length() > 0) {
			int expire = new Double(getDaysFromToday(obj.getString("end_date_gbstimestamp"))).intValue();
			pst.setInt(25, expire);
			}
			else
			{
				pst.setInt(25, 0);
			}
			
			pst.setInt(26, obj.getInt("open_seat_no"));
			pst.setString(27, obj.getString("remarks"));
			
			pst.setString(28, obj.getString("emp_id"));
			
			pst.setString(29, obj.getString("welcome_text"));
			pst.setString(30, obj.getString("welcome_email"));
			
			pst.executeUpdate();
			
			responseObject.put("message","Employee Updated Successfully!!");
			
			pst.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
				}
			}
		}
		
		return Response.status(200).entity(responseObject.toString()).build();
	}
	
	
	public float getDaysFromToday(String doj) 
	{
		
		float days = 0;
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		try {
			Date dojDate = sdf.parse(doj);
			
			String todayDateStr = sdf.format(new Date(System.currentTimeMillis()));
			Date todayDate = sdf.parse(todayDateStr);
			
			float totalDays = (float)( (todayDate.getTime() - dojDate.getTime()) / (1000 * 60 * 60 * 24));
			
			days = totalDays/365;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return days;
		
	}
	
	@POST
	@Path("/sendMail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMail(String data) throws JSONException {
		
		System.out.println("=== data =====" + data);
		
		JSONObject obj = null;
		
		if (data != null)
		{
			obj = new JSONObject(data);
		}
		
		System.out.println("Mail Type:===" + obj.getString("mailtype"));
		System.out.println("To Address:===" + obj.getString("to"));
		
		String subject = "Welcome to Telefonica - UK";
		
		String mailType = obj.getString("mailtype");
		String toAddress = obj.getString("to");
		String welcomeMailtext = obj.getString("mailtext");
		
		JSONObject responseObject = new JSONObject();
		
		try
		{

			String vcapJSONString = System.getenv("VCAP_SERVICES");
			JSONObject json = new JSONObject(vcapJSONString);
			String serviceName ="sendgrid";	
			String key;
			JSONArray twaServiceArray =null;
			         
			Iterator i =  json.keys();        
			while (i.hasNext() )
			{
			    key = (String )i.next();            
			    if (key.startsWith(serviceName ))
			    {
			        twaServiceArray = (JSONArray)json.get(key);
			         break;
			      }                       
			  }
			
			JSONObject twaService = (JSONObject)twaServiceArray.get(0); 
			JSONObject credentials = (JSONObject)twaService.get("credentials");
			
			String username = (String) credentials.get("username");
			String password = (String) credentials.get("password");
			
			System.out.println("User ===>" + username + " == password == " + password);
			
		    SendGrid sendgrid = new SendGrid(username, password);
	 
			SendGrid.Email email = new SendGrid.Email();
			
			 if (toAddress != null && toAddress.trim().length() >0)
			{
				 email.addTo(toAddress.trim());
			}
		    
			 //TODO - Configure @ DB later
		    email.setFrom("pmo-admin@in.ibm.com");
		   //email.setSendAt(unixTime.intValue()); //TODO Scheduling not working...
		    
		    if (mailType != null && mailType.trim().length() >0 
					&& mailType.trim().contains("Welcome"))
			{
		    	subject = "Welcome to Telefonica - UK";
		    	//email.setTemplateId("");//TODO
		    	if (welcomeMailtext != null && welcomeMailtext.trim().length() > 0)
		    	{
		    		email.setText(welcomeMailtext);
		    		email.setHtml(welcomeMailtext);
		    	}
			    
			    email.setSubject(subject);
			} 
		    
		    if (mailType != null && mailType.trim().length() >0 
					&& mailType.trim().contains("ILC"))
			{
		    	Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat sdf  = new SimpleDateFormat("dd-MMM-yyyy");
				//System.out.println(sdf.format(date));
				
		    	subject = "ILC Reminder for Current Week Ending " +sdf.format(date);
		    	email.setTemplateId("d0962ef7-64e4-4785-a2d8-51cc4beef76f");
		        email.setText(" ");
			    email.setHtml(" ");
			    email.setSubject(subject);
			} 
		    
		   
		    
		    try {
		      SendGrid.Response response = sendgrid.send(email);
		     System.out.println(response.getMessage());
		    }
		    catch (SendGridException e) {
		      System.err.println(e);
		    }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
	
		
		return Response.status(200).entity(responseObject.toString()).build();
	}	
	
	
	@GET
	@Path("/deleteEmp/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("i") String empid) throws JSONException {
		
		System.out.println("empid ===" + empid);
					
		JSONObject responseObject = new JSONObject();
		
		Connection dbConnection = null;
		try{

			dbConnection = DBConnection.getConnectionForPMO();

			String deleteSQL = "update employee set isdeleted = 'Yes' where emp_id = ? ";
			PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, empid);
			// execute delete SQL stetement
			int result = preparedStatement.executeUpdate();
			
			if (result == 1)
			responseObject.put("message","Employee deleted successfully!!");
			
		} catch (Exception e)
		{
			System.out.println(e);
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (dbConnection != null)
			{
				try {
					dbConnection.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return Response.status(200).entity(responseObject.toString()).build();
	}
	
	
	/*@GET
	@Path("/invokeILCService/{i}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response invokeILCService(@PathParam("i") String agent)
			throws JSONException {
		
		System.out.println("Agent ===" + agent);
					
		
		JSONObject responseObject = new JSONObject();
		try{
			
			String vcapJSONString = System.getenv("VCAP_SERVICES");
			//Object jsonObject = JSON.parse(vcapJSONString);
			JSONObject json = new JSONObject(vcapJSONString);
			String serviceName ="WorkloadScheduler";	
			String key;
			JSONArray twaServiceArray =null;
			         
			Iterator i =  json.keys();        
			while (i.hasNext() )
			{
			    key = (String )i.next();            
			    if (key.startsWith(serviceName ))
			    {
			        twaServiceArray = (JSONArray)json.get(key);
			         break;
			      }                       
			  }
			
			JSONObject twaService = (JSONObject)twaServiceArray.get(0); 
			JSONObject credentials = (JSONObject)twaService.get("credentials");
			String url = (String) credentials.get("url");
			System.out.println("URL ====" + url);
			WorkloadService ws = new WorkloadService(url);
			
			// WorkloadService ws = new WorkloadService("https://64d9b312217d4126b6486820a10470b6%40bluemix.net:RH7CYTvu%3Dg%26u9%264v2jZIz2bGto9W0k@sidr37wamxo-114.wa.ibmserviceengage.com/ibm/TWSWebUI/Simple/rest?tenantId=FG&engineName=engine&engineOwner=engine");
			  
			  //ws.disableCertificateValidation();
             ws.setDebugMode(true);
             
			//WorkloadService ws = new WorkloadService("https://64d9b312217d4126b6486820a10470b6@bluemix.net:RH7CYTvu=g&u9&4v2jZIz2bGto9W0k@sidr37wamxo-114.wa.ibmserviceengage.com/ibm/TWSWebUI/Simple/rest?tenantId=FG&engineName=engine&engineOwner=engine");
			
			WAProcess p = new WAProcess("ILC Process","ILC Reminder Notifications");
			
			RESTAction action = new RESTAction("http://pmo-telefonica.mybluemix.net/rest/DBService/sendMail", 
		            "accept", 
		            "application/json", 
		            RestfulStep.POST_METHOD, 
		            ""); 
			RESTAuthenticationData auth = RESTAuthenticationData.fromUserPwd("userName", "password"); 
		       
			RESTInput input = RESTInput.fromText("{mailtype:'ILC',to:'pradeepkara@gmail.com',mailtext:''}");
			//RESTInput input = RESTInput.fromText("{mailtype:'ILC',to:'o2_igsi_team@wwpdl.vnet.ibm.com',mailtext:''}");
					//fromText("Your text body"); 
		       
			int index = url.indexOf("tenantId=") + 9;
			String prefix = url.substring(index, index + 2);
			String agentName = prefix + "_CLOUD";
			
			if (agent != null && agent.trim().length() > 0)
			{
				agentName = agent;
			}
			
			System.out.println("Setting agent to ===" + agentName);
			
			Step s1 = new RestfulStep(agentName, action, auth, input); 
			p.addStep(s1); 
			
			//Trigger my_trigger = TriggerFactory.everyDayAt(18,10);
			//Trigger my_trigger = TriggerFactory.scheduleOn(2015, 11, 29,12 ,17);
			List days = new ArrayList();
			//days.add(TriggerFactory.DayOfWeek.TUESDAY);
			days.add(TriggerFactory.DayOfWeek.WEDNESDAY);
			
			Calendar now = Calendar.getInstance();
			Date from = now.getTime();
			System.out.println("From Date ===" + from);
			now.add(Calendar.MONTH,3);
			//now.add(Calendar.YEAR, 1);
			Date to = now.getTime();
			System.out.println("To Date ===" + to);
			
			Trigger my_trigger = TriggerFactory.repeatWeekly(from, days, 1, to);
			p.addTrigger( my_trigger );
			
			//Creating and enabling the process
			long id = ws.createAndEnableTask(p).getId();
			
			System.out.println("idd====== " + id);
			
				
			
			//ws.runTask(id); //The process is submitted to run
			
			List<TaskHistoryInstance> thiList = ws.getTaskHistory(id);
			for (TaskHistoryInstance taskHistoryInstance : thiList) {
			    System.out.println("Started: " + taskHistoryInstance.getStartDate() +
			                              "\n Completed steps: " + taskHistoryInstance.getCompletedSteps() +
			                              "\n Is completed: "  + (taskHistoryInstance.getStatus() == TaskInstanceStatus.COMPLETED)); 
			    if ((taskHistoryInstance.getStatus() == TaskInstanceStatus.COMPLETED)) {
			        System.out.println("The process completed all the steps in: " + taskHistoryInstance.getElapsedTime()/60000 + " minutes");                                  }
			} 
			
		} catch (Exception e)
		{
			e.printStackTrace();
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			
		}
		
		return Response.status(200).entity(responseObject.toString()).build();
	}*/
	
	@GET
	@Path("/ResourceDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResourceDetails() throws JSONException {
		   return queryTableData("ResourceDetails");
	}
	
	@GET
	@Path("/WeekDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWeekDetails() throws JSONException {
		   return queryTableData("WeekDetails");
	}
	
	@POST
	@Path("/addRapData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRAPDetails(String data) throws JSONException{
		
		System.out.println("=== data =====" + data);
		
		JSONObject obj = null;
		
		if (data != null)
		{
			obj = new JSONObject(data);
		}
		
		
		JSONObject responseObject = new JSONObject();
		
		int rapID = getRapID(obj.getString("team"),obj.getString("name"),obj.getString("cr"));
		if (rapID ==0)
		{
			boolean flag = saveRAPData(obj);
			if (flag)
				responseObject.put("message"," RAP Data Saved!!");
			else
				responseObject.put("message"," Sorry, Unable to save, please try again ....");
		} else
		{
			responseObject.put("message","Sorry, RAP Data Record already Exist for this CR,User and Team!!");
		}
		
		return Response.status(200).entity(responseObject.toString()).build();
		
	}
	
	private boolean saveRAPData(JSONObject obj) {
		boolean flag = true;
		try{
			
		}catch (Exception e)
		{
			flag = false;
		}
		
		return flag;
	}


	public Integer getRapID(String team,String name,String cr)
	{
		int rapID =0;
		Connection conn = null;
		try{
			
			conn = DBConnection.getConnectionForPMO();

			Statement st = conn.createStatement();
			
			ResultSet resultSet = st.executeQuery("select count(*) from rapdetails where " +
			    " team='"+team+"' and name='"+name+"' and cr='"+cr+"'");
			if (resultSet.next())
			{
				rapID = resultSet.getInt(1);
			}
			resultSet.close();
			st.close();
		} catch (Exception e)
		{
			System.out.println("Exception while getting RAP ID..." +
					e.getMessage());
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
				}
			}
		}
		
		return rapID;
	}
	
	/**
	 * @param tableName
	 * @return
	 * @throws JSONException
	 */
	public Response queryTableData(String tableName) throws JSONException 
	{
		JSONArray jsonArray = new JSONArray();
		JSONObject responseObject = new JSONObject();
		Connection conn = null;
		try{

			conn = DBConnection.getConnectionForPMO();

			Statement st = conn.createStatement();
			String query = "select * from " +tableName;
			
			ResultSet resultSet = st.executeQuery(query);
		
			while (resultSet.next()) {
				int total_rows = resultSet.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
								
				for (int i = 0; i < total_rows; i++) {

					obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
							.toLowerCase(), resultSet.getObject(i + 1));

				}
				
				jsonArray.put(obj);
			}

			
			resultSet.close();
			st.close();
		} catch (Exception e)
		{
			responseObject.put("message","Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		}
		finally
		{
			if (conn != null)
			{
				try {
					conn.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." +
							e.getMessage());
				}
			}
		}
		
		return Response.status(200).entity(jsonArray.toString()).build();
	}
    
    @GET
	@Path("/getUtilization")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUtilization() throws JSONException,IOException {
		PMOUtilizationHelperCloudant utilizationObj = new PMOUtilizationHelperCloudant();
		return utilizationObj.getUtilization();
	}
	

    
}


//https://www.ng.bluemix.net/docs/services/SQLDB/index.html......