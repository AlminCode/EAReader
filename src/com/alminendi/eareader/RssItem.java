package com.alminendi.eareader;

/**
 * A representation of an rss item from the list.
 * 
 * @author Veaceslav Grec
 * 
 */
public class RssItem {

	public  String title;
	public  String link;

	public RssItem(String title, String link) {
		this.title = title;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}
	
	@Override
	public String toString() {
	    return this.title + "\n" + "("+ this.link +")"; 
	}
}
