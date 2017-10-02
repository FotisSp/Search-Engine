package com.search_engine.application.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.TreeViewModel;
import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.FieldVerifier;
import com.search_engine.application.shared.RootCategories;
import com.search_engine.application.shared.SearchArguments;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.DOM;

import java.util.HashMap;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class SearchEngine implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";
	private static final String FILE_NOT_FOUND = "File not found! Please check your files and try again.";

	private static final int ATTEMPTS = 5;
	private static final int TOTALPROCESSES = 2435673;
	private static final int NOCONNECTION = -1;

	private static GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private final static Label searchBoxLabel = new Label();
	private final static Button sendButton = new Button("Send");
	private static InputElement searchTerm;
	private static MapWidget mapWidget;
	private static Marker markerDrop[];
	private static InfoWindow iw[];
	private static RootPanel searchBox;
	private static FlowPanel centerPanel = new FlowPanel();
	private static DockLayoutPanel dock = new DockLayoutPanel(Unit.PCT);
	private static FlowPanel buttons = new FlowPanel();
	private static int pageId = 0;
	private static Utilities utilities;
	private static PopUpDialogs popUpDialogs = new PopUpDialogs();
	private static ScrollPanel scrollPanel = new ScrollPanel();
	private static String searchKeyWord;
	private static Button clear;
	private static VerticalPanel resultsAndButtonsContainer = new VerticalPanel();

