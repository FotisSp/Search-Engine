package com.search_engine.application.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class Categories implements Serializable
{
	private String name = "";
	private int occurrences = 0;
	private List<Categories> subCategory = new ArrayList<>();
	private boolean subCategoryExists = false;
	
	public String getName() {
		return toString();
	}
	
	public int getOccurrences() {
		return occurrences;
	}
	
	public List<Categories> getSubCategory() {
		return subCategory;
	}
	
	public List<String> getStringCategories() {
		List<String> tmp = new ArrayList<>();
		for(int i=0; i<subCategory.size(); i++)
		{
			tmp.add(subCategory.get(i).toString());
		}
		Collections.sort(tmp);
		return tmp;
	}
	
	public void constructCategories(String name, int occurrences) {
		this.occurrences = occurrences;
		this.name = name;
	}
	
	public void setSubCategory(List<Categories> subCategory) {
		this.subCategory = subCategory;
	}
	
	public void createSubCategory(List<String> sub)
	{
		for(int i=0; i<sub.size(); i++)
		{
			Categories newCategory = new Categories();
			newCategory.constructCategories(sub.get(i), 0);
			addToSubCategory(newCategory);
		}
	}
	
	public void setSubCategoryExistance()
	{
		subCategoryExists = true;
	}
	
	public void increaseOccurrences() {
		occurrences++;
	}

	public void addToSubCategory(Categories subCategory) {
		this.subCategory.add(subCategory);
	}	
	
	public boolean contains(String name)
	{
		if(this.name.equals(name))
		{
			increaseOccurrences();
			return true;
		}
		return false;
	}
	
	public boolean exists(String name)
	{
		if(this.name.equals(name))
		{
			return true;
		}
		return false;
	}
	
	public boolean hasSubCategory()
	{
		return subCategoryExists;
	}
	
	public void removeEmpty()
	{
		List<Categories> newCategories = new ArrayList<>();
		for(int i=0; i<subCategory.size(); i++)
		{
			if(subCategory.get(i).getOccurrences() > 0)
			{
				newCategories.add(subCategory.get(i));
			}
		}
		setSubCategory(newCategories);
	}

	public String toString()
	{
		return name+" ("+occurrences+")";
	}
}
