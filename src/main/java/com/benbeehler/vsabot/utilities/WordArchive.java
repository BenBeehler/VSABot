package com.benbeehler.vsabot.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.benbeehler.vsabot.resource.MongoHandler;

public class WordArchive {

	//list of euphemisms
	public static final String[] NAUGHTY = MongoHandler.getList("euphemisms");
	
	//list of swear words
	public static final String[] SWEAR = MongoHandler.getList("swearwords");
	
	public static final String[] REDUNDANT = new String[] {
		"me",
		"my",
		"am",
		"oh",
		"eh",
		"um",
		"of",
		"if"
	};
	
	public static String regexify(String str) {
		StringBuilder sb = new StringBuilder();
		String[] split = str.split("");
		
		for(String ch : split) {
			sb.append("\\W*" + ch + "+");
		}
		
		String res = sb.toString().replaceFirst("\\W*", "").trim();
		
		return "\\b" + res + "(?:\\s*(?:e\\s*(s|d)|i\\s*n\\s*g))?\\b";
	}

	//check and see if euphemism is present
	public static boolean isNaughty(String query) {
		query = query.toLowerCase();
		for(String bad : NAUGHTY) {
			Pattern p = Pattern.compile(regexify(bad));
			
			Matcher m = p.matcher(query);
			if(m.find())
				return true;
		}
		
		return false;
	}
	
	public static boolean isSwear(String query) {
		query = query.toLowerCase();
		for(String bad : SWEAR) {
			bad = bad.toLowerCase();
			Pattern p = Pattern.compile(regexify(bad));
			
			Matcher m = p.matcher(query);
			boolean conditionOne = m.find();
			String newquery = query.replace(" ", "").replace(".", "");
			boolean conditionTwo = newquery.contains(bad);
			
			if(conditionOne && conditionTwo) {
				return true;
			}
		}
		
		return false;	
	}
}
