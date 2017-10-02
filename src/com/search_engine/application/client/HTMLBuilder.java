package com.search_engine.application.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.Review;

public class HTMLBuilder {
	private HashMap<Integer,Business> searchResults;
	private HashMap<Integer,Review> reviewResults;
	private static final String[] KEYS = {"", "Address", "City", "ReviewCount"};
	private List<String> printList;


	public void buildSearchResults(HashMap<Integer,Business> searchResults) {
		this.searchResults = searchResults;
	}
	
	public void buildReviewResults(HashMap<Integer,Review> reviewResults)
	{
		this.reviewResults = reviewResults;
	}
	
	public HTML toHTML(int id)
	{
		ArrayList<String> toBuild;
		if(searchResults != null)
		{
			toBuild = searchResults.get(id).values();
		}
		else
		{
			toBuild = new ArrayList<String>(printList);
		}
		SafeHtmlBuilder builder = new SafeHtmlBuilder();

		builder.appendHtmlConstant("<div id=\"titleStars\">");

		builder.appendHtmlConstant("<div class=\"businessName\">");
		if(toBuild.get(0).length() >= 21)
		{
			builder.appendHtmlConstant("<marquee behavior=\"alternate\" scrollamount=\"1\">");
			builder.appendEscaped(toBuild.get(0));
			builder.appendHtmlConstant("</marquee>");
		} else {
			builder.appendEscaped(toBuild.get(0));
		}
		builder.appendHtmlConstant("</div>");
		
		builder.appendHtmlConstant("<div class=\"star-ratings-sprite\"><span style=\"width:");
		double width = Double.parseDouble(toBuild.get(5));
		double w = width*100/5;
		builder.appendEscaped(""+w);
		builder.appendHtmlConstant("%\" class=\"star-ratings-sprite-rating\"></span></div>");
		builder.appendHtmlConstant("</div>");

		
		builder.appendHtmlConstant("<div class=\"titles\">");
		for(int i=1; i<KEYS.length; i++)
		{
			builder.appendHtmlConstant("<div class=\"");
			builder.appendEscaped(KEYS[i]);
			builder.appendHtmlConstant("\"></div>");
		}
		builder.appendHtmlConstant("</div>");
		
		builder.appendHtmlConstant("<div class=\"text\">");
		for(int i=1; i<KEYS.length+1; i++)
		{
			if(KEYS[i].equals("Address"))
			{
				String splitAddress = toBuild.get(i).replace(toBuild.get(2), "");
				splitAddress = splitAddress.replace(toBuild.get(3), "");
				if(splitAddress.substring(0, 1).contains(",")){
					builder.appendEscaped(toBuild.get(i).replace(toBuild.get(3), ""));
				} else {
					if (splitAddress.length() > 49) {
						builder.appendHtmlConstant("<marquee behavior=\"alternate\" scrollamount=\"1\">");
						builder.appendEscaped(splitAddress);
						builder.appendHtmlConstant("</marquee>");
					} else {
						builder.appendEscaped(splitAddress.replace(",", ""));
					}
				}
			} else {
				builder.appendEscaped(toBuild.get(i));
			}
			if(!KEYS[i].equals("City")) {
				builder.appendHtmlConstant("<br>");
			} else {
				builder.appendEscaped(", ");
			}
		}
		builder.appendHtmlConstant("</div>");

		return new HTML(builder.toSafeHtml());
	}

	public double getLongitude(int id) {
		return searchResults.get(id).getLongitude();
	}

	public double getLatitude(int id) {
		return searchResults.get(id).getLatitude();
	}
	
	public String getName(int id)
	{
		return searchResults.get(id).getName();
	}
	
	public HTML reviewsToHTML(int id)
	{
		SafeHtmlBuilder builder = new SafeHtmlBuilder();

		builder.appendHtmlConstant("<div class=\"reviewsHtml\">");
		builder.appendHtmlConstant("<div class=\"starsDate\">");
		builder.appendHtmlConstant("<div class=\"date\">");
		builder.appendEscaped(reviewResults.get(id).getDate());	
		builder.appendHtmlConstant("</div>");
		builder.appendHtmlConstant("<div class=\"stars\">");
		builder.appendHtmlConstant("<div class=\"star-ratings-sprite\"><span style=\"width:");
		double width = Double.parseDouble(reviewResults.get(id).getStars());
		double w = width*100/5;
		builder.appendEscaped(""+w);
		builder.appendHtmlConstant("%\" class=\"star-ratings-sprite-rating\"></span></div>");
		builder.appendHtmlConstant("</div>");
		builder.appendHtmlConstant("</div>");
		
		builder.appendHtmlConstant("<br>");
		builder.appendEscaped(reviewResults.get(id).getText());
		builder.appendHtmlConstant("<br>");
		builder.appendHtmlConstant("</div>");

		return new HTML(builder.toSafeHtml());
	}

}
