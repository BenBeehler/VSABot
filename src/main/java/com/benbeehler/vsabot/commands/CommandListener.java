package com.benbeehler.vsabot.commands;

import java.util.Arrays;
import java.util.List;

import com.benbeehler.vsabot.commands.instances.ChatCommand;
import com.benbeehler.vsabot.resource.BotScheduler;
import com.benbeehler.vsabot.schoology.SchoologyManager;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter implements EventListener {

	/*
	 * Listener systems are tight
	 */
	
	@Override
    public void onReady(ReadyEvent event) {
		event.getJDA().getPresence().setGame(Game.playing("VSABot 2.x | Veritas Press"));
		
		System.out.println("VSABot's discord processes are initialized. Starting Schoology processes.");
			
		SchoologyManager.start();
		BotScheduler.beginScheduler();
    }
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentStripped();
				
		if(message.startsWith("vsabot")) {
			message = message.replaceFirst("vsabot", "").trim();
			
			/*
			 * VSABot has received a new incoming command on discord.
			 * This process will strip away the indicator, get the prefix,
			 * tidy up the parameters, and execute the corresponding
			 * command.
			 */
			
			//split up the string by spaces
			String[] split = message.split(" ");
			
			//get prefix
			String prefix = split[0].toLowerCase();
			
			//remove prefix
			String parametersString = message.replaceFirst(prefix, "").trim();
			
			//String list of the parameters
			List<String> parameters = Arrays.asList(parametersString.split(" "));
			
			CommandInformation info = new CommandInformation(CommandType.DISCORD, event, null);
			CommandDump cd = new CommandDump(parametersString, parameters, info);
			
			cd.execute(prefix);
		} else if(message.startsWith(";")) {
			message = message.replaceFirst(";", "").trim();
			
			/*
			 * discord chat command feature (only exception to the rule)
			 */
			
			//split up the string by spaces
			String[] split = message.split(" ");
			
			//get prefix
			String prefix = split[0].toLowerCase();
			
			//remove prefix
			String parametersString = message.replaceFirst(prefix, "").trim();
			
			//String list of the parameters
			List<String> parameters = Arrays.asList(parametersString.split(" "));
			
			CommandInformation info = new CommandInformation(CommandType.DISCORD, event, null);
			ChatCommand cd = new ChatCommand(parametersString, parameters, info);
			
			cd.run();
		}
	}
	
}
