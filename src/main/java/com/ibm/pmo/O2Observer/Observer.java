package com.ibm.pmo.O2Observer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.lightcouch.CouchDbException;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

// This class saves the input observer file to cloudant database

@Path("/FileUpload")
public class Observer {
	public static final String PREFIX = "O2observer";
    public static final String SUFFIX = ".pdf";
    
	 @POST
		@Path("/O2ObserverFileUpload")
		@Consumes(MediaType.MULTIPART_FORM_DATA)
	
	 public Response uploadFile(
					@FormDataParam("file") InputStream uploadedInputStream,
					@FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {

				String uploadedFile = fileDetail.getFileName();
				File file = stream2file(uploadedInputStream);
			
				  CloudantClient connection = getConnection();
					Database db = connection.database("o2observer", false);
					try{ 
				  
				  long id = System.currentTimeMillis();

					// create a new document
					System.out.println("Creating new document with id : " + id);
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("name", uploadedFile);
					data.put("_id", id + "");
					data.put("creation_date", new Date().toString());
					db.save(data);

					// attach the object
					HashMap<String, Object> obj = db.find(HashMap.class, id + "");

					FileInputStream fileInputStream = new FileInputStream(file);
					db.saveAttachment(fileInputStream, file.getName(), "pdf", id + "", (String) obj.get("_rev"));
					fileInputStream.close();
				  
			      

			  }catch (CouchDbException e) {
					throw new RuntimeException("Unable to connect to repository", e);			   
			   }
				  
				  

				String output = "File " +uploadedFile + " uploaded to Cloudant Database " ;

				return Response.status(200).entity(output).build();

			}  
	 // Database connection
		public static CloudantClient getConnection() {
			String url = "https://e73401b4-0b36-4cf0-9c97-966350085029-bluemix.cloudant.com/utilization/02f6f1723b23f4223b35c6edb174d98b";
			String userName = "e73401b4-0b36-4cf0-9c97-966350085029-bluemix";
			String password = "Bluemix4me";
			CloudantClient client = new CloudantClient(url,userName,password);
			System.out.println(client);
			return client;
		}
		
	// conversion from inputstream to file	
		public static File stream2file (InputStream in) throws IOException {
	        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
	        tempFile.deleteOnExit();
	        try (FileOutputStream out = new FileOutputStream(tempFile)) {
	            IOUtils.copy(in, out);
	        }
	        return tempFile;
	    }
		
			
}
