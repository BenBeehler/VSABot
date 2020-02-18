package com.benbeehler.vsabot;

import javax.security.auth.login.LoginException;

import com.benbeehler.schoologyapi.SchoologyAPI;
import com.benbeehler.vsabot.commands.CommandListener;
import com.benbeehler.vsabot.resource.Reference;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 * Hello VSA!
 * @since 12/24/2019
 * @author Ben Beehler
 * @version 2.X
 */

public class VSABot {
	
	private static final SchoologyAPI api = 
			new SchoologyAPI(Reference.getUsername(), Reference.getPassword());
	
	private static JDA jda;
	
    public static void main(String[] args) {      	
    	System.out.println("VSABot " + Reference.VERSION); 
    	
    	try {
    		api.authenticate(Reference.LOGIN_URL, Reference.HOME_URL);
    		
			jda = new JDABuilder(AccountType.BOT)
        		.setToken(Reference.getBotToken())
	 	       	.addEventListener(new CommandListener())
	 	        .buildAsync();                         //start the discord bot
		} catch (LoginException | UnirestException e) {
			e.printStackTrace(); 
			System.exit(1);
		} 
    }
    
    public static JDA getJDA() {
    	return jda;
    }
    
    public static SchoologyAPI getAPI() {
    	return api;
    }
}
