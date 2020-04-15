package com.techelevator.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.dao.JdbcToolDAO;
import com.techelevator.model.dao.ToolDAO;
import com.techelevator.model.beans.Tool;

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
	public void get_all_tools() {
		truncateTool();
		Assert.assertEquals(0, dao.getAllTools().size());
		
		List<Long> categoryList = new ArrayList<Long>();
		categoryList.add(1l);
		long toolId = 50l;
		String toolName = "Hammer";
		String description = "It's a banger";
		String imgName = "hammer.png";
		long brandId = 1l;

		String sql = "INSERT INTO tools(id, name, description, img_name, brand_id) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, toolId, toolName, description, imgName, brandId);
		
		Assert.assertEquals(1, dao.getAllTools().size());
	}
	
	@Test
	public void insert_tool_and_get_by_id() {
		truncateTool();
		Assert.assertEquals(0, dao.getAllTools().size());
		
		List<Long> categoryList = new ArrayList<Long>();
		categoryList.add(1l);
		long toolId = 50l;
		String toolName = "Hammer";
		String description = "It's a banger";
		String imgName = "hammer.png";
		long brandId = 1l;

		String sql = "INSERT INTO tools(id, name, description, img_name, brand_id) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, toolId, toolName, description, imgName, brandId);
		
		Assert.assertEquals(1l, dao.getToolById(50l).getToolBrandId());
	}
	
	@Test
	public void get_all_available_tools() {
		truncateTool();
		
		List<Long> categoryList = new ArrayList<Long>();
		categoryList.add(1l);
		long toolId = 101051l;
		String toolName = "Hammer";
		String description = "It's a banger";
		String imgName = "hammer.png";
		long brandId = 1l;

		String sql = "INSERT INTO tools(id, name, description, img_name, brand_id) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, toolId, toolName, description, imgName, brandId);
		
		Assert.assertEquals(1, dao.getAllAvailableTools().size());
	}
	
	@Test
	public void add_tool() {
		truncateTool();
		
		List<Long> categoryList = new ArrayList<Long>();
		categoryList.add(1l);
		String toolName = "Hammer";
		String description = "It's a banger";
		String imgName = "hammer.png";
		long brandId = 1l;
		
		dao.addTool(toolName, description, (int) brandId, imgName, categoryList);
		
		Assert.assertEquals(1, dao.getAllTools().size());
		
	}
	
	private void truncateTool() {
		String sql = "TRUNCATE tools CASCADE";
		jdbcTemplate.update(sql);
	}

}
