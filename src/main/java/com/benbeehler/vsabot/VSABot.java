package com.benbeehler.vsabot;

import com.benbeehler.schoologyapi.SchoologyAPI;
import com.benbeehler.vsabot.commands.CommandListener;
import com.benbeehler.vsabot.resource.BotScheduler;
import com.benbeehler.vsabot.resource.Reference;
import com.benbeehler.vsabot.schoology.SchoologyManager;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.security.auth.login.LoginException;


/**
 * Hello VSA!
 * @since 12/24/2019
 * @author Ben Beehler
 * @version 2.2.X
 */

public class VSABot implements EventListener {
	
	private static final SchoologyAPI api = 
			new SchoologyAPI(Reference.getUsername(), Reference.getPassword());
	
	private static JDA jda;
	
    public static void main(String[] args) throws InterruptedException {          	
    	System.out.println("VSABot " + Reference.VERSION); 
    	
    	try {
    		api.authenticate(Reference.LOGIN_URL, Reference.HOME_URL);
    		
    		JDA jda = JDABuilder.createDefault(Reference.getBotToken()).build();
    		setJDA(jda);

    		jda.addEventListener(new VSABot());
            jda.addEventListener(new CommandListener());                   //start the discord bot
            
			jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.listening("the moderators."));
			
			jda.awaitReady();
			
			//			Group group = new GroupManager().getGroup(Reference.GROUP4);
			//DiscussionManager.getAllDiscussions(group);
		} catch (UnirestException | LoginException e) {
			e.printStackTrace(); 
			System.exit(1);
		} 
    }
    
	@Override
	public void onEvent(GenericEvent event) {
		if (event instanceof ReadyEvent) {
			System.out.println("VSABot's discord processes are initialized. Starting Schoology processes.");
			
			SchoologyManager.start();
			BotScheduler.beginScheduler();
		}
    }
	
	private static void setJDA(JDA jda) {
		VSABot.jda = jda;
	}
    
    public static JDA getJDA() {
    	return jda;
    }
    
    public static SchoologyAPI getAPI() {
    	return api;
    }
}
