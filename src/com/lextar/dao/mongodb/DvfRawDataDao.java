package com.lextar.dao.mongodb;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lextar.database.MangoDatabaseFactory;
import com.lextar.raw.DvfData;
import com.mongodb.BulkWriteOperation;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class DvfRawDataDao {
	private static final Logger logger = LoggerFactory.getLogger(DvfRawDataDao.class);
	
	private MangoDatabaseFactory dbFactory;
	
	private static final String TABLE_NAME = "DVF_RAW_DATA";
	//myCollection
//	private static final String TABLE_NAME = "myCollection";
	
	public DvfRawDataDao(MangoDatabaseFactory dbFactory)
	{
		this.dbFactory = dbFactory;
	}
	
//	public void insert(List<DvfData> dvfDataList)
//	{
//		logger.info("[{}] collection insert list count : {}",TABLE_NAME, dvfDataList.size());
//		
//		MongoClient client = dbFactory.getMongoClient();
//		
//		
//		
//		try {
//			MongoDatabase database = client.getDatabase(MangoDatabaseFactory.DB_TEST);
//			
//			MongoCollection<Document> collection = database.getCollection(TABLE_NAME);
//			
//			ObjectMapper mapper = new ObjectMapper();
//			
//			List<Document> insDocList = new ArrayList<Document>();
//			
//			Timestamp nowTime = new Timestamp(new Date().getTime());
//			dvfDataList.forEach(data -> {
//				
//		        
//				try {
//					String json = mapper.writeValueAsString(data);
//			        Document doc = Document.parse(json);
//			        doc.append("INSERT_TIME", nowTime);
//			        insDocList.add(doc);
//			        
//				} catch (Exception e) {
//					
//					logger.error("TEST [{}] convert to mongodb document fail. ", data.getTest());
//					throw new RuntimeException(e);
//				}
//				
//			});	
//			
//			logger.debug("Insert count {}", insDocList.size());
//			collection.insertMany(insDocList);
//
//			
//			
//		} catch (Exception e) {
//			logger.error("[{}] collection insert list error . {}", TABLE_NAME, e);
//		}finally{
//			client.close();
//		}
//	}
	
	public void insert(Document dvfDoc)
	{
		logger.info("[{}] collection insert one raw",TABLE_NAME);
		
		MongoClient client = dbFactory.getMongoClient();
		
		
		
		try {
			MongoDatabase database = client.getDatabase(MangoDatabaseFactory.DB_TEST);
			
			MongoCollection<Document> collection = database.getCollection(TABLE_NAME);
			
			
			Timestamp nowTime = new Timestamp(new Date().getTime());
			dvfDoc.append("INSERT_TIME", nowTime);
	        
			collection.insertOne(dvfDoc);
			
			
			
		} catch (Exception e) {
			logger.error("[{}] collection insert list error . {}", TABLE_NAME, e);
		}finally{
			client.close();
		}
	}
	
	public void insert(List<Document> dvfDataList)
	{
		logger.info("[{}] collection insert list count : {}",TABLE_NAME, dvfDataList.size());
		
		MongoClient client = dbFactory.getMongoClient();
		
		
		
		try {
			MongoDatabase database = client.getDatabase(MangoDatabaseFactory.DB_TEST);
			
			MongoCollection<Document> collection = database.getCollection(TABLE_NAME);
			
			List<Document> insDocList = new ArrayList<Document>();
			
			Timestamp nowTime = new Timestamp(new Date().getTime());
			dvfDataList.forEach(data -> {
				
		        
				try {
					
			        data.append("INSERT_TIME", nowTime);
			        insDocList.add(data);
			        
				} catch (Exception e) {
					
					logger.error("TEST [{}] convert to mongodb document fail. ", data.toJson());
					throw new RuntimeException(e);
				}
				
			});	
			
			logger.debug("Insert count : {}", insDocList.size());
			
			collection.insertMany(insDocList);
			
			
			
		} catch (Exception e) {
			logger.error("[{}] collection insert list error . {}", TABLE_NAME, e);
		}finally{
			client.close();
		}
	}
	
}
