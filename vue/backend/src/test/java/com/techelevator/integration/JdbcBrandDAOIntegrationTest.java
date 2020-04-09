package com.techelevator.integration;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Brand;
import com.techelevator.model.BrandDAO;
import com.techelevator.model.Category;
import com.techelevator.model.JdbcBrandDAO;
import com.techelevator.model.JdbcCategoryDAO;

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
		Brand brand = getBrand("name");
		dao.addBrand(brand);
		
		Assert.assertNotEquals(0, brand.getBrandId());
		
		Brand newBrand = dao.findBrandById(brand.getBrandId());
		
		Assert.assertNotNull(newBrand);
		Assert.assertEquals(brand.getBrandId(), newBrand.getBrandId());
		Assert.assertEquals(brand.getBrandName(), newBrand.getBrandName());
	}
	
	@Test
	public void lists_all_brands() {
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
	
	private Brand getBrand(String name) {
		Brand selectedBrand = new Brand();
		selectedBrand.setBrandName("name");
		return selectedBrand;
	}
	

}
