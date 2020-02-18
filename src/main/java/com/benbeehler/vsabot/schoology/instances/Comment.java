package com.benbeehler.vsabot.schoology.instances;

import com.benbeehler.vsabot.VSABot;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Comment {

	private String id;
	private String content;
	private String author;
	private String time;
	private Discussion discussion;
	private Group group;
	private String html;
	private boolean isMod;
	private Comment parent;
	
	public Comment(String html, String id, String content, String author, String time, Discussion discussion, Group group, boolean isMod, Comment parent) {
		this.html = html;
		this.id = id;
		this.content = content;
		this.author = author;
		this.time = time;
		this.discussion = discussion;
		this.group = group;
		this.isMod = isMod;
		this.parent = parent;
	}
	
	public void reply(String reply) throws UnirestException {
		VSABot.getAPI().reply(this.id, reply);
	}
	
	public void delete() throws UnirestException {
		VSABot.getAPI().delete(this.id);
	}
	
	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor() {
		return author;
	}
	
	public String getTime() {
		return time;
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public Group getGroup() {
		return group;
	}

	public String getHtml() {
		return html;
	}

	public boolean isMod() {
		return isMod;
	}
	
	public Comment getParent() {
		return this.parent;
	}
}
