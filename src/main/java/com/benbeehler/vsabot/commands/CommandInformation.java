package com.benbeehler.vsabot.commands;

import com.benbeehler.vsabot.schoology.instances.Comment;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandInformation {

	private GuildMessageReceivedEvent event;
	private Comment comment;
	private CommandType type;
	
	public CommandInformation(CommandType type, GuildMessageReceivedEvent event, Comment comment) {
		this.type = type;
		this.event = event;
		this.comment = comment;
	}

	public GuildMessageReceivedEvent getEvent() {
		return event;
	}

	public Comment getComment() {
		return comment;
	}

	public CommandType getType() {
		return type;
	}
	
}
