package com.benbeehler.vsabot.schoology;

import com.benbeehler.vsabot.resource.ChannelHandler;
import com.benbeehler.vsabot.resource.Reference;
import com.benbeehler.vsabot.schoology.instances.Comment;

import java.awt.*;

public class DiscordCommunication {

	@SuppressWarnings("static-access")
	public static void reportComment(Comment comment, double rating, boolean euphemism, boolean curse) {
		/*
		 * Check group name, ping the correct mods, report comment with specific format
		 */
		
		String roleName = "";
		Color color = Color.BLACK;
		
		if(comment.getDiscussion().getGroup().getTitle().contains("3-5")) {
			roleName = "3-5";
			color = Color.BLUE;
		} else if(comment.getDiscussion().getGroup().getTitle().contains("6-7")) {
			roleName = "6-7";
			color = Color.YELLOW;
		} else if(comment.getDiscussion().getGroup().getTitle().contains("8-9")) {
			roleName = "8-9";
			color = color.GREEN;
		} else if(comment.getDiscussion().getGroup().getTitle().contains("10-12")) {
			roleName = "10-12";
			color = color.DARK_GRAY;
		}
		
		final String rName = roleName;
		
		boolean exists = Reference.getUpdatesChannel()
				.getGuild().getRoles().stream().filter(r -> r.getName()
						.equalsIgnoreCase(rName)).findAny().isPresent();
		
		String role = "Anyone";
		
		if(exists) {
			role = Reference.getUpdatesChannel()
				.getGuild().getRoles().stream().filter(r -> r.getName()
						.equalsIgnoreCase(rName)).findAny().get().getAsMention();
		}
			
		String send = "";
		
		if(comment.getParent() == null) {
			send =  "This comment may violate discussion guidelines.\n"
						+ "**Content**: " + comment.getContent() + "\n"
						+ "**Author**: " + comment.getAuthor() + "\n"
						+ "**Time**: " + comment.getTime() + "\n"
						+ "**URL**: " + comment.getDiscussion().getUrl() + "\n"
						+ "**Discussion**: " + comment.getDiscussion().getTitle() + "\n"
						+ "**Group**: " + comment.getDiscussion().getGroup().getTitle() + "\n"
						+ "**Rating**: " + rating + "\n"
						+ "**Euphemism?**: " + String.valueOf(euphemism) + "\n"
						+ "**Swearing?**: " + String.valueOf(curse);
		} else {
			send = "This comment may violate discussion guidelines.\n"
					+ "**Content**: " + comment.getContent() + "\n"
					+ "**Author**: " + comment.getAuthor() + "\n"
					+ "**Time**: " + comment.getTime() + "\n"
					+ "**URL**: " + comment.getDiscussion().getUrl() + "\n"
					+ "**Discussion**: " + comment.getDiscussion().getTitle() + "\n"
					+ "**Group**: " + comment.getDiscussion().getGroup().getTitle() + "\n"
					+ "**Rating**: " + rating + "\n"
					+ "**Euphemism?**: " + String.valueOf(euphemism) + "\n"
					+ "**Swearing?**: " + String.valueOf(curse) + "\n"
					+ "\n"
					+ "***Parent Comment***\n"
					+ "- **Content**: " + comment.getParent().getContent() + "\n"
					+ "- **Author**: " + comment.getParent().getAuthor();
		}
		
		Reference.getUpdatesChannel().sendMessage(role).complete();	
		Reference.getUpdatesChannel().sendMessage(ChannelHandler.getEmbedded(send, comment.getAuthorPFP(), color)).complete();	
	}
	
	public static void logComment(Comment comment) {
		String send = "I am logging another comment\n"
				+ "**Content**: " + comment.getContent() + "\n"
				+ "**Author**: " + comment.getAuthor() + "\n"
				+ "**Time**: " + comment.getTime() + "\n"
				+ "**URL**: " + comment.getDiscussion().getUrl() + "\n"
				+ "**Discussion**: " + comment.getDiscussion().getTitle() + "\n"
				+ "**Group**: " + comment.getDiscussion().getGroup().getTitle();
		
		Reference.getUpdatesChannel().sendMessage(send).complete();
	}
}
