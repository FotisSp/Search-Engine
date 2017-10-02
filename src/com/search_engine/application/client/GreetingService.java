package com.search_engine.application.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.Review;
import com.search_engine.application.shared.RootCategories;
import com.search_engine.application.shared.SearchArguments;


@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	HashMap<Integer, Business> greetServer(SearchArguments name) throws IllegalArgumentException;
	
	void checkIndex() throws IllegalArgumentException, IOException;
	
	List<RootCategories> getFacetsMap();
	
	HashMap<Integer, Business> getPrintList(Set<String> filters) throws IOException;
	
	void emptyFilters();
	
	Set<String> getFilters();
	
	HashMap<Integer,Review> findReviews(String businessId);
	
	int getProgress();
}
