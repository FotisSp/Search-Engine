package com.search_engine.application.server;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.json.JSONException;

import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.Review;
import com.search_engine.application.shared.RootCategories;
import com.search_engine.application.shared.SearchArguments;

public class SearchEngine {

	private final static int MAXRESULTS = 1500;
	private Searcher search;
	private TopDocs topDocs;
	private SearchArguments searchTerms;
	private HashMap<Integer, Business> map;
	private ScoreDoc[] hits;

	public HashMap<Integer, Business> searchTerm(SearchArguments searchTerms) throws IOException, ParseException, JSONException, org.apache.lucene.queryparser.classic.ParseException
	{
		this.searchTerms = searchTerms;
		search = new Searcher();

		topDocs = search.performSearch(searchTerms.getSearchArgument());

		hits = topDocs.scoreDocs;

		map = new HashMap<>();
		float firstScore = findFirstScore();

		int size = hits.length;
		if(size > MAXRESULTS)
		{
			size = MAXRESULTS;
		}
		for (int i = 0; i < size; i++) {
			if ((hits[i].score / firstScore) > 0.7) {
				Document doc = search.getDocument(hits[i].doc);
				String name = doc.get("Name");
				String address = doc.get("Full Address");
				String city = doc.get("City");
				String state = doc.get("State");
				int reviewCount = Integer.parseInt(doc.get("Review Count"));
				String stars = doc.get("Stars");
				String categories = doc.get("Categories");
				double longitude = Double.parseDouble(doc.get("Longitude"));
				double latitude = Double.parseDouble(doc.get("Latitude"));
				String businessId = doc.get("Business Id");
				String checkIns = doc.get("CheckIns");
				Business info = new Business();
				info.construct(name, address, city, state, categories, reviewCount, stars, longitude, latitude, businessId,checkIns);
				map.put(i, info);
			}
		}
		return map;
	}
	
	private float findFirstScore() throws IOException
	{
		String term = searchTerms.getSearchArgument();
		for(int i=0; i<hits.length; i++)
		{
			Document doc = search.getDocument(hits[i].doc);
			if(doc.get("Name").equalsIgnoreCase(term) || doc.get("Full Address").contains(" "+term+" ") || doc.get("City").equalsIgnoreCase(term) || doc.get("State").equalsIgnoreCase(term))
			{
				return hits[i].score;
			}
		}
		return hits[0].score;
	}

	public HashMap<Integer,Review> reviewSearch(String businessId) throws IOException, org.apache.lucene.queryparser.classic.ParseException 
	{
		search = new Searcher(businessId);

		topDocs = search.performReviewSearch(businessId);
		if(topDocs == null)
		{
			return null;
		}
		hits = topDocs.scoreDocs;

		HashMap<Integer,Review> reviewMap = new HashMap<>();

		for (int i = 0; i < hits.length; i++) {
			Document doc = search.getReviewDocument(hits[i].doc);
			Review review = new Review();

			String text = doc.get("Text");
			String stars = doc.get("Stars");
			String date = doc.get("Date");
			String business_id = doc.get("Business Id");
			review.constructReview(text, date, stars, business_id);
			reviewMap.put(i, review);
		}
		return reviewMap;
	}

	public HashMap<Integer, Business> filter() throws IOException
	{
		HashMap<Integer,Business> results = new HashMap<>();
		List<String> cityList = new ArrayList<String>();
		List<String> stateList = new ArrayList<String>();
		List<String> starsList = new ArrayList<String>();
		List<String> categoriesList = new ArrayList<String>();
		List<String> printList = new ArrayList<String>();

		for (int i = 0; i < map.size(); i++) 
		{
			for (String filter : searchTerms.getFilters()) 
			{		
				if (map.get(i).getCity().contains(filter) && !cityList.contains(map.get(i).toString())) {
					cityList.add(map.get(i).toString());
				} else if (map.get(i).getState().contains(filter) && !stateList.contains(map.get(i).toString())) {
					stateList.add(map.get(i).toString());
				} else if (map.get(i).getStars().contains(filter) && !starsList.contains(map.get(i).toString())) {
					starsList.add(map.get(i).toString());
				} else if (map.get(i).getCategory().contains(filter) && !categoriesList.contains(map.get(i).toString())) {
					categoriesList.add((map.get(i).toString()));
				} 
			}
		}

		if (!cityList.isEmpty()) {
			printList = cityList;
			if (!stateList.isEmpty()) {
				cityList.retainAll(stateList);
				printList = cityList;
			} 
		}
		else if(!stateList.isEmpty()) {
			printList = stateList;
		}
		if(!starsList.isEmpty()) {
			if(!printList.isEmpty()) {
				printList.retainAll(starsList);
			} else {
				printList = starsList;
			}
		}
		if(!categoriesList.isEmpty()) {
			if(!printList.isEmpty()) {
				printList.retainAll(categoriesList);
			} else {
				printList = categoriesList;
			}
		}

		if (!printList.isEmpty()) {
			for (int i = 0; i < map.size(); i++) {
				for (int j = 0; j < printList.size(); j++) {	
					String[] cityName = printList.get(j).split("city=");
					String[] city = cityName[1].split(",");
					String[] stateName = cityName[1].split("state=");
					String[] state = stateName[1].split(",");
					String[] categoriesName= stateName[1].split("category=");
					String[] categories = categoriesName[1].split("]");
					categories[0] += "]";
					String[] starsValue = categories[1].split("stars=");
					String[] stars = starsValue[1].split(",");

					if (map.get(i).getCity().contains(city[0]) && map.get(i).getState().contains(state[0]) && map.get(i).getStars().contains(stars[0]) && map.get(i).getCategory().contains(categories[0])) {
						results.put(j, map.get(i));
					}
				}
			} 
		}else {
			return map;
		}
		return results;
	}

	public List<RootCategories> createFacets(HashMap<Integer,Business> map)
	{
		List<RootCategories> root = new ArrayList<>(); 

		RootCategories city = new RootCategories();
		city.setName("City");
		RootCategories state = new RootCategories();
		state.setName("State");
		RootCategories stars = new RootCategories();
		stars.setName("Stars");
		RootCategories categories = new RootCategories();
		categories.setName("Categories");
		categories.construct();

		for(int i=0; i<map.size(); i++)
		{
			String cityName = map.get(i).getCity();
			city.containsChild(cityName);
			String stateName = map.get(i).getState();
			state.containsChild(stateName);
			String starName = map.get(i).getStars();
			stars.containsChild(starName);

			String categoriesName = map.get(i).getCategory();
			categoriesName = categoriesName.replaceAll("[\"\\[\\]]", "");
			String[] categoriesItems = categoriesName.split(",");
			for(int j=0; j<categoriesItems.length; j++)
			{
				categories.containsCategory(categoriesItems[j]);
			}
		}
		categories.setCategories(categories.removeEmpty());

		root.add(city);
		root.add(state);
		root.add(stars);
		root.add(categories);
		return root;
	}
}
