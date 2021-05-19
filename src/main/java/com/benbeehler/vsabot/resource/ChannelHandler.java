package com.benbeehler.vsabot.resource;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;


public class ChannelHandler {

	/*
	 * More discord abstraction
	 */
	
	public static Message sendEmbeded(MessageChannel channel, String message, Color color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription(message);
		eb.setColor(color);
		channel.sendTyping().queue();
		Message mess = channel.sendMessage(eb.build()).complete();

		return mess;
	}

	public static MessageEmbed getEmbedded(String message, Color color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription(message);
		eb.setColor(color);

		return eb.build();
	}
	
	public static MessageEmbed getEmbedded(String message, String imgUrl, Color color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription(message);
		eb.setColor(color);
		eb.setImage(imgUrl);

		return eb.build();
	}

	public static Message sendEmbeded(PrivateChannel channel, String message, Color color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription(message);
		eb.setColor(color);
		channel.sendTyping().queue();
		Message mess = channel.sendMessage(eb.build()).complete();

		return mess;
	}

	public static Message sendEmbeded(TextChannel channel, String message, Color color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription(message);
		eb.setColor(color);
		channel.sendTyping().queue();
		Message mess = null;
		
		if(channel.canTalk()) {
			mess = channel.sendMessage(eb.build()).complete();
		}
		
		return mess;
	}
}
