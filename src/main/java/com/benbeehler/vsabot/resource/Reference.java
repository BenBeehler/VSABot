package com.benbeehler.vsabot.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import com.benbeehler.vsabot.VSABot;
import com.benbeehler.vsabot.utilities.Parser;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import okhttp3.MediaType;

public class Reference {

	/*
	 * 
	 * List of Valid Keys and Reference Links
	 * 
	 */
	
	private static JSONObject config = null;
	
	public static final MediaType JSON = 
			MediaType.parse("application/json; charset=utf-8");
	
	public static final String LOGIN_URL = "https://resource3.veritaspress.com/theHub/remoteLogin/login.php";
	public static final String HOME_URL = "https://scholars.veritaspress.com/";
	
	public static final String TEST_GROUP = "http://scholars.veritaspress.com/group/2057296450/discussion";
	public static final String GROUP1 = "https://scholars.veritaspress.com/group/2897283446/discussion"; //3-5
	public static final String GROUP2 = "https://scholars.veritaspress.com/group/2897263775/discussion"; //6-7
	public static final String GROUP3 = "https://scholars.veritaspress.com/group/2897218717/discussion"; //8-9
	public static final String GROUP4 = "https://scholars.veritaspress.com/group/2897185682/discussion"; //10-12
	
	public static final String GROUP1_PRIVACY = "https://scholars.veritaspress.com/group/2897283446/edit/privacy";
	public static final String GROUP2_PRIVACY = "https://scholars.veritaspress.com/group/2897263775/edit/privacy";
	public static final String GROUP3_PRIVACY = "https://scholars.veritaspress.com/group/2897218717/edit/privacy";
	public static final String GROUP4_PRIVACY = "https://scholars.veritaspress.com/group/2897185682/edit/privacy";
		
	public static final String SERVER_ID = "705066534904791051";
	public static final String CHANNEL_ID = "705066536263745572";
	
	public static final String GSD1_ID = "605437389778976770";
	public static final String GSD2_ID = "605437389778976770";
	public static final String MSD_ID = "605437389778976770";
	public static final String HSD_ID = "605437389778976770";
	
	public static final String ACCOUNT_NAME = "Click to";
	
	public static final String VERSION = "Version 2.2.3";
	
	private static boolean log = false;
	
	public static List<String> DISCORD_ADMINS = Arrays.asList(new String[] {
			"291308460405161984",
			"607987019129290783",
			"520348027458682901",
			"605436948986986511"
	}); 
	
	public static String[] PRIVACY_URLS = new String[] {
			"https://scholars.veritaspress.com/group/2897185682/edit/privacy",
			"https://scholars.veritaspress.com/group/2897218717/edit/privacy",
			"https://scholars.veritaspress.com/group/2897263775/edit/privacy",
			"https://scholars.veritaspress.com/group/2897283446/edit/privacy"
	};
	
	public static boolean DISABLED = false;
	
	public static boolean isAdmin(User user) {
		return DISCORD_ADMINS.contains(user.getId()) || (user == Reference.getUpdatesChannel().getJDA().getSelfUser());
	}
	
	public static boolean canExecute(Member author) {
		if(author.getUser().getId().equals("291308460405161984") 
				|| author.getUser().getId().equals("520348027458682901")) {
			return true;
		}
		
		if(author.getUser() == Reference.getUpdatesChannel().getJDA().getSelfUser()) {
			return true;
		}
		
		if(author.getGuild().equals(Reference.getUpdatesChannel().getGuild())) {
			boolean headmod = author.getRoles().stream().filter(r -> r.getName().equalsIgnoreCase("head moderators")).findAny().isPresent();
			boolean advisor = author.getRoles().stream().filter(r -> r.getName().equalsIgnoreCase("advisors")).findAny().isPresent();
			boolean administrator = author.getRoles().stream().filter(r -> r.getName().equalsIgnoreCase("administrators")).findAny().isPresent();
			
			return headmod || advisor || administrator;
		}
		
		return false;
	}

	public static TextChannel getUpdatesChannel() {
		return VSABot.getJDA().getGuildById(SERVER_ID)
				.getTextChannelById(CHANNEL_ID);
	}
	
	public static boolean isDisabled() {
		return DISABLED;
	}

	public static void setDisabled(boolean disabled) {
		DISABLED = disabled;
	}

	private static JSONObject getConfig() {
		if(config == null) {
			//File file = new File("./config.json");
			
			File file = new File("./config.json");
			
			try {
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				StringBuilder sb = new StringBuilder();
				
				String st = "";
				while ((st = br.readLine()) != null) {
					sb.append(st);
				}
				
				config = new JSONObject(sb.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
				
				return new JSONObject();
			} 
		}
		
		return config;
	}
	
	public static void toggleLog() {
		log = !log;
	}
	
	public static boolean getLog() {
		return log;
	}
	
	public static String[] getEuphemisms() {
		return Parser.getStringArray(getConfig().getJSONArray("euphemisms"));
	}
	
	public static String[] getSwear() {
		return Parser.getStringArray(getConfig().getJSONArray("swearwords"));
	}
	
	public static String[] getExceptions() {
		return Parser.getStringArray(getConfig().getJSONArray("exceptions"));
	}
	
	public static String getBotToken() {
		return getConfig().getString("apitoken");
	}
	
	public static String getPAPIToken() {
		return getConfig().getString("perspectivetoken");
	}
	
	public static String getOCRToken() {
		return getConfig().getString("ocrtoken");
	}
	
	public static String getWolframToken() {
		return getConfig().getString("wolframtoken");
	}
	
	public static String getConnectionURI() {
		return getConfig().getString("dburi");
	}
	
	public static String getUsername() {
		return getConfig().getString("username");
	}
	
	public static String getPassword() {
		return getConfig().getString("password");
	}
}
