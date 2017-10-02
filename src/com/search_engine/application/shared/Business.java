package com.search_engine.application.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Business implements Serializable
{
	private String name;
	private String fullAddress;
	private String city;
	private String state;
	private String category;
	private int reviewCount;
	private String stars;
	private double longitude;
	private double latitude;
	private String businessId;
	private HashMap<String,ArrayList<String>> checkIns;
	private String chk;
	
	public void construct(String name, String fullAddress, String city, String state, String category, int reviewCount,
			String stars, double longitude, double latitude, String businessId, String checkIn) {
		this.name = name;
		this.fullAddress = fullAddress;
		this.city = city;
		this.state = state;
		this.category = category;
		this.reviewCount = reviewCount;
		this.stars = stars;
		this.longitude = longitude;
		this.latitude = latitude;
		this.businessId = businessId;
		checkIns = new HashMap<>();
		chk = checkIn;
	}
	
	public String getName() {
		return name;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public String getCity() {
		if(city.equals(""))
		{
			return "Other";
		}
		return city;
	}

	public String getState() {
		if(state.equals(""))
		{
			return "Other";
		}
		return state;
	}

	public String getCategory() {
		return category;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public String getStars() {
		return stars;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public String getBusinessId() {
		return businessId;
	}
	
	public ArrayList<String> values() {
		ArrayList<String> toBuild = new ArrayList<String>();
		toBuild.add(name);
		toBuild.add(fullAddress);
		toBuild.add(city);
		toBuild.add(state);
		toBuild.add(reviewCount+"");
		toBuild.add(stars);
		toBuild.add(longitude+"");
		toBuild.add(latitude+"");
		
		return toBuild;
	}
	
	public void addCheckIn(HashMap<String,ArrayList<String>> byHour) {
		 checkIns = byHour;
	 }
	 

	@Override
	public String toString() {
		return "Business [name=" + name + ", fullAddress=" + fullAddress + ", city=" + city + ", state=" + state
				+ ", category=" + category + ", reviewCount=" + reviewCount + ", stars=" + stars + ", longitude="
				+ longitude + ", latitude=" + latitude + ", businessId=" + businessId + ", checkIns=" + checkIns + "]";
	}

	public HashMap<String,ArrayList<String>> getCheckIns() {  
		return checkIns;
	}
	
	public String checkInsToString()
	{
		String output="";
		for(String i : checkIns.keySet())
		{
			output += i+"-"+checkIns.get(i).toString()+"/";
		}
		return output;
	}

	public String getChk() {
		return chk;
	}
	
	public Double getInfoToSort(int sortBy)
	{
		if(sortBy == 0)
		{
			return Double.parseDouble(getStars());
		}
		else{
			return getReviewCount()+0.0;
		}
	}
}
