package com.lextar.proxy;

import java.util.HashMap;
import java.util.Map;

import com.lextar.dao.oracle.TblOemoBasisDao;
import com.lextar.database.OraDatabase;
import com.lextar.database.OraDatabaseFactory;
import com.lextar.enums.OraDbEnv;
public class DataProxy {
	static OraDatabase database = OraDatabaseFactory.getOraDatabase(OraDbEnv.T05Hist);
	
	static TblOemoBasisDao oemoBasisDao = new TblOemoBasisDao(database);
	static Map<String, String> moToProdmap = new HashMap<>();
	
	public static String getProductID(String moNo) throws Exception{
		
		if(moToProdmap.containsKey(moNo))
			return moToProdmap.get(moNo);
		
		if(moToProdmap.size() >= 5000 )
			moToProdmap.clear();
		
		String prod = oemoBasisDao.QueryProductID(moNo);
		moToProdmap.put(moNo, prod);
		
		return prod;
		
	}
}
