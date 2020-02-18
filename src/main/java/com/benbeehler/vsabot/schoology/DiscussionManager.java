package com.benbeehler.vsabot.schoology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.benbeehler.vsabot.VSABot;
import com.benbeehler.vsabot.schoology.instances.Discussion;
import com.benbeehler.vsabot.schoology.instances.Group;
import com.benbeehler.vsabot.utilities.Parser;
import com.mashape.unirest.http.exceptions.UnirestException;

public class DiscussionManager {

	public static Discussion getDiscussion(String url, Group group) throws UnirestException, IOException {
		/*
		 * Get a discussion instance based on the URL and group
		 */
		
		//extract HTML from page
		String html = VSABot.getAPI().raw(url);
		
		Document doc = Jsoup.parse(html);
		//formulate into JSoup document and get title block
		Elements els = doc.getElementsByClass("group-area discussion");
		
		//extract title
		String title = els.text();
		
		///group/2187291458/discussion/view/2364402018
		
		String reverseURL = Parser.reverse(url);
		String[] split = reverseURL.split("/");
		String id = split[0];
				
		//build and return
		return new Discussion(url, html, title, id, group);
	}
	
	public static List<Discussion> getAllDiscussions(Group group) throws UnirestException, IOException {
		List<Discussion> discussions = new ArrayList<>();
		
		Document doc = Jsoup.parse(group.getHtml());
		
		Elements elements = doc.getElementsByClass("discussion-title");
		
		for(Element element : elements) {
			String href = element.attr("href");
			
			if(href.startsWith("/group/")) {
				String title = element.text();
				String url = "https://scholars.veritaspress.com" + href;
				
				String reverseURL = Parser.reverse(url);
				String[] split = reverseURL.split("/");
				String id = split[0];
				
				//The discussion must be instantiated this way because of Schoology's rate limiter
				Discussion discussion = new Discussion(url, "", title, id, group);
				discussions.add(discussion);
			}
		}
		
		return discussions;
	}
	
	public static List<Discussion> getUpdatedDiscussions(Group group) {
		List<Discussion> discussions = new ArrayList<>();
		
		Document doc = Jsoup.parse(group.getHtml());
		
		Elements elements = doc.getElementsByClass("discussion-unread-counter");
		
		for(Element element : elements) {
			Element parent = element.parent();
			Elements children = parent.children(); 
			
			for(Element child : children) {
				if(child.is("a")) {
					String durl = "https://scholars.veritaspress.com" + child.attr("href");
						
					try {
						Discussion discussion = DiscussionManager.getDiscussion(durl, group);
						discussions.add(discussion);
					} catch (UnirestException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return discussions;
	}
}
