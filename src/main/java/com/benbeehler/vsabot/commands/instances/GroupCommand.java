package com.benbeehler.vsabot.commands.instances;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.benbeehler.vsabot.commands.Command;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.ChannelHandler;
import com.benbeehler.vsabot.resource.Reference;
import com.benbeehler.vsabot.schoology.CommentManager;
import com.benbeehler.vsabot.schoology.DiscussionManager;
import com.benbeehler.vsabot.schoology.GroupManager;
import com.benbeehler.vsabot.schoology.SchoologyManager;
import com.benbeehler.vsabot.schoology.instances.Discussion;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.entities.MessageChannel;


public class GroupCommand extends Command {

	public GroupCommand(String parametersString, List<String> params, CommandInformation info) {
		super(parametersString, params, info);
		
		this.prefix = "group";
		
		this.runnable = () -> {
			if(info.getType().equals(CommandType.DISCORD)) {
				if(Reference.canExecute(info.getEvent().getMember())) {
					//check user permissions
					
					MessageChannel channel = info.getEvent().getChannel();
					
					if(params.size() >= 2) {
						String mode = params.get(0);
						String group = params.get(1);
						
						/*
						 * Run through the list
						 */
						
						
						/*
						 * Pretty straightforward
						 * Basic logic based on parameters
						 */
						
						try {
							if(mode.equalsIgnoreCase("log") && group.equalsIgnoreCase("toggle")) {
								Reference.toggleLog();
								
								if(Reference.getLog()) {
									info.getEvent().getChannel().sendMessage("VSABot will now log every new comment.").complete();
								} else {
									info.getEvent().getChannel().sendMessage("VSABot will stop logging every new comment.").complete();
								}
							} else if(mode.equalsIgnoreCase("enable")) {
								switch(group) {
									case "3-5":
										SchoologyManager.enableNewDiscussions(Reference.GROUP1_PRIVACY);
										channel.sendMessage("You have enabled discussion creation on 3-5.").complete();
										break;
									case "6-7":
										SchoologyManager.enableNewDiscussions(Reference.GROUP2_PRIVACY);
										channel.sendMessage("You have enabled discussion creation on 6-7.").complete();
										break;
									case "8-9":
										SchoologyManager.enableNewDiscussions(Reference.GROUP3_PRIVACY);
										channel.sendMessage("You have enabled discussion creation on 8-9.").complete();
										break;
									case "10-12":
										SchoologyManager.enableNewDiscussions(Reference.GROUP4_PRIVACY);
										channel.sendMessage("You have enabled discussion creation on 10-12.").complete();
										break;
									case "all":
										SchoologyManager.enableAll();
										channel.sendMessage("You have enabled discussion creation on all groups.").complete();
										break;
									default:
										channel.sendMessage("Invalid `group` parameter.").complete();
										break;
								}
							} else if(mode.equalsIgnoreCase("disable")) {
								switch(group) {
									case "3-5":
										SchoologyManager.disableNewDiscussions(Reference.GROUP1_PRIVACY);
										channel.sendMessage("You have disabled discussion creation on 3-5.").complete();
										break;
									case "6-7":
										SchoologyManager.disableNewDiscussions(Reference.GROUP2_PRIVACY);
										channel.sendMessage("You have disabled discussion creation on 6-7.").complete();
										break;
									case "8-9":
										SchoologyManager.disableNewDiscussions(Reference.GROUP3_PRIVACY);
										channel.sendMessage("You have disabled discussion creation on 8-9.").complete();
										break;
									case "10-12":
										SchoologyManager.disableNewDiscussions(Reference.GROUP4_PRIVACY);
										channel.sendMessage("You have disabled discussion creation on 10-12.").complete();
										break;
									case "all":
										SchoologyManager.disableAll();
										channel.sendMessage("You have disabled discussion creation on all groups.").complete();
										break;
									default:
										channel.sendMessage("Invalid `group` parameter.").complete();
										break;
								}
							} else if(mode.equalsIgnoreCase("open")) {
								switch(group) {
									case "3-5":
										SchoologyManager.openGroup(Reference.GROUP1_PRIVACY);
										channel.sendMessage("You have opened discussions for 3-5.").complete();
										break;
									case "6-7":
										SchoologyManager.openGroup(Reference.GROUP2_PRIVACY);
										channel.sendMessage("You have opened discussions for 6-7.").complete();
										break;
									case "8-9":
										SchoologyManager.openGroup(Reference.GROUP3_PRIVACY);
										channel.sendMessage("You have opened discussions for 8-9.").complete();
										break;
									case "10-12":
										SchoologyManager.openGroup(Reference.GROUP4_PRIVACY);
										channel.sendMessage("You have opened discussions for 10-12.").complete();
										break;
									case "all":
										SchoologyManager.openAll();
										channel.sendMessage("You have opened discussions for all groups.").complete();
										break;
									default:
										channel.sendMessage("Invalid `group` parameter.").complete();
										break;
								}
							} else if(mode.equalsIgnoreCase("close")) {
								switch(group) {
									case "3-5":
										SchoologyManager.closeGroup(Reference.GROUP1_PRIVACY);
										channel.sendMessage("You have closed discussions for 3-5.").complete();
										break;
									case "6-7":
										SchoologyManager.closeGroup(Reference.GROUP2_PRIVACY);
										channel.sendMessage("You have closed discussions for 6-7.").complete();
										break;
									case "8-9":
										SchoologyManager.closeGroup(Reference.GROUP3_PRIVACY);
										channel.sendMessage("You have closed discussions for 8-9.").complete();
										break;
									case "10-12":
										SchoologyManager.closeGroup(Reference.GROUP4_PRIVACY);
										channel.sendMessage("You have closed discussions for 10-12.").complete();
										break;
									case "all":
										SchoologyManager.closeAll();
										channel.sendMessage("You have closed discussions for all groups.").complete();
										break;
									default:
										channel.sendMessage("Invalid `group` parameter.").complete();
										break;
								}
							} else if(mode.equalsIgnoreCase("list")) {
								if(params.size() >= 3) {
									String title = parametersString.replaceFirst(params.get(0), "")
											.replaceFirst(params.get(1), "").trim();
									
									//replace previous parameters to get title
									
									String groupLink = null;
									
									switch(group) {
										case "3-5":
											groupLink = Reference.GROUP1;
											break;
										case "6-7":
											groupLink = Reference.GROUP2;
											break;
										case "8-9":
											groupLink = Reference.GROUP3;
											break;
										case "10-12":
											groupLink = Reference.GROUP4;
											break;
										case "all":
											groupLink = "all";
											break;
										default:
											channel.sendMessage("Invalid `group` parameter.").complete();
											break;
									}
									
									if(groupLink != null) {
										List<Discussion> applicable = new ArrayList<>();
										
										//Get all matching discussions
										if(groupLink.equalsIgnoreCase("all")) {
											 applicable.addAll(DiscussionManager
												.getAllDiscussions(GroupManager.getGroup(Reference.GROUP1)));
											 applicable.addAll(DiscussionManager
														.getAllDiscussions(GroupManager.getGroup(Reference.GROUP2)));
											 applicable.addAll(DiscussionManager
														.getAllDiscussions(GroupManager.getGroup(Reference.GROUP3)));
											 applicable.addAll(DiscussionManager
														.getAllDiscussions(GroupManager.getGroup(Reference.GROUP4)));
											 
											 SchoologyManager.dBuffer.addAll(applicable);
										} else {
											List<Discussion> discussions = DiscussionManager
												.getAllDiscussions(GroupManager.getGroup(groupLink));
											applicable = discussions.stream().filter(discussion -> discussion.getTitle()
													.toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
											
											SchoologyManager.dBuffer.addAll(applicable);
										}
										
										info.getEvent().getChannel().sendMessage("Calculating....").queue();
										
										StringBuilder sb = new StringBuilder();
										
										applicable.forEach(discussion -> {
											//info.getEvent().getChannel().sendMessage("`" + discussion.getTitle() + "`").complete();
											
											sb.append(discussion.getTitle() + " - " + discussion.getGroup().getTitle() + "\n\n");
										});
										
										int x = 0;
										for(int i = 0; i < sb.toString().length(); i+=2040) {
											ChannelHandler.sendEmbeded(info.getEvent().getChannel(), sb.toString().substring(x, i) + "...", Color.DARK_GRAY);	
											x = i;
										}										
									}
								} else {
									
								}
							} else if(mode.equalsIgnoreCase("buffer")) {
								if(group.equalsIgnoreCase("info")) {
									int sum = 0;
									String g = "";
									for(Discussion d : SchoologyManager.dBuffer) {
										g = d.getGroup().getTitle();
										sum+=CommentManager.getAllComments(d).size();
										Thread.sleep(500);
									}
									
									String response = ", this comment may violate discussion guidelines.\n"
											+ "**Discussions**: " + SchoologyManager.dBuffer.size() + "\n"
											+ "**Sum of comments**: " + sum + "\n"
											+ "**Group**: " + g;
									
									Reference.getUpdatesChannel().sendMessage(response).complete();
								}
							}
						} catch(UnirestException | IOException | InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						channel.sendMessage("Invalid parameter count! `vsabot group [mode] [group]").complete();
					}
				}
			}
		};
	}
	
}
