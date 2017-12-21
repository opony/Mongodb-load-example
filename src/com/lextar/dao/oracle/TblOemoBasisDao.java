package com.lextar.dao.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.lextar.database.OraDatabase;

public class TblOemoBasisDao {
	private OraDatabase database;
	
	public TblOemoBasisDao(OraDatabase database){
		this.database = database;
	}
	
	
	public String QueryProductID(String moNo) throws Exception{
		String prodID = "";
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select productno ")
			.append("from tbloemobasis ")
			.append("Where MONO = '").append(moNo).append("'");
			
			Connection conn = database.getConnection();
			Statement stmt = conn.createStatement(); 
			ResultSet rset = stmt.executeQuery("select mono, productno from tbloemobasis");
			
			
			while(rset.next()){
				prodID = rset.getString("productno");
			}
		} catch (Exception e) {
			throw e;
		} finally{
			database.close();
		}
		
		return prodID;
	}
}
