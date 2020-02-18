package com.benbeehler.vsabot.utilities;

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
}
