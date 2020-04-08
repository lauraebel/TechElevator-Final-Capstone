package com.techelevator.integration;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Category;
import com.techelevator.model.CategoryDAO;
import com.techelevator.model.JdbcCategoryDAO;

public class JdbcCategoryDAOIntegrationTest {
	
	private CategoryDAO dao;
	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private static final String TEST_CATEGORY = "Fake Category";
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tool_library");
		dataSource.setUsername("capstone_owner");
		dataSource.setPassword("capstone_owner1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Before
	public void setup() {
		dao = new JdbcCategoryDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void insert_new_category() {
		Category category = getCategory("name");
		dao.addCategory(category);
		
		Assert.assertNotEquals(0, category.getCategoryId());
		
		Category newCategory = dao.findCategoryById(category.getCategoryId());
		
		Assert.assertNotNull(newCategory);
		Assert.assertEquals(category.getCategoryId(), newCategory.getCategoryId());
		Assert.assertEquals(category.getCategoryName(), newCategory.getCategoryName());
	}
	
	private Category getCategory(String name) {
		Category selectedCategory = new Category();
		selectedCategory.setCategoryName("name");
		return selectedCategory;
	}
	
	@Test
	public void lists_all_categories() {
		String sqlListAllCategories = "INSERT INTO category(id, name) VALUES (1000, ?) ";
		
		jdbcTemplate.update(sqlListAllCategories, TEST_CATEGORY);
		boolean listsCategories = false;
		
		for(Category categories : dao.getAllCategories()) {
			String example = categories.getCategoryName();
			if(example.equals("Fake Category")) {
				listsCategories = true;
			}
		}
		Assert.assertTrue(listsCategories);
	}
}
