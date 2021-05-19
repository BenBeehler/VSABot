package com.benbeehler.vsabot.utilities;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.json.JSONArray;
import org.jsoup.Jsoup;

public class Parser {

	public static String purify(String content) {
		String[] amsplit = content.split("am");
		String[] pmsplit = content.split("pm");
		
		try {
			if(content.contains("pm")) {
				return pmsplit[1].trim();
			} else if(content.contains("am")) {
				return amsplit[1].trim();
			}
		} catch(Exception e) {
			return content;
		}
		
		return content;
	}
	
	public static String convertHTMLToMarkdown(String string) {
		String converted = string.replaceAll("<br>", "\n")
				.replaceAll("<i>", "*")
				.replaceAll("</i>", "")
				.replaceAll("<b>", "**")
				.replaceAll("</b>", "**");
		
		String parsed = Jsoup.parse(converted).text();
		return parsed;
	}
	
	public static String getCommentAuthor(String content) {
		String[] split = content.split(" ");
		return split[0] + " " + split[1];
	}
	
	public static String time(String content, String author) {
		String purified = Parser.purify(content);
		String time = content.replace(purified, "");
		time = time.replace(author, "").trim();
		
		return time;
	}
	
	public static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
	}
	
	public static String reverse(String input) {
        byte [] strAsByteArray = input.getBytes(); 
        
        byte [] result =  
                   new byte [strAsByteArray.length]; 
  
        // Store result in reverse order into the 
        // result byte[] 
        for (int i = 0; i<strAsByteArray.length; i++) 
            result[i] =  
             strAsByteArray[strAsByteArray.length-i-1]; 
  
        return new String(result); 
	}
		
	public static String[] getStringArray(JSONArray jsonArray) {
	    String[] stringArray = null;
	    if (jsonArray != null) {
	        int length = jsonArray.length();
	        stringArray = new String[length];
	        for (int i = 0; i < length; i++) { 
	            stringArray[i] = jsonArray.optString(i);
	        }
	    }
	    return stringArray;
	}
	
	public static int getNISTHourET() throws IOException, ParseException {
		//Totally unnecessary but neat nonetheless
		
		String TIME_SERVER = "time-a.nist.gov";   
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
		TimeInfo timeInfo = timeClient.getTime(inetAddress);
		long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
		Date time = new Date(returnTime);
		
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
		formatter.format(time);
		
		formatter.setTimeZone(TimeZone.getTimeZone("EST"));
		
		return formatter.parse((formatter.format(time))).getHours();
	}
}
