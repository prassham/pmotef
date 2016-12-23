package com.ibm.pmo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ibm.pmo.employee.EmployeeResource;

@Path("/PMOTest")
public class PMOTest {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Context
	ServletContext servletContext;
	@Context
	HttpHeaders httpHeaders;
	@Context
	HttpServletRequest httprequest;
	@Context
	HttpServletResponse httpresponse;

	@GET
	@Produces("text/plain")
	public Response getEmployee() {
		EmployeeResource empr = new EmployeeResource();
		JSONObject responseObject = new JSONObject();
		System.out.println("invoking ===" );
		String loginid = null;	
		Principal principal = httprequest.getUserPrincipal();
		System.out.println(principal);
		if(principal!=null) {
			System.out.println("success");
			String name = principal.getName();
			//String name = "prassham@in.ibm.com";
			System.out.println("Name is "+principal.getName());	
			System.out.println("Name is "+name);
			String [] splits = name.split("/");
			System.out.println("login id is "+splits[splits.length-1]);
			loginid = splits[splits.length-1];
			String result = empr.getId(loginid);
			if(result!=null){
				responseObject.put("email",loginid);
				return Response.status(200).entity(responseObject.toString()).build();
			}
			else{
				responseObject.put("message","Forbidden Access");
				System.out.println(responseObject);
				return Response.status(403).entity(responseObject.toString()).build();
			}
		}
	}
		
	@GET
	@Path("/EmpManageaccess")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkPMO(){
		Gson gson = new Gson();
		Response loggedinId =  getEmployee();
		EmployeeResource empr = new EmployeeResource();
		String json1 = loggedinId.getEntity().toString();
		JsonObject jobj = gson.fromJson(json1, JsonObject.class);
		String email = jobj.get("email").toString().replace("\"", "");
		String result = empr.getId(email);
		return result;
	}
}