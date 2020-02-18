package com.benbeehler.vsabot.schoology.instances;

public class Group {

	private String title;
	private String url;
	private String html;
	
	public Group(String url, String html, String title) {
		this.url = url;
		this.html = html;
		this.title = title;
	}

	public String getHtml() {
		return html;
	}

	public String getTitle() {
		return title;
	}
	
	public String getURL() {
		return url;
	}
}
