package com.ibm.pmo.employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lightcouch.CouchDbException;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class CloudantEmployee {

	public static JsonObject getConnectionObject() {
		/*String url = "https://e73401b4-0b36-4cf0-9c97-966350085029-bluemix.cloudant.com/employee/_security";
		String userName = "e73401b4-0b36-4cf0-9c97-966350085029-bluemix";
		String password = "Bluemix4me";*/
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
        //JSONObject vcap;
		JsonObject credentials = null;
        JsonParser parser = new JsonParser();
        try{

        Object obj = parser.parse(VCAP_SERVICES);

        JsonObject jsonObject = (JsonObject) obj;
        JsonArray vcapArray = (JsonArray) jsonObject.get("cloudantNoSQLDB");

        //JSONArray vcapArray = (JSONArray) jsonObject.get(“cloudantNoSQULDB”);

        JsonObject vcap =(JsonObject) vcapArray.get(0);

        credentials = (JsonObject) vcap.get("credentials");

        /*String username = credentials.get("username").toString();

        String password = credentials.get("password").toString();

        String url = credentials.get("url").toString();

        String domain = credentials.get("name").toString();
		System.out.println(client);*/
       }
        catch(Exception e){
        	e.printStackTrace();
       }
       return credentials;
	}
	public static void main(String args[]) throws Exception{
		JsonObject client = getConnectionObject();
		System.out.println(client);
	}
}