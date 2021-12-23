package com.benbeehler.vsabot.schoology;

import com.benbeehler.vsabot.resource.OCRAPI;
import com.benbeehler.vsabot.schoology.instances.Comment;
import com.benbeehler.vsabot.schoology.instances.Discussion;
import com.benbeehler.vsabot.utilities.Parser;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentManager {

	/*
	 * Schoology Comment Handler
	 * 
	 * Observe:
	 * getComment()
	 * getNewComments()
	 * getAllComments()
	 * 
	 */
	
	public static Comment getComment(Element element, Discussion discussion) {
		/*
		 * Extract meaningful information from the HTML 
		 */
		
		String id = element.id().replace("comment-", "");
		String html = element.html();
		
		String content = element.text();
		
		content = Jsoup.parse(content.replace("Like 's comment · Reply", "")
				.replaceFirst("Delete", "").trim()).text();
		
		String author = Parser.getCommentAuthor(content);
		String time = Parser.time(content, author);
		
		final Document doc = Jsoup.parse(html);
		
		String pfpImg = "";
		
		Elements e = doc.getElementsByClass("imagecache");
		if(e.size() > 0) {
			pfpImg = e.get(0).attr("src");
		}
		
		boolean isMod = doc.getElementsByClass("is-admin").size() > 0;
		
		content = Parser.purify(content);
		
		
		/*
		 * Get parent comment 
		 */
		
		Comment parent = null;
		
		Optional<Element> optional = element.parent().parent().children().stream()
				.filter(c -> c.className().contains("s-js-comment-wrapper")).findAny();
		
		
		if(optional.isPresent()) {
			Element masterElement = optional.get();
			
			if(masterElement.html().trim() != html.trim()) {
				//avoid unnecessary recursion
				//parent = CommentManager.getComment(masterElement, discussion);
			}
		}
		
		Comment comment = new Comment(html, id, content, author, time, discussion, discussion.getGroup(), isMod, parent);
		comment.setAuthorPFP(pfpImg);
		
		return comment;
	}
	
	public static List<Comment> getNewComments(Discussion discussion) throws IOException {
		List<Comment> list = new ArrayList<>();
		
		/*
		 * Parse document and extract all comment element divs
		 */
		
		Document doc = Jsoup.parse(discussion.getHtml());
		
		Elements elts = doc.getElementsByClass("comment");
		
		Iterator<Element> iterator = elts.iterator();
		
		while(iterator.hasNext()) {
			//Iterate through each div and find the element
			Element element = iterator.next();
			
			if(element.hasClass("unread")) {
				//Check if it's unread, build comment, add to list
				
				Comment comment = CommentManager.getComment(element, discussion);
				list.add(comment);
			}
		}
		
		return list;
	}
	
	public static List<Comment> getAllComments(Discussion discussion) throws IOException {
		List<Comment> list = new ArrayList<>();
		
		Document doc = Jsoup.parse(discussion.getHtml());
		
		Elements elts = doc.getElementsByClass("comment");
		
		Iterator<Element> iterator = elts.iterator();
		
		while(iterator.hasNext()) {
			//same process as above but it doesn't limit to unread comments
			Element element = iterator.next();
				
			Comment comment = CommentManager.getComment(element, discussion);
			list.add(comment);
		}
		
		return list;
	}
	
	public static String getImagesContent(Comment comment) {
		Document doc = Jsoup.parse(comment.getHtml());
		
		List<Element> list = doc.getElementsByAttribute("href")
				.stream().filter(e -> e.attr("href").startsWith("https://asset"))
				.collect(Collectors.<Element>toList());
		
		StringBuilder builder = new StringBuilder();
		
		for(Element e : list) {
			String link = e.attr("href");
			
			try {
				String extracted = OCRAPI.readImage(link);
				
				builder.append(extracted + " ");
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}
		}
		
		return builder.toString().trim();
	}
	
}
