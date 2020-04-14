package com.techelevator.model.dao;

import java.util.List;

import com.techelevator.model.beans.Category;

public interface CategoryDAO {

	public List<Category> getAllCategories();
	
	public void addCategory(String categoryName);
	
	public Category findCategoryById(long id);
}
