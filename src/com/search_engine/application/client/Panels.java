package com.search_engine.application.client;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Panels 
{
	private FlowPanel resultsPanel;
	private MapWidget mapWidget;
	private Marker[] markerDrop;
	private InfoWindow[] iw;
	private FlowPanel buttons;
	private FlowPanel numOfResults;
	private ScrollPanel scrollPanel;
	private VerticalPanel centerContainer;
	private FlowPanel sortButtonPanel;
	
	public Panels(FlowPanel resultsPanel, MapWidget mapWidget, Marker[] markerDrop,
			InfoWindow[] iw, FlowPanel buttons, FlowPanel numOfResults, ScrollPanel scrollPanel,
			VerticalPanel centerContainer, FlowPanel sortButtonPanel) {
		this.resultsPanel = resultsPanel;
		this.mapWidget = mapWidget;
		this.markerDrop = markerDrop;
		this.iw = iw;
		this.buttons = buttons;
		this.numOfResults = numOfResults;
		this.scrollPanel = scrollPanel;
		this.centerContainer = centerContainer;
		this.sortButtonPanel = sortButtonPanel;
	}

	public FlowPanel getResultsPanel() {
		return resultsPanel;
	}

	public MapWidget getMapWidget() {
		return mapWidget;
	}

	public Marker[] getMarkerDrop() {
		return markerDrop;
	}

	public InfoWindow[] getIw() {
		return iw;
	}

	public FlowPanel getButtons() {
		return buttons;
	}
	
	public FlowPanel getNumOfResults() {
		return numOfResults;
	}
	
	public ScrollPanel getScrollPanel() {
		return scrollPanel;
	}
	
	public VerticalPanel getCenterContainer() {
		return centerContainer;
	}
	
	public FlowPanel getSortButtonPanel() {
		return sortButtonPanel;
	}
}