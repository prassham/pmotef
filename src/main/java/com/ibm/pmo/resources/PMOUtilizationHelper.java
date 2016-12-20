package com.ibm.pmo.resources;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ibm.pmo.utils.DBConnection;

public class PMOUtilizationHelper {

	static String defaultUtilization = "{\"NOV\":0.0,\"OCT\":0.0,\"MAY\":0.0,\"APR\":0.0,\"DEC\":0.0,\"JAN\":0.0,\"JUL\":0.0,\"JUN\":0.0,\"SEP\":0.0,\"AUG\":0.0,\"FEB\":0.0,\"MAR\":0.0}";
	String qtd="{\"q1\":0.0,\"q2\":0.0,\"q3\":0.0,\"q4\":0.0,\"ytd\":0.0,\"count\":0.0}"; //adding team level data
		
	public Response getUtilization() throws JSONException {

		JSONObject responseObject = new JSONObject();
		
		JSONObject totalObject = new JSONObject();
		JSONArray utilArray = new JSONArray();
		JSONArray workStreamArray =new JSONArray();
		Connection dbConnection = null;
		try {

			dbConnection = DBConnection.getConnectionForPMO();
			Statement st = dbConnection.createStatement();
            Statement st1 = dbConnection.createStatement();
			String query = "select  EMPLOYEE.EMP_ID, EMPLOYEE.NAME, UTILIZATION.UTILIZATION, EMPLOYEE.WORKSTREAM, EMPLOYEE.TYPE from EMPLOYEE inner "
					+ "join UTILIZATION on EMPLOYEE.EMP_ID = UTILIZATION.EMP_ID";
			String workStreamQuery = "select distinct WORKSTREAM from EMPLOYEE";
			ResultSet resultSet = st.executeQuery(query);
			ResultSet workStreamResultSet = st1.executeQuery(workStreamQuery);
			JSONObject workStreamObject = new JSONObject();
			while (workStreamResultSet.next()) {
				workStreamObject.put(workStreamResultSet.getString(1), new JSONObject(qtd));
            }
			workStreamArray.put(workStreamObject);
			totalObject.put("workstream", workStreamArray);
			
			while (resultSet.next()) {
				String utilization = resultSet.getString(3);
				JsonFormatter jsonformatter = new JsonFormatter(); 
				JSONObject utilObj = new JSONObject();
				Gson empgson = new Gson();
				jsonformatter = empgson.fromJson(utilization, jsonformatter.getClass());
				utilization = empgson.toJson(jsonformatter.getUtilization());
				String availHours = empgson.toJson(jsonformatter.getAvailHours());
				int total_rows = resultSet.getMetaData().getColumnCount();
							
				for (int i = 0; i < total_rows-1; i++) {
//					utilObj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(),
//							resultSet.getObject(i + 1));
					if (i + 1 == 3) {
						if (utilization == null) {
							utilization = defaultUtilization;
							availHours = defaultUtilization;
						}
						if(resultSet.getString(i+3).equalsIgnoreCase("CT")){
							// FOR Contractors 
							availHours = utilization;
						}
						utilObj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(),
								new JSONObject(utilization));
						utilObj.put("availHours", new JSONObject(availHours));

					} else {
						utilObj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(),
								resultSet.getObject(i + 1));
					}

				}
				

				utilArray.put(utilObj);
				// utilArray.
			}
			totalObject.put("utilization", utilArray);
			System.out.println(totalObject.toString());
			resultSet.close();
			workStreamResultSet.close();
			st.close();
            st1.close();

		} catch (Exception e) {
			System.out.println(e);
			responseObject.put("message", "Sorry, Please try again!!");
			return Response.status(400).entity(responseObject.toString()).build();
		} finally {
			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					System.out.println("Exception while closing connection..." + e.getMessage());
					e.printStackTrace();
				}
			}
		}

		// utilObj.put("workstreem", "Da");
		
		//totalObject.put(key, value)
		return Response.status(200).entity(totalObject.toString()).build();
	}	
}
class JsonFormatter{
	HashMap<String,Float> availHours;
	HashMap<String,Float> utilization;
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

