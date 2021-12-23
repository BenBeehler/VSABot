package com.benbeehler.vsabot.commands.instances;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.BotStatistics;
import com.benbeehler.vsabot.resource.ChannelHandler;

import java.awt.*;
import java.util.List;

public class StatisticsCommand extends Command {

	public StatisticsCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "statistics";
		
		this.runnable = () -> {
			if(info.getType().equals(CommandType.DISCORD)) {
				String message = "**Daily Bot Statistics**\n"
								+ "Today's Comments: " + BotStatistics.getTodayComments() + "\n"
								+ "Today's Discussions: " + BotStatistics.getTodayComments() + " \n"
								+ "Today's Reports: " + BotStatistics.getTodayReports() + "\n"
								+ "Today's Deleted Comments: " + BotStatistics.getTodayDeleted() + " \n"
								+ "Today's Euphemisms: " + BotStatistics.getTodayEuphemisms() + "\n"
								+ "Today's Moderator Comments: " + BotStatistics.getTodayModComments() + " \n";
				
				ChannelHandler.sendEmbeded(info.getEvent().getTextChannel(), message, Color.DARK_GRAY);
			}
		};
	}
	
}
