package com.benbeehler.vsabot.commands.instances;

import java.util.List;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.Message;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HelpCommand extends Command {

	public HelpCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "help";
		
		this.runnable = () -> {
			if(info.getType().equals(CommandType.SCHOOLOGY)) {
				try {
					info.getComment().reply(Message.HELP_MESSAGE);
				} catch (UnirestException e) {
					e.printStackTrace();
				}
			} else if(info.getType().equals(CommandType.DISCORD)) {
				
			}
		};
	}
	
}
