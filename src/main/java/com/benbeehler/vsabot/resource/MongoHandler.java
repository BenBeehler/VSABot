package com.benbeehler.vsabot.resource;

import java.util.Iterator;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoHandler {

	/*
	 * API Type
	 * default - 0
	 * stupidfish - 1
	 */
	
	public static boolean isOnline() {
		try {
			getClient().getAddress();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static MongoClient getClient() {
		MongoClientURI uri = new MongoClientURI("mongodb+srv://root:rootpassword@cluster0.stbdo.mongodb.net/main?retryWrites=true&w=majority"); 
		MongoClient client = new MongoClient(uri); 
		return client;
	}
	
	public static Document getConfig() {
		MongoDatabase database = getClient().getDatabase("main");   
	    MongoCollection<Document> collection = database.getCollection("config");
	    
	    FindIterable<Document> docList = collection.find();
	    Iterator<Document> documents = docList.iterator();
	    
	    if(documents.hasNext()) {
	    	Document document = documents.next();
	    	
	    	return document;
	    }
	    
	    return null;
	}
	
	public static String[] getList(String element) {
		String content = getConfig().toJson();
		
		JSONObject obj = new JSONObject(content);
		JSONArray array = obj.getJSONArray(element);
		
		String[] result = new String[array.length()];
		
		for (int i = 0; i < array.length(); i++) {
            result[i] = array.getString(i);
        }
		
		return result;
	}
}
