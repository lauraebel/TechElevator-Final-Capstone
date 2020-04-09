package com.techelevator.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Brand;
import com.techelevator.model.JdbcBrandDAO;
import com.techelevator.model.JdbcToolDAO;
import com.techelevator.model.Tool;
import com.techelevator.model.ToolDAO;

public class JdbcToolDAOIntegrationTest {
	
	private ToolDAO dao;
	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private static final String TEST_TOOL = "Fake Tool";
	
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
		dao = new JdbcToolDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void insert_new_tool() {
		List<Long> categories;
		Tool tool = new Tool("name", "description", 100, "img", categories);
		dao.addTool(tool);
		
		Assert.assertNotEquals(0, tool.getToolId());
	}
	
	private Tool getTool(String name, String description, long brandId, String img, List<Long> categories) {
		Tool selectedTool = new Tool();
		selectedTool.setToolName("name");
		selectedTool.setToolDescription("description");
		selectedTool.setToolBrandId(100);
		selectedTool.setToolImgName("img");
		selectedTool.setToolCategories(categories);
		return selectedTool;
	}
	


}
