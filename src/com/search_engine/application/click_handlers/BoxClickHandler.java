package com.search_engine.application.click_handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlays.InfoWindow;
import com.google.gwt.maps.client.overlays.InfoWindowOptions;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.search_engine.application.client.Panels;

public class BoxClickHandler implements ClickHandler
{
	private double lat;
	private double longt;
	private int id;
	private String name;
	private MapWidget mapWidget;
	private Marker[] markerDrop;
	private int size;
	private InfoWindow[] iw;

	public BoxClickHandler(Double lat,Double longt,int id,String name, Panels panel, int size) {
		this.lat = lat;
		this.longt = longt;
		this.id = id;
		this.name = name;
		this.mapWidget = panel.getMapWidget();
		this.markerDrop = panel.getMarkerDrop();
		this.iw = panel.getIw();
		this.size = size;
		
	}
	
	public BoxClickHandler(Panels panel, int size,Double lat,Double longt) {
		this.mapWidget = panel.getMapWidget();
		this.markerDrop = panel.getMarkerDrop();
		this.iw = panel.getIw();
		this.size = size;
		this.lat = lat;
		this.longt = longt;

	}	

	@Override
	public void onClick(ClickEvent event) {
		LatLng markerCenter = LatLng.newInstance(lat,longt);
		mapWidget.setCenter(markerCenter);
		mapWidget.setZoom(16);
		mapWidget.triggerResize();
		drawInfoWindow(markerDrop[id],name,id);
	}
	
	public void drawInfoWindow(Marker marker,String name,int markerID) {
		HTMLPanel html= new HTMLPanel(name);

		InfoWindowOptions options = InfoWindowOptions.newInstance();
		options.setContent(html);
		for(int i =0;i<size;i++)
		{
			if(i != markerID && iw[i]!=null)
			{
				iw[i].close();
			}
		}
		iw[markerID] = InfoWindow.newInstance(options);
		iw[markerID].open(mapWidget, marker);
	}
}