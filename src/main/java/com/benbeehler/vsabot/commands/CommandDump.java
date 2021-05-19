package com.benbeehler.vsabot.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.benbeehler.vsabot.commands.instances.ChatCommand;
import com.benbeehler.vsabot.commands.instances.CheckCommand;
import com.benbeehler.vsabot.commands.instances.DeleteCommand;
import com.benbeehler.vsabot.commands.instances.GroupCommand;
import com.benbeehler.vsabot.commands.instances.HelpCommand;
import com.benbeehler.vsabot.commands.instances.PingCommand;
import com.benbeehler.vsabot.commands.instances.RateCommand;
import com.benbeehler.vsabot.commands.instances.SayCommand;
import com.benbeehler.vsabot.commands.instances.ScriptureCommand;
import com.benbeehler.vsabot.commands.instances.StatisticsCommand;
import com.benbeehler.vsabot.commands.instances.StopCommand;
import com.benbeehler.vsabot.commands.instances.VerseCommand;
import com.benbeehler.vsabot.commands.instances.WolframCommand;

public class CommandDump {

	private List<Command> commands = new ArrayList<>();
	
	public CommandDump(String parametersString, List<String> parameters, CommandInformation info) {
		/*
		 * Dump
		 */
		
		commands.add(new GroupCommand(parametersString, parameters, info));
		commands.add(new PingCommand(parametersString, parameters, info));
		commands.add(new HelpCommand(parametersString, parameters, info));
		commands.add(new SayCommand(parametersString, parameters, info));
		commands.add(new WolframCommand(parametersString, parameters, info));
		commands.add(new ScriptureCommand(parametersString, parameters, info));
		commands.add(new VerseCommand(parametersString, parameters, info));
		commands.add(new StopCommand(parametersString, parameters, info));
		commands.add(new DeleteCommand(parametersString, parameters, info));
		commands.add(new ChatCommand(parametersString, parameters, info));
		commands.add(new RateCommand(parametersString, parameters, info));
		commands.add(new StatisticsCommand(parametersString, parameters, info));
		commands.add(new CheckCommand(parametersString, parameters, info));
		
		/*
		 * Add the commands
		 */
	}
	
	public boolean execute(String prefix) {
		//Find list of commands with corresponding prefix
		Optional<Command> query = commands.stream()
				.filter(command -> command.getPrefix()
						.equalsIgnoreCase(prefix)).findAny();
		
		if(query.isPresent()) {
			//if the command exists, run it and return true
			
			query.get().run();
			
			//manual memory cleanup
			Runtime.getRuntime().gc();
			
			return true;
		}
		
		//manual memory cleanup
		Runtime.getRuntime().gc();
		
		return false;
	}
	
}
