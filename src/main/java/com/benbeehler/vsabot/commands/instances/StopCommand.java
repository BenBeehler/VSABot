package com.benbeehler.vsabot.commands.instances;

import java.util.List;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.Reference;

public class StopCommand extends Command {

	public StopCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "stop";
		
		this.runnable = () -> {			
			if(info.getType().equals(CommandType.DISCORD)) {
				if(Reference.isAdmin(info.getEvent().getAuthor())) {
					info.getEvent().getChannel().sendMessage("The bot will now stop.").complete();
					System.exit(0);
				}
			}
		};
	}
	
}
