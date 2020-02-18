package com.benbeehler.vsabot.resource;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class OCRAPI {

	//http://i.imgur.com/fwxooMv.png
	
	public static String readImage(String imgURL) throws UnirestException {
		JSONObject object = Unirest.get("https://api.ocr.space/parse/imageurl?apikey=" + Reference.getOCRToken() + "&url=" + imgURL).asJson().getBody()
				.getObject();
		
		//sends image to API to evaluate image content
		
		try {
			String str = object.getJSONArray("ParsedResults").getJSONObject(0).getString("ParsedText");
			
			str = str.replaceAll("\\r\\n", " ").trim();
			
			return str;
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
}
