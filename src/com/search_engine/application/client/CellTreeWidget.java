package com.search_engine.application.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.overlays.Marker;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.TreeViewModel;
import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.Categories;
import com.search_engine.application.shared.RootCategories;

public class CellTreeWidget implements TreeViewModel
{
	private List<RootCategories> facetResults = new ArrayList<>();
	private final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
	private static GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private final VerticalPanel myDock;
	private final FlowPanel myResults;
	private final MapWidget myMapWidget;
	private final FlowPanel myButtons;
	private final Marker myMarkerDrop[];
	private final static String[] EXCEPTIONS = {"American (New)","American (Traditional)"};
	private final static String[] RESULTS = {"American New","American Traditional"};
	private static int pageId;
	private final Panels myPanel;

	public CellTreeWidget(List<RootCategories> list, Panels panel, final String searchKeyWord) {
		myDock = panel.getCenterContainer();
		myResults = panel.getResultsPanel();
		myMapWidget = panel.getMapWidget();
		myMarkerDrop = panel.getMarkerDrop();
		myButtons = panel.getButtons();
		myPanel = panel;

		facetResults = list;
		pageId = 0;

		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			public void onSelectionChange(SelectionChangeEvent event) {
				final Set<String> selectedItems = selectionModel.getSelectedSet();

				greetingService.getFilters(new AsyncCallback<Set<String>>(){
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Set<String> selection) {
						for (Iterator<String> it = selectedItems.iterator(); it.hasNext();) {
							String f = it.next();
							f = f.replaceAll("[\\[()]", "");
							String processed = filterResult(f);

							if (selection.contains(processed)) {
								selection.remove(processed);
							} else {
								selection.add(processed);
							}
						}

						greetingService.getPrintList(selection, new AsyncCallback<HashMap<Integer, Business>>() {
							@Override
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(HashMap<Integer, Business> result) {
								final HTMLBuilder builder = new HTMLBuilder();
								builder.buildSearchResults(result);
								myDock.clear();
								myResults.clear();
								myButtons.clear();

								LatLng center = LatLng.newInstance(builder.getLatitude(0), builder.getLongitude(0));
								myMapWidget.setZoom(4);
							    myMapWidget.setCenter(center);

								final Utilities utilities = new Utilities(myPanel,result.size(),searchKeyWord,result);

								utilities.loadSearchResultBoxes(result,myResults,pageId);
							    utilities.sortButton(result);

								myDock.add(myResults);
								for(int k = 0;k<myMarkerDrop.length;k++)
								{
									myMarkerDrop[k].clear();
								}

								utilities.showMap();
								utilities.pageButtons();
							}
						});
					}
				});

			}
		});
	}

	private String filterResult(String term)
	{
		String result = "";
		String[] splitted = term.split(" ");
		int i=0;
		while(isString(splitted[i]))
		{
			result += splitted[i]+" ";
			i++;
		}
		result = result.trim();
		if(result.equals(RESULTS[0])) {
			result = EXCEPTIONS[0];
		}
		else if (result.equals(RESULTS[1])) {
			result = EXCEPTIONS[1];
		}
		return result;
	}

	private boolean isString(String string) {
	    try {
	        Integer.parseInt(string);
	    } catch (Exception e) {
	        return true;
	    }
	    return false;
	}

	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// LEVEL 0.
			ListDataProvider<RootCategories> dataProvider = new ListDataProvider<RootCategories>(facetResults);
			Cell<RootCategories> cell = new AbstractCell<RootCategories>() {
				@Override
				public void render(Context context, RootCategories value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value.getName());
					}
				}
			};
			return new DefaultNodeInfo<RootCategories>(dataProvider, cell);
		} else if (value instanceof RootCategories) {
			// LEVEL 1.
			if (((RootCategories) value).getName().equals("Categories")) {
				ListDataProvider<Categories> dataProvider1 = new ListDataProvider<Categories>(
						((RootCategories) value).getCategories());
				Cell<Categories> cell = new AbstractCell<Categories>() {
					@Override
					public void render(Context context, Categories value, SafeHtmlBuilder sb) {
						if (value != null) {
							sb.appendEscaped(value.getName());
						}
					}
				};
				return new DefaultNodeInfo<Categories>(dataProvider1, cell);
			} else {
				ListDataProvider<String> dataProvider = new ListDataProvider<String>(
						((RootCategories) value).getCityStateStars());
				return new DefaultNodeInfo<String>(dataProvider, new TextCell(), selectionModel, null);
			}
		} else if (value instanceof Categories) {
			// LEVEL 2,3
			if (((Categories) value).hasSubCategory()) {
				ListDataProvider<String> dataProvider = new ListDataProvider<String>(
						((Categories) value).getStringCategories());

				return new DefaultNodeInfo<String>(dataProvider, new TextCell(), selectionModel, null);
			}
		}
		return null;
	}

	public boolean isLeaf(Object value) {
		if (value instanceof String) {
			return true;
		}
		return false;
	}
}
