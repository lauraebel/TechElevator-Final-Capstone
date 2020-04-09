package com.techelevator.model.dao;

import java.util.List;

import com.techelevator.model.beans.Category;

public interface CategoryDAO {

	public List<Category> getAllCategories();
	
	public void addCategory(Category newCategory);
	
	public Category findCategoryById(long id);
}
