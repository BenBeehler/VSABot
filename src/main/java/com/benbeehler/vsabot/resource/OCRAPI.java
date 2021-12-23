package com.benbeehler.vsabot.resource;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class OCRAPI {

	//http://i.imgur.com/fwxooMv.png
	
	public static String readImage(String imgURL) throws UnirestException {
		try {
			JSONObject object = Unirest.get("https://api.ocr.space/parse/imageurl?apikey=" + Reference.getOCRToken() + "&url=" + imgURL).asJson().getBody()
					.getObject();
		
			//sends image to API to evaluate image content
		
			String str = object.getJSONArray("ParsedResults").getJSONObject(0).getString("ParsedText");
			
			str = str.replaceAll("\\r\\n", " ").trim();
			
			return str;
		} catch(org.json.JSONException e) {
			e.printStackTrace();
			
			return "";
		}
	}
	
}
