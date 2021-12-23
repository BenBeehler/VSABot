package com.benbeehler.vsabot.schoology;

import com.benbeehler.vsabot.VSABot;
import com.benbeehler.vsabot.schoology.instances.Group;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
