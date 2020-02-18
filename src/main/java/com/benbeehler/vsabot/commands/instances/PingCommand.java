package com.benbeehler.vsabot.commands.instances;

import java.util.List;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.core.entities.TextChannel;

public class PingCommand extends Command {

	public PingCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "ping";
		
		this.runnable = () -> {
			if(info.getType().equals(CommandType.SCHOOLOGY)) {
				try {
					info.getComment().reply("Pong!");
				} catch (UnirestException e) {
					e.printStackTrace();
				}
			} else {
				TextChannel channel = info.getEvent().getChannel();
				channel.sendMessage("Pong!").complete();
			}
		};
	}
	
}
