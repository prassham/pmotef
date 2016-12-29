package com.ibm.pmo.O2Observer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.codec.binary.Base64;

import org.lightcouch.Attachment;
import org.lightcouch.CouchDbException;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import com.cloudant.client.api.model.Params;

import com.ibm.pmo.employee.CloudantEmployee;

import com.google.gson.JsonObject;

@Path("/FileDownload")
public class viewObserver {
	 @GET
		@Path("/O2ObserverFileShow")
	    @Produces(MediaType.APPLICATION_OCTET_STREAM)
		 public Response viewFile() throws Exception {

		 
		 CloudantClient con = null;
			con = getConnection();
			Database db = con.database("o2observer", false);
			List<ObserverPojo> list=null;
			try{
				list = db.findByIndex("\"selector\":{\"creation_date\":{\"$gt\": 0}},\"sort\": [{\"creation_date\": \"desc\"}],\"limit\":1", ObserverPojo.class);
				System.out.println("the list:"+ list.toString());
      			}

				 catch (CouchDbException e){
						e.printStackTrace();
				 }
			
			System.out.println("Inside method calling");
		ObserverPojo obs = list.get(0);
		String Document_Id=obs.getId();
		Map<String, Attachment> _attachments = obs.getAttachments();
		System.out.println(_attachments.size());
		String Attachment_name="";
		for (String ATTACHMENT : _attachments.keySet()) {
		    System.out.println("PDF Attachment Name: " + ATTACHMENT);
		    Attachment_name=ATTACHMENT;
		 }
		String pdfBase64Data="";

		 try{
			 ObserverPojo foo = db.find(ObserverPojo.class, Document_Id, new Params().attachments());
			 pdfBase64Data = foo.getAttachments().get(Attachment_name).getData();
		 } catch (Exception e)
			{
				e.printStackTrace();
			}
			 
		  
		//    String pdfdata=new String(Base64.decodeBase64(pdfBase64Data.getBytes()));
		      byte[] bytes = Base64.decodeBase64(pdfBase64Data.getBytes());
		      
		      InputStream myInputStream = new ByteArrayInputStream(bytes);

			  ResponseBuilder response = Response.ok(myInputStream, MediaType.APPLICATION_OCTET_STREAM);
		      
     	      System.out.println("viewObserver : Building MediaType.APPLICATION_OCTET_STREAM data ! ");
			    
			    response.type("application/pdf");
				response.header("Content-Disposition", "filename=Observer.pdf");

			return response.status(200).build();
			}  
	 // Database connection
		/*public static CloudantClient getConnection() {
			String url = "https://e73401b4-0b36-4cf0-9c97-966350085029-bluemix.cloudant.com/o2observer/_all_docs";
			String userName = "e73401b4-0b36-4cf0-9c97-966350085029-bluemix";
			String password = "Bluemix4me";
			CloudantClient client = new CloudantClient(url,userName,password);
			System.out.println(client);
			return client;
		}*/
		
		public static CloudantClient getConnection() throws IOException {
		
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
				
}

