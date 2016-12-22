package com.ibm.pmo.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection {
	
	public static Connection getConnectionForPMO() throws Exception {
		
		try{

			//Context ctx = new InitialContext();
			//DataSource ds = (DataSource)ctx.lookup("jdbc/pmo"); 
			//return ds.getConnection();
			//I am figuring out how to add a resource.xml in the liberty server. till then hardcoding
			
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			String username= "user09092";

			String password= "JhD2NMKFKmLS";

			String url="jdbc:db2://75.126.155.153:50000/SQLDB";
			return DriverManager.getConnection(url, username, password);
			
			
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

}
