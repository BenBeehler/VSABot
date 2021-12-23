package com.benbeehler.vsabot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class Command {
	
	/*
	 * Instead of overwhelming a single class with a long,
	 * drawn-out succession of verbosity, commands will fall into a
	 * polymorphic inheritance listener system. 
	 * 
	 * For the nerds out there, this class is essence and each specific command
	 * is an instantiated existence of that essence with "accidental attributes"
	 * 
	 * 'this joke was made by vsa gang'
	 */
	
	protected String prefix; 
	protected Runnable runnable = () -> { /* empty by default */ };
	protected List<String> parameters = new ArrayList<>();
	protected MessageReceivedEvent  event;
	protected CommandInformation info;
	protected String parametersString;
	
	public Command(String parametersString, List<String> parameters, CommandInformation info) {
		this.parameters = parameters;
		this.info = info;
		this.parametersString = parametersString;
	}
	
	public void run() {
		new Thread(runnable).start();
	}

	public String getPrefix() {
		return prefix;
	}
	
}
