package com.search_engine.application.shared;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class SearchArguments implements Serializable
{
	private String searchArgument;
	private Set<String> filters = new HashSet<String>();
	
	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void addFilters(Set<String> filters)
	{
		this.filters = filters;
	}

	public Set<String> getFilters() {
		return filters;
	}

	@Override
	public String toString() {
		return "[filters=" + filters + "]";
	}
}
