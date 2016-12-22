package com.ibm.pmo.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

@Path("/UtilResource")
public class UtilDisplay {
	
	 @GET
		@Path("/getUtilization")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getUtilization() throws JSONException,IOException {
			PMOUtilizationHelperCloudant utilizationObj = new PMOUtilizationHelperCloudant();
			return utilizationObj.getUtilization();
		}
}
