package com.search_engine.application.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Review implements Serializable{
	private String text;
	private String date;
	private String stars;
	private String businessId;
	
	public void constructReview(String text, String date, String stars, String businessId) {
		this.text = text;
		this.date = date;
		this.stars = stars;
		this.businessId = businessId;
	}

	public String getText() {
		return text;
	}

	public String getDate() {
		return date;
	}

	public String getStars() {
		return stars;
	}

	public String getBusinessId() {
		return businessId;
	}

	@Override
	public String toString() {
		return "Review [text=" + text + ", date=" + date + ", stars=" + stars + ", businessId=" + businessId + "]";
	}
}
