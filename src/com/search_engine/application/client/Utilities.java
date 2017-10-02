package com.search_engine.application.client;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.events.click.ClickMapEvent;
import com.google.gwt.maps.client.events.click.ClickMapHandler;
import com.google.gwt.maps.client.overlays.Animation;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.maps.client.overlays.MarkerOptions;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.search_engine.application.click_handlers.BoxClickHandler;
import com.search_engine.application.click_handlers.ReviewClickHandler;
import com.search_engine.application.shared.Business;

public class Utilities 
{

	private static final int NUMBEROFCARDS = 12;
	private int pageId = 0;
	private Panels panel;
	private int size;
	private Marker[] markerDrop;
	private FlowPanel numOfResults;
	private FlowPanel buttons;
	private String searchTerm;
	private Button sortButton;
	private HTMLBuilder builder = new HTMLBuilder();
	private HashMap<Integer, Business> searchResults;
	private HashMap<Integer, Business> orderedResults = new HashMap<>();
	private int sortBy;

	public Utilities(Panels panel, int size, String searchTerm, HashMap<Integer,Business> searchResults)
	{
		this.panel = panel;
		this.size = size; 
		markerDrop = panel.getMarkerDrop();
		numOfResults = panel.getNumOfResults();
		buttons = panel.getButtons();
		this.searchTerm = searchTerm;
		this.searchResults = searchResults;
		orderedResults = searchResults;
	}

	public void nextPage(HashMap<Integer, Business> searchResults,
			final FlowPanel resultsPanel ) {
		if ((pageId+1)*NUMBEROFCARDS < searchResults.size()) {
			resultsPanel.clear();
			pageId++;
			loadSearchResultBoxes(searchResults, resultsPanel, pageId);
			pageButtons();
		}
	}

	public void previousPage(HashMap<Integer, Business> searchResults,
			final FlowPanel resultsPanel) {
		if(pageId > 0) {
			resultsPanel.clear();
			pageId--;
			loadSearchResultBoxes(searchResults, resultsPanel, pageId);
			pageButtons();
		}
	}

	public void loadSearchResultBoxes(HashMap<Integer, Business> searchResults,
			final FlowPanel resultsPanel, int page) 
	{
		int limit;

		builder.buildSearchResults(searchResults);
		resultsPanel.clear();
		numOfResults.clear();
		int startC = (pageId*NUMBEROFCARDS)+1;
		if(searchResults.size() == 0) {
			startC = 0;
		}
		int endC = (pageId+1)*NUMBEROFCARDS;
		if(endC > searchResults.size()) {
			endC = searchResults.size();
		}
		numOfResults.add(new HTML(startC+" - "+endC+" of "+searchResults.size()+" results for : <span style=\"color:orange;font-size:15px;\">\""+searchTerm+"\"</span>"));
		numOfResults.setStyleName("totalResults");

		if(searchResults.size() >= ((page*NUMBEROFCARDS)+NUMBEROFCARDS))
		{
			limit = (page*NUMBEROFCARDS)+NUMBEROFCARDS;
		} else {
			limit = searchResults.size();
		}

		for(int i=page*NUMBEROFCARDS; i<limit; i++)
		{
			HTML box = builder.toHTML(i);

			FlowPanel functions = new FlowPanel();
			functions.setStyleName("functions");

			FlowPanel locateButton = new FlowPanel();
			final BoxClickHandler clkHandler = new BoxClickHandler(searchResults.get(i).getLatitude(),searchResults.get(i).getLongitude(),i,searchResults.get(i).getName(),panel,searchResults.size());	  
			Button locateOnMap = new Button("", new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					clkHandler.onClick(event);
					panel.getScrollPanel().scrollToTop();
				}
			});
			locateOnMap.setStyleName("locateOnMap hvr-buzz-out");
			HTML hoverTextLocate = new HTML("<span class=\"revHover\">Show on Map</span>");
			locateButton.add(hoverTextLocate);
			locateButton.add(locateOnMap);
			locateButton.setStyleName("locateButton");

