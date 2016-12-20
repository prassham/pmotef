package com.ibm.pmo.startoftheweek;

import com.ibm.pmo.employee.*;
import com.ibm.pmo.vacation.VacationPOJO;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


@Path("/sowedit")
public class SowEditData {
	Gson gson = new Gson();

//Connecting to cloudant database
	
	public static CloudantClient getConnectionObject() {
		String url = "https://e73401b4-0b36-4cf0-9c97-966350085029-bluemix.cloudant.com/startoftheweek/_all_docs";
		String USERNAME = "e73401b4-0b36-4cf0-9c97-966350085029-bluemix";
		String PASSWORD = "Bluemix4me";
			CloudantClient client = new CloudantClient(url,USERNAME,PASSWORD);
			System.out.println("Conn"+client);
		return client;
	}
	
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	/*@Produces(MediaType.APPLICATION_JSON)*/
	public String insert(String data) throws JSONException {
		System.out.println("Inside rest");
		CloudantClient con = getConnectionObject();
		JSONObject responseObject = new JSONObject();
		Database db = con.database("startoftheweek", false);
		
		JsonObject jobj = gson.fromJson(data, JsonObject.class);
		
		try{
		
			db.post(jobj);
			responseObject.put("message","Database has been updated successfully");
			System.out.println("Database has been updated successfully");
			return "success";
		 	
		}
		catch (CouchDbException e){
			responseObject.put("message",e);
			return "Failure";
		}
	}
	
	@GET
	@Path("/view/{i}/{r}")
	//@Path("/view/{r}")
	@Produces("application/json")
	public String view(@PathParam("i") String team,@PathParam("r") String date ) throws CouchDbException {
		//System.out.println(date);
		System.out.println(team);
		CloudantClient con = getConnectionObject();
		JSONObject responseObject = new JSONObject();
		Database db = con.database("startoftheweek", false);
		String json2 = null;
		try{
		  List<GetSowDetails> list3 = db.findByIndex("\"selector\":{\"team\":\""+team+"\",\"date\":\""+date+"\"}", GetSowDetails.class);
			if(!list3.isEmpty())
			 json2 = gson.toJson(list3);
			 System.out.println("JSON"+json2);
			 }
			 catch (CouchDbException e){
			System.out.println(e);
				}
		return json2;
	}
	
}
