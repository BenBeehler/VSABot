package com.benbeehler.vsabot.commands.instances;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.Reference;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

public class DeleteCommand extends Command {

	public DeleteCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "delete";
		
		this.runnable = () -> {
			if(info.getType().equals(CommandType.SCHOOLOGY)) {
				try {
					info.getComment().getDiscussion().delete();
					Reference.getUpdatesChannel().sendMessage(info.getComment().getAuthor() + " deleted discussion " + info.getComment().getDiscussion().getTitle() + ".").complete();
				} catch (UnirestException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
}