//	Logger logger = Logger.getLogger("<Name of the logger>");
//	logger.log(Level.SEVERE, "<Message to be logged");

	public void onModuleLoad()
	{
		searchBox = RootPanel.get("wrap");
		RootLayoutPanel root = RootLayoutPanel.get();
		dock.addNorth(searchBox, 20);

		dock.setStyleName("cw-DockPanel");
		greetingService.checkIndex(new AsyncCallback<Void>()
		{
			@Override
			public void onFailure(Throwable caught) {
				popUpDialogs.createErrorBox(FILE_NOT_FOUND, "Server Error - File Not Found","Server error:");
			}

			@Override
			public void onSuccess(Void result) {
				popUpDialogs.destroyProgress();
				startSearch();
				registerFunctions();
			}
		});

		popUpDialogs.createProgressBox();
		greetingService.getProgress(new AsyncCallback<Integer>()
		{
			int attempts = 0;
			int stage = 0;
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Integer result) {
				if(attempts < ATTEMPTS && result < TOTALPROCESSES && result != NOCONNECTION)
				{	// TODO MAGIC NUMBERS FIX THAT!
					if(result > 73445 && result < 77445 && stage == 0)			
					{
						popUpDialogs.setBusinessProgress();
						stage++;
					}
					else if (result > 129014 && result < 133014 && stage == 1)
					{
						popUpDialogs.setCheckInProgress();
						stage++;
					}
					else if (result > 205459 && result < 209459 && stage == 2)
					{
						popUpDialogs.setIndexProgress();
						stage++;
					}
					else if (result > 2430672 && result < 2434672 && stage == 3)
					{
						popUpDialogs.setReviewsProgress();
						stage++;
					}
					else if (result >= 2434672 && stage == 4)
					{
						popUpDialogs.setDoneProgress();
						stage++;
					}
					popUpDialogs.setPercentage(result);
					greetingService.getProgress(this);

				}
				else if(attempts < ATTEMPTS && result == NOCONNECTION)
				{
					// Still trying to connect
					attempts++;
					greetingService.getProgress(this);
				}
			}
		});

		scrollPanel.add(dock);
		root.add(scrollPanel);
	}

	public static native void info(String msg) /*-{
	   $wnd.alert(msg);
	}-*/;

	public static native void registerFunctions() /*-{
		$wnd.startSearch=$entry(@com.search_engine.application.client.SearchEngine::startSearch());
	}-*/;

	public static void startSearch()
	{
		searchTerm =(InputElement)(Element)DOM.getElementById("nameFieldContainer");
		searchKeyWord = searchTerm.getValue();
		greetingService.emptyFilters(new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Void result) {
				pageId = 0;
				loadSearchBox();
				searchTerm.blur();
				searchTerm.setValue("");
			}
    	});
	}

	public static void loadPage(HashMap<Integer,Business> searchResults)
	{
		dock.setHeight("1800px");

		final HashMap<Integer,Business> res = searchResults;
		greetingService.getFacetsMap(new AsyncCallback<List<RootCategories>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<RootCategories> result) {
				loadCellTrees(res,result);
				mapWidget.triggerResize();
			}
		});
	}

	public static void loadCellTrees(HashMap<Integer,Business> searchResults, List<RootCategories> list)
	{
		final List<RootCategories> myList = list;
		final HashMap<Integer,Business> myResults = searchResults;
		final FlowPanel resultsPanel = new FlowPanel();

		VerticalPanel centerContainer = new VerticalPanel();
		centerContainer.setStyleName("centerContainer");
		FlowPanel northContainer = new FlowPanel();
		northContainer.setStyleName("northContainer");

		VerticalPanel westTrees = new VerticalPanel();
	    markerDrop = new Marker[searchResults.size()];
		iw = new InfoWindow[searchResults.size()];

		LatLng center = LatLng.newInstance(35.951330, -3.867188);
	    MapOptions opts = MapOptions.newInstance();
	    opts.setZoom(3);
	    opts.setCenter(center);
	    opts.setMapTypeId(MapTypeId.ROADMAP);

	    FlowPanel numOfResults = new FlowPanel();
	    FlowPanel sortResultsContainer = new FlowPanel();
	    FlowPanel sortIcon = new FlowPanel();
	    sortIcon.setStyleName("sortIcon");
	    sortResultsContainer.setStyleName("sortResultsContainer");

	    FlowPanel sortButtonPanel = new FlowPanel();
	    sortButtonPanel.setStyleName("sortButtonPanel none");
	    sortResultsContainer.add(sortIcon);
	    sortResultsContainer.add(sortButtonPanel);
	    sortResultsContainer.add(numOfResults);

	    clear = new Button("Clear Filters", new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	clearButtonAction(resultsPanel,myResults.size());
	        	loadCellTrees(myResults,myList);
	        	utilities.showMap();
	        }
	      });
	    clear.setStyleName("clearButton");
	    mapWidget = new MapWidget(opts);
	    Panels panels = new Panels(resultsPanel,mapWidget,markerDrop,iw,buttons, numOfResults, scrollPanel, resultsAndButtonsContainer, sortButtonPanel);
		TreeViewModel treeModel = new CellTreeWidget(list,panels, searchKeyWord);
		final CellTree tree = new CellTree(treeModel, null);
		tree.setAnimationEnabled(true);
		tree.addOpenHandler(new OpenHandler<TreeNode>() {

			@Override
			public void onOpen(OpenEvent<TreeNode> event) {
				for(int i=0;i<3;i++)
				{
					if(tree.getRootTreeNode().isChildOpen(i))
					{
						tree.getRootTreeNode().setChildOpen(i, !tree.getRootTreeNode().isChildOpen(i+1));
					}
				}
			}
		});

		tree.setStyleName("tree");
		tree.setAnimationEnabled(true);

		dock.clear();
		centerPanel.clear();
		buttons.clear();
		westTrees.clear();
		westTrees.add(tree);
		resultsAndButtonsContainer.clear();

		resultsPanel.setStyleName("resultsPanel");

		utilities = new Utilities(panels,searchResults.size(),searchKeyWord, searchResults);
	    utilities.loadSearchResultBoxes(searchResults, resultsPanel,pageId);

	    utilities.sortButton(searchResults);

	    utilities.showMap();

		northContainer.add(searchBox);
		northContainer.add(mapWidget);
		northContainer.add(sortResultsContainer);
		centerContainer.add(northContainer);

		centerPanel.setStyleName("centerPanel");

		FlowPanel westTreesPanel = new FlowPanel();
		westTreesPanel.setStyleName("westTreesPanel");
		westTreesPanel.add(clear);
		westTreesPanel.add(westTrees);

		centerPanel.add(westTreesPanel);

	    utilities.pageButtons();
	    resultsAndButtonsContainer.add(resultsPanel);
		resultsAndButtonsContainer.add(buttons);
		centerPanel.add(resultsAndButtonsContainer);
		centerContainer.add(centerPanel);
		resultsAndButtonsContainer.setStyleName("resultsAndButtonContainer");

		dock.add(centerContainer);
	}

	private static void clearButtonAction(final FlowPanel resultsPanel, int size) {

		centerPanel.remove(resultsPanel);
    	resultsPanel.clear();
    	pageId=0;
    	mapWidget.setZoom(4);
    	for(int i =0;i<size;i++)
		{
			if(iw[i]!=null)
			{
				iw[i].close();
			}
		}

    	greetingService.emptyFilters(new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Void result) {
				mapWidget.triggerResize();
			}
    	});
	}

	public static void loadSearchBox()
	{
		final Label errorLabel = new Label();

		errorLabel.setText("");
		SearchArguments search = new SearchArguments();
		search.setSearchArgument(searchTerm.getValue());

		if (!FieldVerifier.isValidName(search.getSearchArgument())) {
			errorLabel.setText("Please enter at least four characters");
			return;
		}

		sendButton.setEnabled(false);
		searchBoxLabel.setText(search.getSearchArgument());


		greetingService.greetServer(search, new AsyncCallback<HashMap<Integer, Business>>() {
			public void onFailure(Throwable caught) {
				popUpDialogs.createErrorBox(SERVER_ERROR,"Remote Procedure Call - Failure","Server error:");
			}

			public void onSuccess(HashMap<Integer, Business> result) {
				loadPage(result);
			}
		});
	}
}
