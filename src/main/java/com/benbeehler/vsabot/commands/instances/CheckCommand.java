package com.benbeehler.vsabot.commands.instances;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.BotScheduler;

import java.util.List;

public class CheckCommand extends Command {

	public CheckCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "time";
		
		this.runnable = () -> {
			if(info.getType().equals(CommandType.DISCORD)) {
				int one = Integer.parseInt(params.get(0));
				int two = Integer.parseInt(params.get(1));
				
				BotScheduler.check(one, two);
				
				info.getEvent().getTextChannel().sendMessage("Running...").complete();
			}
		};
	}
	
}
