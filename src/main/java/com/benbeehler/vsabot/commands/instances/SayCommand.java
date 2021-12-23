package com.benbeehler.vsabot.commands.instances;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.Reference;

import java.util.List;

public class SayCommand extends Command {

	public SayCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "say";
		
		this.runnable = () -> {
			if(info.getType().equals(CommandType.DISCORD)) {
				if(Reference.isAdmin(info.getEvent().getAuthor())) {
					info.getEvent().getMessage().delete().complete();
					
					info.getEvent().getChannel().sendMessage(parametersString).complete();
				}
			}
		};
	}
}
