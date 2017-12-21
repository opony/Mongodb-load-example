package com.lextar.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MangoDatabaseFactory {
	
	public static final String DB_TEST = "test";
	
	String uri;
	public MangoDatabaseFactory(String uri)
	{
		this.uri = uri;
	}
	
	public MangoDatabaseFactory(String host, int port)
	{
		this.uri = "mongodb://" + host + ":" + port;
	}
	
	public MongoClient getMongoClient()
	{
		MongoClientURI connectionString = new MongoClientURI(uri);
		return new MongoClient(connectionString);
	}
	
	
}
