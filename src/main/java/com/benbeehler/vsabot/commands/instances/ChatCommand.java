package com.benbeehler.vsabot.commands.instances;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.Reference;
import com.benbeehler.vsabot.schoology.CommentManager;
import com.benbeehler.vsabot.schoology.DiscussionManager;
import com.benbeehler.vsabot.schoology.GroupManager;
import com.benbeehler.vsabot.schoology.instances.Discussion;
import com.benbeehler.vsabot.schoology.instances.Group;
import com.benbeehler.vsabot.schoology.instances.Comment;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ChatCommand extends Command {

	public ChatCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "chat";
		
		this.runnable = () -> {
			String chat = parametersString;
			
			try {
				Group[] groups = new Group[] {
					GroupManager.getGroup(Reference.TEST_GROUP),
					GroupManager.getGroup(Reference.GROUP1),
					GroupManager.getGroup(Reference.GROUP2),
					GroupManager.getGroup(Reference.GROUP3),
					GroupManager.getGroup(Reference.GROUP4)
				};
				
				//list of groups instantiated locally instead of universally
				
				List<Discussion> pull = new ArrayList<>();
				
				for(Group group : groups) {
					List<Discussion> local = DiscussionManager.getAllDiscussions(group);
					/*
					 * Get all discussions (no HTML because of rate limiter)
					 */
					
					for(int i = 0; i < 3; i++) {
						//get random discussions for random comment
						int index = new Random().nextInt(local.size()-1)+1;
						Discussion discussion = local.get(index);
						pull.add(discussion);
					}
				}
				
				List<Discussion> finalized = new ArrayList<>();
				//list of proper discussions
				
				pull.forEach(discussion -> {
					try {
						/*
						 * Rebuild discussion with proper HTML content
						 * Getting all discussions neglects the discussion's page src
						 */
						finalized.add(DiscussionManager.getDiscussion(discussion.getUrl(), discussion.getGroup()));
						Thread.sleep(500);
					} catch (UnirestException | IOException | InterruptedException e) {
						e.printStackTrace();
					}	
				});
				
				Map<String, String> commentMap = new HashMap<>();
				//key-value response pairs
				
				finalized.forEach(discussion -> {
					try {
						List<Comment> comments = CommentManager.getAllComments(discussion);
						
						comments.forEach(comment -> {
							if(comment.getParent() != null) {
								/*
								 * Assign parent value to comment value in map
								 */
								commentMap.put(comment.getParent().getContent(), comment.getContent());
							}
						});
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				Optional<String> optional = commentMap.keySet()
						.stream().filter(string -> string.toLowerCase()
								.contains(chat.toLowerCase()))
						.findAny();
				
				String response = "Buffered response.";
				/*
				 * Response is default until changed
				 */
				
				if(optional.isPresent()) {
					response = commentMap.get(optional.get());
				} else {
					int random = new Random().nextInt(commentMap.size()-1)+1;
					
					int iterator = 0;
					
					/*
					 * Messy way of searching but sets don't allow for indexing
					 */
					for(String str : commentMap.keySet()) {
						if(iterator == random) {
							String key = str;
							response = commentMap.get(key);
							break;
						}
						
						iterator++;
					}
				}
				
				/*
				 * Limit to only discord
				 */
				
				if(info.getType().equals(CommandType.DISCORD)) {
					info.getEvent().getChannel().sendMessage(response).complete();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		};
	}
	
}
