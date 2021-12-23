package com.benbeehler.vsabot.schoology;

import com.benbeehler.vsabot.VSABot;
import com.benbeehler.vsabot.commands.CommandDump;
import com.benbeehler.vsabot.commands.CommandInformation;
import com.benbeehler.vsabot.commands.CommandType;
import com.benbeehler.vsabot.resource.BotStatistics;
import com.benbeehler.vsabot.resource.MessageLib;
import com.benbeehler.vsabot.resource.Reference;
import com.benbeehler.vsabot.schoology.instances.Comment;
import com.benbeehler.vsabot.schoology.instances.Discussion;
import com.benbeehler.vsabot.schoology.instances.Group;
import com.benbeehler.vsabot.utilities.Parser;
import com.benbeehler.vsabot.utilities.PerspectiveAPI;
import com.benbeehler.vsabot.utilities.ProcessHandler;
import com.benbeehler.vsabot.utilities.WordArchive;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SchoologyManager {

	public static final List<Discussion> dBuffer = new ArrayList<Discussion>();
	
	public static void start() {
		ProcessHandler.spawn(() -> {
			while(true) {
				try {
					Group[] groups = new Group[] {
						GroupManager.getGroup(Reference.TEST_GROUP),
						GroupManager.getGroup(Reference.GROUP1),
						GroupManager.getGroup(Reference.GROUP2),
						GroupManager.getGroup(Reference.GROUP3),
						GroupManager.getGroup(Reference.GROUP4)
					};
					
					for(Group group : groups) {
						List<Discussion> discussions = DiscussionManager.getUpdatedDiscussions(group);
						
						for(Discussion discussion : discussions) {
							List<Comment> newcomments = CommentManager.getNewComments(discussion);
							List<Comment> allcomments = CommentManager.getAllComments(discussion);
							
							if(allcomments.size() == 1) {
								if(discussion.getGroup().getTitle().contains("10-12")) {
									discussion.comment(MessageLib.NEW_DISCUSSION_WELCOME_MEME);
								} else {
									discussion.comment(MessageLib.NEW_DISCUSSION_WELCOME);
								}
							}
							
							for(Comment comment : newcomments) {
								boolean action = evaluateComment(comment);
								
								if(!action) {
									//The comment did not violate guidelines
									
									if(Reference.getLog()) {
										DiscordCommunication.logComment(comment);
									}
									
									String content = comment.getContent();
									
									if(content.startsWith("@")) {
										content = content.replaceFirst("@", "");
										
										String[] split = content.split(" ");
										
										//get prefix
										String prefix = split[0].toLowerCase();
										
										//remove prefix
										String parametersString = content.replaceFirst(prefix, "").trim();
										
										//String list of the parameters
										List<String> parameters = Arrays.asList(parametersString.split(" "));
										
										CommandInformation info = new CommandInformation(CommandType.SCHOOLOGY, null, comment);
										CommandDump cd = new CommandDump(parametersString, parameters, info);
										
										cd.execute(prefix);
									}
								}
							}
						}
					}
					
					try {
						Thread.sleep(ProcessHandler.seconds(1));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			} 
		});
	}
	
	public static boolean evaluateComment(Comment comment) throws IOException, UnirestException {
		String content = comment.getContent();
				
		/*
		 * General rating scheme
		 */
		
		double rating = PerspectiveAPI.rate(content);
		
		boolean swearing = WordArchive.isSwear(content);
		boolean euphemism = WordArchive.isNaughty(content);
		boolean report = rating > 70;
		boolean respond = rating > 90;
		
		String imageContent = CommentManager.getImagesContent(comment);
		
		if(WordArchive.isNaughty(imageContent)) {
			euphemism = true;
		}
		
		if(WordArchive.isSwear(imageContent)) {
			swearing = true;
		}
		
		/*
		 * Bot checks for content in reverse
		*/
		
		String reversed = Parser.reverse(content);
		String reversedImgContent = Parser.reverse(content);
		
		if(WordArchive.isNaughty(reversed)) {
			euphemism = true;
		}
			
		if(WordArchive.isSwear(reversed)) {
			swearing = true;
		}
		
		if(WordArchive.isNaughty(reversedImgContent)) {
			euphemism = true;
		}
		
		if(WordArchive.isSwear(reversedImgContent)) {
			swearing = true;
		}
		
		if(comment.isMod()) {
			BotStatistics.addTodayModComments();
		}
		
		/*
		 * 
		 */
		
		if(comment.getContent().contains("https://www.youtube.com/watch?v=dQw4w9WgXcQ")) {
			comment.reply("watch out - this is a rickroll");
		}
		
		/*
		 * Log for stats
		 */
		BotStatistics.addTodayComments(); 
		
		
		if(validateComment(comment)) {
			if(swearing) {
				BotStatistics.addTodaySwears();
				
				//swearing
				rating = 99;
				
				comment.reply(MessageLib.HARSH_COMMENT_RESPONSE);
				comment.delete();
				
				DiscordCommunication.reportComment(comment, rating, euphemism, swearing);
				
				return true;
			} else if(euphemism) {
				BotStatistics.addTodayEuphemisms();
				
				//euphemism
				rating = 90;
				
				comment.reply(MessageLib.EUPHEMISM_RESPONSE);
				
				DiscordCommunication.reportComment(comment, rating, euphemism, swearing);
				
				return true;
			} else if(report) {
				BotStatistics.addTodayReports();
				
				//report to the moderators
				
				//If the comment rating is > 70, report. If it > 90, respond to student.
				DiscordCommunication.reportComment(comment, rating, euphemism, swearing);
				
				if(respond) {
					comment.reply(MessageLib.MODERATE_COMMENT_RESPONSE);
				}
				
				return true;
			}
			
			return false;
		} else {
			comment.delete();
			//the individual does not have the required permissions to comment in this discussion
			
			return false;
		}
	}
	
	public static boolean validateComment(Comment comment) {
		String title = comment.getDiscussion().getTitle().toLowerCase();
				
		/*
		 * If the discussion has any of these tags/identifiers, delete the comment.
		 */
		
		if(comment.isMod()) {
			return true;
		}
		
		String[] prohibited = new String[] {
			"[locked]",
			"[admins]",
			"[mods]",
			"[info]"
		};
		
		for(String tag : prohibited) {
			if(title.contains(tag)) {
				return false;
			}
		}
		
		return true;
	}
	
	
	/*
	 * Enable/disable and open/close discussions functions
	 */
	
	public static void enableAll() {
		ProcessHandler.spawn(() -> {
			String[] urls = Reference.PRIVACY_URLS;
			
			for(String url : urls) {
				try {
					SchoologyManager.enableNewDiscussions(url);
					Thread.sleep(5000);
					//Schoology has a rate limiter
				} catch (UnirestException | InterruptedException e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
	public static void disableAll() {
		ProcessHandler.spawn(() -> {
			String[] urls = Reference.PRIVACY_URLS;
			
			for(String url : urls) {
				try {
					SchoologyManager.disableNewDiscussions(url);
					Thread.sleep(5000);
					//Schoology has a rate limiter
				} catch (UnirestException | InterruptedException e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
	public static void openAll() {
		ProcessHandler.spawn(() -> {
			String[] urls = Reference.PRIVACY_URLS;
			
			for(String url : urls) {
				try {
					SchoologyManager.openGroup(url);
					Thread.sleep(5000);
					//Schoology has a rate limiter
				} catch (UnirestException | InterruptedException e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
	public static void closeAll() {
		ProcessHandler.spawn(() -> {
			String[] urls = Reference.PRIVACY_URLS;
			
			for(String url : urls) {
				try {
					SchoologyManager.closeGroup(url);
					Thread.sleep(5000);
					//Schoology has a rate limiter
				} catch (UnirestException | InterruptedException e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
	/*
	 * Schoology discussion privacy functions
	 */
	
	public static void disableNewDiscussions(String link) throws UnirestException {
		String raw = VSABot.getAPI().raw(link);
		
		Document doc = Jsoup.parse(raw);
		
		String id = doc.getElementsByAttributeValue("name", "form_build_id").val();
		String token = doc.getElementsByAttributeValue("name", "form_token").val();
		String form_id = doc.getElementsByAttributeValue("name", "form_id").val();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("privacy[areas][group][areas][s_group_profile]", "school");
		map.put("privacy[areas][group][areas][s_group_profile_updates]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_calendar]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_materials]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_roster]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_discussion]", "group");
		
		map.put("other[member_options][post_updates]", "admins");
		map.put("other[member_options][post_update_comments]", "admins");
		map.put("other[member_options][create_discussions]", "admins");
		map.put("other[member_options][create_files]", "admins");
		map.put("op", "Save Changes");
		
		map.put("form_build_id", id);
		map.put("form_token", token);
		map.put("form_id", form_id);
		
		Unirest.post(link).fields(map).asString();
	}
	
	public static void enableNewDiscussions(String link) throws UnirestException {
		String raw = VSABot.getAPI().raw(link);
		
		Document doc = Jsoup.parse(raw);
		
		String id = doc.getElementsByAttributeValue("name", "form_build_id").val();
		String token = doc.getElementsByAttributeValue("name", "form_token").val();
		String form_id = doc.getElementsByAttributeValue("name", "form_id").val();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("privacy[areas][group][areas][s_group_profile]", "school");
		map.put("privacy[areas][group][areas][s_group_profile_updates]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_calendar]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_materials]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_roster]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_discussion]", "group");
		
		map.put("other[member_options][post_updates]", "admins");
		map.put("other[member_options][post_update_comments]", "admins");
		map.put("other[member_options][create_discussions]", "members");
		map.put("other[member_options][create_files]", "admins");
		map.put("op", "Save Changes");
		
		map.put("form_build_id", id);
		map.put("form_token", token);
		map.put("form_id", form_id);
		
		Unirest.post(link).fields(map).asString();
	}
	
	public static void closeGroup(String link) throws UnirestException {
		String raw = VSABot.getAPI().raw(link);
		
		Document doc = Jsoup.parse(raw);
		
		String id = doc.getElementsByAttributeValue("name", "form_build_id").val();
		String token = doc.getElementsByAttributeValue("name", "form_token").val();
		String form_id = doc.getElementsByAttributeValue("name", "form_id").val();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("privacy[areas][group][areas][s_group_profile]", "school");
		map.put("privacy[areas][group][areas][s_group_profile_updates]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_calendar]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_materials]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_roster]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_discussion]", "nobody");
		
		map.put("other[member_options][post_updates]", "admins");
		map.put("other[member_options][post_update_comments]", "admins");
		map.put("other[member_options][create_discussions]", "members");
		map.put("other[member_options][create_files]", "admins");
		map.put("op", "Save Changes");
		
		map.put("form_build_id", id);
		map.put("form_token", token);
		map.put("form_id", form_id);
		
		Unirest.post(link).fields(map).asString();
	}
	
	public static void openGroup(String link) throws UnirestException {
		String raw = VSABot.getAPI().raw(link);
		
		Document doc = Jsoup.parse(raw);
		
		String id = doc.getElementsByAttributeValue("name", "form_build_id").val();
		String token = doc.getElementsByAttributeValue("name", "form_token").val();
		String form_id = doc.getElementsByAttributeValue("name", "form_id").val();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("privacy[areas][group][areas][s_group_profile]", "school");
		map.put("privacy[areas][group][areas][s_group_profile_updates]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_calendar]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_materials]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_roster]", "group");
		map.put("privacy[areas][group][areas][s_group_profile_discussion]", "group");
		
		map.put("other[member_options][post_updates]", "admins");
		map.put("other[member_options][post_update_comments]", "admins");
		map.put("other[member_options][create_discussions]", "members");
		map.put("other[member_options][create_files]", "admins");
		map.put("op", "Save Changes");
		
		map.put("form_build_id", id);
		map.put("form_token", token);
		map.put("form_id", form_id);
		
		Unirest.post(link).fields(map).asString();
	}
}
