package com.benbeehler.vsabot.commands.instances;

import java.io.IOException;
import java.util.List;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.BibleAPI;
import com.mashape.unirest.http.exceptions.UnirestException;

public class VerseCommand extends Command {

	public VerseCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "verse";
		
		this.runnable = () -> {
			try {
				if(info.getType().equals(CommandType.SCHOOLOGY)) {
					String response = BibleAPI.getMotivationalVerse();
					
					info.getComment().reply(response);
				}
			} catch(UnirestException | IOException e) {
				e.printStackTrace();
			}
		};
	}
	
}
