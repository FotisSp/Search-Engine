package com.search_engine.application.click_handlers;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.search_engine.application.client.GreetingService;
import com.search_engine.application.client.GreetingServiceAsync;
import com.search_engine.application.client.PopUpDialogs;
import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.Review;

public class ReviewClickHandler implements ClickHandler
{
	private static GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private String businessId;
	private String checkIns;
	private static PopUpDialogs popUpDialogs = new PopUpDialogs();

	public ReviewClickHandler(Business business) {
		this.businessId = business.getBusinessId();
		this.checkIns = business.getChk();
	}

	@Override
	public void onClick(ClickEvent event) {
		greetingService.findReviews(businessId, new AsyncCallback<HashMap<Integer,Review>>() {
			@Override
			public void onSuccess(HashMap<Integer, Review> result) {
				if(result == null && checkIns.isEmpty()) {
					popUpDialogs.createErrorBox("No Reviews Found", "No Reviews Found","");
				} else {
					popUpDialogs.createDialogBox(result, checkIns);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

}
