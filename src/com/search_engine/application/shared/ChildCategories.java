package com.search_engine.application.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChildCategories implements Serializable
{
	private String name;
	private int occurrences = 0;
	
	public void construct(String name, int occurrences)
	{
		this.name = name;
		this.occurrences = occurrences;
	}

	public String getName() 
	{
		return name;
	}
	
	public int getOccurrences() 
	{
		return occurrences;
	}

	public boolean containsName(String name)
	{
		if(this.name.equals(name))
		{
			occurrences++;
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		return name+" ("+occurrences+")";
	}
}
