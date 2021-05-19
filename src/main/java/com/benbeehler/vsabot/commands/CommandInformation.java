package com.benbeehler.vsabot.commands;

import com.benbeehler.vsabot.schoology.instances.Comment;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandInformation {

	private MessageReceivedEvent event;
	private Comment comment;
	private CommandType type;
	
	public CommandInformation(CommandType type, MessageReceivedEvent event, Comment comment) {
		this.type = type;
		this.event = event;
		this.comment = comment;
	}

	public MessageReceivedEvent getEvent() {
		return event;
	}

	public Comment getComment() {
		return comment;
	}

	public CommandType getType() {
		return type;
	}
	
}
