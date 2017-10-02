package com.search_engine.application.server;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.search_engine.application.client.GreetingService;
import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.Review;
import com.search_engine.application.shared.RootCategories;
import com.search_engine.application.shared.SearchArguments;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService
{
    private List<RootCategories> facetsList;
    private Set<String> filters = new HashSet<String>();
    private SearchArguments input;
    private SearchEngine search;
    private HashMap<Integer, Business> printList = new HashMap<>();
    private Indexer index = new Indexer();

	public HashMap<Integer, Business> greetServer(SearchArguments input) throws IllegalArgumentException
	{
		this.input = input;
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		input.setSearchArgument(escapeHtml(input.getSearchArgument()));
		userAgent = escapeHtml(userAgent);

		HashMap<Integer, Business> map = new HashMap<>();
		try {
			search = new SearchEngine();
			map = search.searchTerm(input);
			facetsList = search.createFacets(map);
		} catch (IOException | ParseException | JSONException
				| org.apache.lucene.queryparser.classic.ParseException e) {
			e.printStackTrace();
		}
		return map;
	}

	public HashMap<Integer,Review> findReviews(String businessId)
	{
		HashMap<Integer,Review> results = new HashMap<>();
		try {
			results = search.reviewSearch(businessId);
		} catch (org.apache.lucene.queryparser.classic.ParseException | IOException e) {
			e.printStackTrace();
		}
		return results;
	}

	public void checkIndex() throws IllegalArgumentException, IOException
	{
		try {
			index.indexer();
		} catch (JSONException | ParseException e) {
			e.printStackTrace();
		}
	}

	public int getProgress()
	{
		if(index == null || index.getProgress() == -1)
		{
			return -1;
		}
		return index.getProgress();
	}

	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public List<RootCategories> getFacetsMap() {
		return facetsList;
	}

	public HashMap<Integer, Business> getPrintList(Set<String> filters) throws IOException
	{
		this.filters = filters;
		input.addFilters(this.filters);
		printList = search.filter();
		return printList;
	}

	public Set<String> getFilters() {
		return filters;
	}

	public void emptyFilters()
	{
		printList.clear();
		filters.clear();
	}
}
