package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Category;

@Component
public class JdbcCategoryDAO implements CategoryDAO {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcCategoryDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		String sql = "SELECT id, name FROM category";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			categories.add(mapRowToCategory(results));
		}
		return categories;
	}

	@Override
	public void addCategory(Category newCategory) {
		Long categoryId = getNextCategoryId();
		String sqlInsertCategory = "INSERT INTO category(id, name) VALUES (?, ?)";
		jdbcTemplate.update(sqlInsertCategory, categoryId, newCategory.getCategoryName());
		newCategory.setCategoryId(categoryId);
	}
	
	@Override
	public Category findCategoryById(long id) {
		Category category = null;
		String sql = "SELECT id, name FROM category WHERE id = ? ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);
		
		if (rows.next()) {
			category = mapRowToCategory(rows);
		}
		return category;
	}
	
	private Category mapRowToCategory(SqlRowSet row) {
		Category category = new Category();
		category.setCategoryId(row.getLong("id"));
		category.setCategoryName(row.getString("name"));
		return category;
	}

	private Long getNextCategoryId() {
		String sqlSelectNextId = "SELECT NEXTVAL('category_id_seq')";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectNextId);
		Long categoryId = null;
		if(results.next()) {
			categoryId = results.getLong(1);
		} else {
			throw new RuntimeException("Unable to get next Category Id");
		}
		return categoryId;
	}

}
