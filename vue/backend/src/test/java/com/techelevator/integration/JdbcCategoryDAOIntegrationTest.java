package com.techelevator.integration;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.beans.Category;
import com.techelevator.model.dao.CategoryDAO;
import com.techelevator.model.dao.JdbcCategoryDAO;

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
		truncateCategory();
		Assert.assertEquals(0, dao.getAllCategories().size());
		
		dao.addCategory(TEST_CATEGORY);
		
		Assert.assertEquals(1, dao.getAllCategories().size());
	}
	
	@Test
	public void returns_5_categories_when_5_added() {
		truncateCategory();
		
		String sqlListAllCategories = "INSERT INTO category(id, name) VALUES (?, ?) ";
		
		jdbcTemplate.update(sqlListAllCategories, 1000, TEST_CATEGORY + "1");
		jdbcTemplate.update(sqlListAllCategories, 1001, TEST_CATEGORY + "2");
		jdbcTemplate.update(sqlListAllCategories, 1002, TEST_CATEGORY + "3");
		jdbcTemplate.update(sqlListAllCategories, 1003, TEST_CATEGORY + "4");
		jdbcTemplate.update(sqlListAllCategories, 1004, TEST_CATEGORY + "5");
		
		Assert.assertEquals(5, dao.getAllCategories().size());;
	}
	
	@Test
	public void find_category_by_id() {
		truncateCategory();
		
		String sqlListAllCategories = "INSERT INTO category(id, name) VALUES (?, ?) ";
		jdbcTemplate.update(sqlListAllCategories, 1000, TEST_CATEGORY + "1");
		
		Assert.assertEquals("Fake Category1", dao.findCategoryById(1000).getCategoryName());
	}
	
	private void truncateCategory() {
		String sql = "TRUNCATE category CASCADE";
		jdbcTemplate.update(sql);
	}
}
