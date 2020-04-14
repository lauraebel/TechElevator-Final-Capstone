package com.techelevator.integration;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.beans.Brand;
import com.techelevator.model.dao.BrandDAO;
import com.techelevator.model.dao.JdbcBrandDAO;

public class JdbcBrandDAOIntegrationTest {
	
	private BrandDAO dao;
	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private static final String TEST_BRAND = "Fake Brand";
	
	
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
		dao = new JdbcBrandDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void insert_new_brand() {
		truncateBrands();
		Assert.assertEquals(0, dao.getAllBrands().size());
		
		dao.addBrand("name");
		
		Assert.assertEquals(1, dao.getAllBrands().size());
	}
	
	@Test
	public void lists_all_brands() {
		truncateBrands();
		
		String sqlListAllBrands = "INSERT INTO brands(id, name) VALUES (1000, ?) ";		
		jdbcTemplate.update(sqlListAllBrands, TEST_BRAND);
		boolean listsBrands = false;
		
		for(Brand brands : dao.getAllBrands()) {
			String example = brands.getBrandName();
			if(example.equals("Fake Brand")) {
				listsBrands = true;
			}
		}
		Assert.assertTrue(listsBrands);
	}
	
	@Test
	public void returns_5_when_5_brands_added() {
		truncateBrands();
		
		dao.addBrand("Brand 1");
		dao.addBrand("Brand 2");
		dao.addBrand("Brand 3");
		dao.addBrand("Brand 4");
		dao.addBrand("Brand 5");
		
		Assert.assertEquals(5, dao.getAllBrands().size());
	}
	
	@Test
	public void find_brand_by_id() {
		truncateBrands();
		
		String sql = "INSERT INTO brands (id, name) VALUES (1000, ?) ";		
		jdbcTemplate.update(sql, TEST_BRAND);

		Assert.assertEquals("Fake Brand", dao.findBrandById(1000).getBrandName());
	}
	
	private void truncateBrands() {
		String sql = "TRUNCATE brands CASCADE";
		jdbcTemplate.update(sql);
	}

}
