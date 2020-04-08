package com.techelevator.model;

import java.util.List;

import com.techelevator.model.Category;

public interface CategoryDAO {

	public List<Category> getAllCategories();
	
	public void addCategory(Category newCategory);
	
	public Category findCategoryById(long id);
}
