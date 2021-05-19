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
			
			String discordResponse = response.replaceAll("<b>", "**").replaceAll("</b>", "**").replaceAll("<br>", "\n");
			
			if(discordResponse.length() >= 1990)
				discordResponse = discordResponse.substring(0, 1990).trim() + "...";
			
			if(info.getType().equals(CommandType.DISCORD)) {
				info.getEvent().getChannel().sendMessage(discordResponse).complete();
			} else if(info.getType().equals(CommandType.SCHOOLOGY)) {
				try {
					info.getComment().reply(response);
				} catch (UnirestException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
}
