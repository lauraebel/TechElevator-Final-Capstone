package com.techelevator.model;

import java.util.List;

public interface CategoryDAO {

	public List<Category> getAllCategories();
	
	public void addCategory(Category newCategory);
}
