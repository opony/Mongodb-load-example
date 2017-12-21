package com.lextar;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lextar.dao.mongodb.DvfRawDataDao;
import com.lextar.database.MangoDatabaseFactory;
import com.lextar.parser.DvfCsvParser;

public class LoaderMain {
	private static final Logger logger = LoggerFactory.getLogger(DvfRawDataDao.class);
	
	public static void main(String[] args) {
		
		MangoDatabaseFactory dbFactory = new MangoDatabaseFactory("10.234.72.237",27017 );
		
		DvfRawDataDao dvfDao = new DvfRawDataDao(dbFactory);
		
		Instant start = Instant.now();
		try {
			DvfCsvParser dvfParser = new DvfCsvParser();
			
//			Path folder = Paths.get("D:\\DVF_Temp_1");
//			Path compPath = Paths.get("D:/DVF_Complete_1");
			
			Path folder = Paths.get(args[0]);
			Path compPath = Paths.get(args[1]);
			
			long totCnt = Files.list(folder).count();
			logger.info("Prepare parse file count : {}", totCnt);
			int fIdx = 0;
	        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
	        	
	        	
	            for (Path path : directoryStream) {
	            	fIdx++;
	            	
	            	logger.info("{} / {}, ",fIdx,totCnt,   path.toString());
	            	
	            	//List<DvfData> dvfDataList = dvfParser.Parse(path.toString());
	            	List<Document> dvfDataList = dvfParser.ParseToDocument(path.toString());
	            	//Document dvfData = dvfParser.ParseToDocument(path.toString());
	            	if(dvfDataList.size() == 0){
	            		logger.warn("No raw data , then don't insert table");
	            		continue;
	            	}
	            		
	            	dvfDao.insert(dvfDataList);
	            	
	        		Files.move(path, compPath.resolve(path.getFileName()));
	            }
	        } catch (IOException ex) {
	        	throw ex;
	        }
			
		} catch (Exception e) {
			logger.error("loader error . ",e);
			
		} finally
		{
			
		}
		Instant end = Instant.now();
		
		logger.info("Done! , {}", Duration.between(start, end));
	}
	
	
	
//	public static void main(String[] args) {
//		MongoClient mongoClient = new MongoClient( "10.234.72.237" );
//		
//		try {
//			MongoDatabase database = mongoClient.getDatabase("test");
//			
//			MongoCollection<Document> collection = database.getCollection("myCollection");
//			Document myDoc = collection.find().first();
//			System.out.println(myDoc.toJson());
//			
////			Document doc = new Document("name", "MongoDB")
////	                .append("type", "database")
////	                .append("count", 1)
////	                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
////	                .append("info", new Document("x", 203).append("y", 102))
////	                .append("time", new Timestamp(new Date().getTime()));
////			
////			collection.insertOne(doc);
//			
//			//myDoc = collection.find(eq("name", "MongoDB")).first();
//			java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2017-09-27 08:40:00");
//			myDoc = collection.find(gt("time", timestamp)).first();
//			System.out.println(myDoc.toJson());
//			
////			Document doc = Document.parse("{'name':'mkyong', 'age':30}");
////			collection.insertOne(doc);
//			
//			//collection.deleteOne(eq("name", "MongoDB"));
//			System.out.println(collection.count());
//			
//			
//			
////			try (BufferedReader br = new BufferedReader(new FileReader("C:\\temp\\testfile.txt"))) {
////	            String line;
////	            while ((line = br.readLine()) != null) {
////	                System.out.println(line);
////	            }
////	        } catch (IOException e) {
////	            e.printStackTrace();
////	        }
//			
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally
//		{
//			mongoClient.close();
//		}
//		
//		
//		
//	}

}
