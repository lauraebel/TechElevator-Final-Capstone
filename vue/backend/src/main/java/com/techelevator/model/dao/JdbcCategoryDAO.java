package com.techelevator.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.beans.Category;

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
	public void addCategory(String categoryName) {
		String sqlInsertCategory = "INSERT INTO category (name) VALUES (?)";
		jdbcTemplate.update(sqlInsertCategory, categoryName);
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

}
