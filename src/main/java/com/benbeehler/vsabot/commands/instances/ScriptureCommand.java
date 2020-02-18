package com.benbeehler.vsabot.commands.instances;

import java.io.IOException;
import java.util.List;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.BibleAPI;
import com.benbeehler.vsabot.schoology.instances.Comment;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ScriptureCommand extends Command {

	public ScriptureCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "bible";
		
		this.runnable = () -> {
			try {
				if(info.getType().equals(CommandType.SCHOOLOGY)) {
					Comment comment = info.getComment();
					
					String content = parametersString;
					
					if(params.size() == 2) {
						String book = params.get(0);
						
						String[] colonSplit = content.split(":");
						
						if(colonSplit.length == 2) {
							String chapter = colonSplit[0];
							String verse = colonSplit[1];
							
							String text = BibleAPI.getVerse(book, chapter, verse);
												
							comment.reply(text + " (" + content + ")");
						} else {
							comment.reply("Invalid Format! <i>Use 'Book Chapter:Verse'</i>");
						}
					} else {
						comment.reply("Invalid Format! <i>Use 'Book Chapter:Verse'</i>");
					}
				}
			} catch(UnirestException | IOException e) {
				e.printStackTrace();
			}
		};
	}
	
}
