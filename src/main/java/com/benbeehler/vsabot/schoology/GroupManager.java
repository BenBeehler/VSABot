package com.benbeehler.vsabot.schoology;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.benbeehler.vsabot.VSABot;
import com.benbeehler.vsabot.schoology.instances.Group;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GroupManager {

	public static Group getGroup(String url) throws UnirestException {
		String html = VSABot.getAPI().raw(url);
			
		Document doc = Jsoup.parse(html);
		String title = doc.title()
				.replace("| Schoology", "")
				.replace("- Discussions", "").trim(); //get title
			
		return new Group(url, html, title);
	}
	
}
