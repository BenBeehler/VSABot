package com.benbeehler.vsabot.utilities;

import com.benbeehler.vsabot.resource.Reference;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PerspectiveAPI {

	public static JSONObject evaluate(String message) throws IOException {
		OkHttpClient client = new OkHttpClient(); //client
		
		//Observe the perspective API documentation 
		
		JSONObject payload = new JSONObject(); //build post json object
		payload.put("comment", new JSONObject().put("text", message));
		payload.put("languages", new String[] { "en" });
		payload.put("requestedAttributes", new JSONObject().put("TOXICITY", new JSONObject()));
		//required JSON Body fields
		
		RequestBody body = RequestBody.create(Reference.JSON, payload.toString());
		
		Request request = new Request.Builder()
			    .url("https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + Reference.getPAPIToken())
			    .post(body)
			    .build(); //access the API by sending payload
		
		Response response = client.newCall(request).execute();
		JSONObject res = new JSONObject(response.body().string());
		return res;
	}
	
	public static double rate(String message) throws IOException {
		message = message.replaceAll("spicy", "").replaceAll("spiciness", "");
		
		JSONObject res = evaluate(message);
		//get JSONObject with body
		try {
				
			double prob = 100 * res.getJSONObject("attributeScores")
					.getJSONObject("TOXICITY") //fix nesting
					.getJSONObject("summaryScore")
					.getDouble("value");
			//get the actual score and multiply by 100
				
			return prob;
		} catch(JSONException e) {
			//e.printStackTrace();
			return 0;
		}
	}
	
}
