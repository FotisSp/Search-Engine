package com.search_engine.application.client;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.Review;
import com.search_engine.application.shared.RootCategories;
import com.search_engine.application.shared.SearchArguments;

public interface GreetingServiceAsync {
	void greetServer(SearchArguments input, AsyncCallback<HashMap<Integer, Business>> callback) throws IllegalArgumentException;

	void checkIndex(AsyncCallback<Void> callback);

	void getFacetsMap(AsyncCallback<List<RootCategories>> callback );
	
	void getPrintList(Set<String> filters,AsyncCallback<HashMap<Integer, Business>> callback);
	
	void emptyFilters(AsyncCallback<Void> callback);
	
	void getFilters(AsyncCallback<Set<String>> callback);
	
	void findReviews(String businessId, AsyncCallback<HashMap<Integer,Review>> callback);
	
	void getProgress(AsyncCallback<Integer> callback);
}
