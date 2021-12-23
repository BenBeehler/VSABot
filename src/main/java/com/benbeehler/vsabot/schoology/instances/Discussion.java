package com.benbeehler.vsabot.schoology.instances;

import com.benbeehler.vsabot.VSABot;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class Discussion {

	private String title;
	private Group group;
	private String html;
	private String url;
	private String id;
	
	public Discussion(String url, String html, String title, String id, Group group) throws UnirestException, IOException {
		this.group = group;
		this.html = html;
		this.url = url;
		this.title = title;
		this.group = group;
	}
	
	public void deriveHTML() throws UnirestException {
		this.html = VSABot.getAPI().raw(this.url);
	}
	
	public void delete() throws UnirestException {
		VSABot.getAPI().deleteDiscussion(this.url);
	}
	
	public void comment(String content) throws UnirestException {
		VSABot.getAPI().comment(this.getUrl(), content);
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}
	
	public String getHtml() {
		return html;
	}
	
	public String getID() {
		return this.id;
	}

	public Group getGroup() {
		return group;
	}
}
