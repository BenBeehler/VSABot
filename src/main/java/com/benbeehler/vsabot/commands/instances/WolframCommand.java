package com.benbeehler.vsabot.commands.instances;

import java.util.List;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.WolframAPI;
import com.mashape.unirest.http.exceptions.UnirestException;

public class WolframCommand extends Command {

	public WolframCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "query";
		
		this.runnable = () -> {
			String response = WolframAPI.query(parametersString);
			
			response = response.replaceAll("<b>", "**").replaceAll("</b>", "**").replaceAll("<br>", "\n");
			
			if(response.length() >= 1990)
				response = response.substring(0, 1990).trim() + "...";
			
			System.out.println(String.valueOf(info.getEvent() == null));
			
			if(info.getType().equals(CommandType.DISCORD)) {
				event.getChannel().sendMessage(response).complete();
			} else if(info.getType().equals(CommandType.SCHOOLOGY)) {
				try {
					info.getComment().reply("Wolfram Alpha is not functional yet.");
				} catch (UnirestException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
}
