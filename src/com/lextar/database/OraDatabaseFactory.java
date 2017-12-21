package com.lextar.database;

import com.lextar.enums.OraDbEnv;

public class OraDatabaseFactory {
	
	public static OraDatabase getOraDatabase(OraDbEnv dbEnv){
		String url;
		OraDatabase database = null;
		if(dbEnv == OraDbEnv.T05Hist){
			//url = "jdbc:oracle:thin:@(description=(address=(host=T05HIS01)(protocol=tcp)(port=1531))(connect_data=(service_name=T05HIS01)(server=SHARED)))";
			url = "jdbc:oracle:thin:@T05HIS01:1531:T05HIS01";
			database = new OraDatabase(url,"pkgrpt_ap","rpt$520");
		}
		
		return database;
	}
}
