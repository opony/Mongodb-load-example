package com.lextar.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class OraDatabase {
	
	private String url;
	Connection conn = null; 
	String userName;
	String pwd;
	
	public OraDatabase(String rul, String userName, String pwd){
		this.url = rul;
		this.userName = userName;
		this.pwd = pwd;
	}
	
	public Connection getConnection() throws ClassNotFoundException, Exception{
		String driverName = "oracle.jdbc.driver.OracleDriver"; 
	    Class.forName(driverName);
	    
	    String serverName = "T05HIS01"; 
	    String portNumber = "1531"; 
	    String sid = "T05HIS01"; 
	    //String url = "jdbc:oracle:thin:@(description=(address=(host=" + serverName + ")(protocol=tcp)(port=" + portNumber + "))(connect_data=(service_name=" + sid + ")(server=SHARED)))";
	    conn = DriverManager.getConnection(url, "pkgrpt_ap", "rpt$520");
	    
	    return conn;  
	}
	
	public void close(){
		try {
			if(conn !=null && !conn.isClosed()){
				conn.close();
				conn = null;
			}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
}
