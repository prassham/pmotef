package com.ibm.pmo.modal;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class PropertiesCache {
	
	private final Properties weekenddateconfig = new Properties();
    
	   private PropertiesCache(){
	      //Private constructor to restrict new instances
	     // InputStream in = this.getClass().getClassLoader().getResourceAsStream("monthconfig.properties");
	      InputStream in = null;
		   System.out.println("Read all properties from file");
	      try {
	    	  in = this.getClass().getClassLoader().getResourceAsStream("weekenddate.properties");
	    	  weekenddateconfig.load(in);
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
}
	   
		   private static class LazyHolder
		   {
		      private static final PropertiesCache INSTANCE = new PropertiesCache();
		   }
		 
		   public static PropertiesCache getInstance()
		   {
		      return LazyHolder.INSTANCE;
		   }
		    
		   public String getProperty(String key){
		      return weekenddateconfig.getProperty(key);
		   }
		    
		   public Set<String> getAllPropertyNames(){
		      return weekenddateconfig.stringPropertyNames();
		   }
		    
		   public boolean containsKey(String key){
		      return weekenddateconfig.containsKey(key);
		   }
		}


