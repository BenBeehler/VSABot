package com.benbeehler.vsabot.resource;

import java.io.IOException;
import java.util.Random;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BibleAPI {

	private static final String[] ENCOURAGING = new String[] {
			"Philippians 4:13",
			"Isaiah 41:10",
			"Deuteronomy 31:6",
			"Isaiah 40:31",
			"Exodus 15:2",
			"Ephesians 6:10",
			"Deuteronomy 20:4",
			"Joshua 1:9",
			"Isaiah 12:2",
			"Matthew 11:28",
			"Isaiah 40:29 ",
			"Psalm 27:1",
			"Psalm 31:24",
			"Psalm 73:26",
			"Mark 12:30"
	};
	
	//may add more verses later
	
	public static String getVerse(String book, String chapter, String verse) throws IOException {
		OkHttpClient client = new OkHttpClient(); //client
		
		Request request = new Request.Builder()
			    .url("https://bible-api.com/" + book + "+" + chapter + ":" + verse)
			    .get()
			    .build(); //access the api by sending payload
		
		Response response = client.newCall(request).execute();
		JSONObject res = new JSONObject(response.body().string());
		
		return res.getString("text");
	}
	
	public static String getMotivationalVerse() throws IOException {
		String str = ENCOURAGING[new Random().nextInt(ENCOURAGING.length-1)+1];
		String[] spaceSplit = str.split(" ");
		
		String book = spaceSplit[0];
		
		str = str.replaceFirst(book, "").trim();
		
		String[] colonSplit = str.split(":");
		
		String chapter = colonSplit[0];
		String verse = colonSplit[1];

		return getVerse(book, chapter, verse) + "(" + book + " " + chapter + ":" + verse + ")";
	}
}