			FlowPanel revButton = new FlowPanel();
			final ReviewClickHandler revClkHandler = new ReviewClickHandler(searchResults.get(i));
			Button reviewButton = new Button("",new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					revClkHandler.onClick(event);
				}
			});
			reviewButton.setStyleName("reviewButton hvr-buzz-out");
			HTML hoverText = new HTML("<span class=\"revHover\">Show Reviews</span>");
			revButton.add(hoverText);
			revButton.add(reviewButton);
			revButton.setStyleName("revButton");

			functions.add(revButton);
			functions.add(locateButton);

			FlowPanel rPanel = new FlowPanel();
			rPanel.add(box);
			rPanel.add(functions);
			rPanel.setStyleName("resultBox");
			resultsPanel.add(rPanel);
		}
	}

	public void showMap(){		
		for(int i=0; i<size; i++)
		{
			LatLng markerCenter = LatLng.newInstance(builder.getLatitude(i), builder.getLongitude(i));
			MarkerOptions options = MarkerOptions.newInstance();
			options.setPosition(markerCenter);
			options.setAnimation(Animation.DROP);
			final String name = builder.getName(i);
			final int markerID = i;

			markerDrop[i] = Marker.newInstance(options);
			markerDrop[i].setMap(panel.getMapWidget());

			final BoxClickHandler clkHandler = new BoxClickHandler(panel,size,builder.getLatitude(i), builder.getLongitude(i));	  
			markerDrop[i].addClickHandler(new ClickMapHandler() {
				@Override
				public void onEvent(ClickMapEvent event) {
					clkHandler.drawInfoWindow(markerDrop[markerID], name,markerID);
				}
			});
		}
	}

	public void pageButtons()
	{
		buttons.clear();
		if(searchResults.size() > NUMBEROFCARDS)
		{
			Button forwardButton = new Button("Next Page",new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					nextPage(orderedResults, panel.getResultsPanel());
				}
			});

			HTML pageNumber = new HTML(pageId+1+"");
			pageNumber.setStyleName("pageNumber");

			Button previousButton = new Button("Previous Page", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					previousPage(orderedResults, panel.getResultsPanel());
				}
			});
			previousButton.setStyleName("pageButtonBack hvr-icon-back");
			forwardButton.setStyleName("pageButtonForward hvr-icon-forward");

			buttons.add(previousButton);
			buttons.add(pageNumber);
			buttons.add(forwardButton);
			buttons.setStyleName("buttonsStyle");
			panel.getCenterContainer().add(buttons);
		}
	}
	
	public void sortButton(final HashMap<Integer, Business> searchResults)
	{
		panel.getSortButtonPanel().clear();
		final HashMap<Integer, Business> unsortedResults = this.searchResults;
		sortButton = new Button("", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String buttonName = panel.getSortButtonPanel().getStyleName();

				HashMap<Integer, Business> sorted;
				if(buttonName.contains("none"))
				{
					buttonName = buttonName.replace("none", "asc");
					sorted = sortResults(searchResults,1);
				} 
				else if (buttonName.contains("asc"))
				{
					buttonName = buttonName.replace("asc", "dec");
					sorted = sortResults(searchResults,2);
				}
				else {
					buttonName = buttonName.replace("dec", "none");
					sorted = sortResults(unsortedResults,0);
				}
				orderedResults = sorted;
				loadSearchResultBoxes(sorted, panel.getResultsPanel(), pageId);

				panel.getSortButtonPanel().setStyleName(buttonName);
			}
		});
		
		final ListBox sortListBox = new ListBox();
		
		sortListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				sortBy = sortListBox.getSelectedIndex();
				String buttonName = panel.getSortButtonPanel().getStyleName();
				HashMap<Integer, Business> sorted;
				if(buttonName.contains("none"))
				{
					sorted = sortResults(searchResults,0);
				} 
				else if (buttonName.contains("asc"))
				{
					sorted = sortResults(searchResults,1);
				}
				else {
					sorted = sortResults(unsortedResults,2);
				}
				orderedResults = sorted;
				loadSearchResultBoxes(sorted, panel.getResultsPanel(), pageId);

				panel.getSortButtonPanel().setStyleName(buttonName);
			}
		});
		
		sortListBox.addItem("Stars");
		sortListBox.addItem("Review Count");
		String html = "<span></span>";
		sortButton.setHTML(html);
		sortButton.setStyleName("sort-button");
		panel.getSortButtonPanel().add(sortButton);
		panel.getSortButtonPanel().add(sortListBox);
	}
	
	public HashMap<Integer, Business> sortResults(HashMap<Integer, Business> searchResults, int order)
	{
		Business[] unsortedResults = new Business[searchResults.size()];
		
		for(Integer id: searchResults.keySet())
		{
			unsortedResults[id] = searchResults.get(id);
		}
		
		Business temp;
		
		for(int i=0; i<unsortedResults.length; i++)
		{
			for(int j=1; j<unsortedResults.length-i; j++)
			{
				if(order == 1)
				{
					if(unsortedResults[j-1].getInfoToSort(sortBy) > unsortedResults[j].getInfoToSort(sortBy))
					{
						temp = unsortedResults[j-1];
						unsortedResults[j-1] = unsortedResults[j];
						unsortedResults[j] = temp;
					}
				} 
				else if (order == 2) 
				{
					if(unsortedResults[j-1].getInfoToSort(sortBy) < unsortedResults[j].getInfoToSort(sortBy)) 
					{
						temp = unsortedResults[j-1];
						unsortedResults[j-1] = unsortedResults[j];
						unsortedResults[j] = temp;
					}
				} else {
					return searchResults;
				}
			}
		}
		
		HashMap<Integer,Business> sortedMap = new HashMap<>();
		for(int i=0; i<unsortedResults.length; i++)
		{
			sortedMap.put(i, unsortedResults[i]);
		}

		return sortedMap;
	}
}
