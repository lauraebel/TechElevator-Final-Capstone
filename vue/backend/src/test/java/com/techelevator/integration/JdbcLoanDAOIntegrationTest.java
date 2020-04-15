package com.techelevator.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.beans.Cart;
import com.techelevator.model.beans.Tool;
import com.techelevator.model.dao.JdbcLoanDAO;
import com.techelevator.model.dao.LoanDAO;
import com.techelevator.model.dao.ToolDAO;

public class JdbcLoanDAOIntegrationTest {
	
	private LoanDAO dao;
	private ToolDAO toolDao;
	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private static final String TEST_RESERVATION = "Fake Reservation";

	private Cart cart1;

	
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
		dao = new JdbcLoanDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void get_all_loans_0_on_truncated_table() {
		truncateLoans();
		Assert.assertEquals(0, dao.getAllLoans().size());		
	}
	
	@Test
	public void add_loan_and_get_all_loans() {
		truncateLoans();
		Assert.assertEquals(0, dao.getAllLoans().size());
		
		cart1.addItem(makeATool());
		
		dao.addLoan(cart1);
		Assert.assertEquals(1, dao.getAllLoans().size());
	}	
	
	@Test
	public void get_loans_by_user() {
		
	}
	
	@Test
	public void get_all_active_loans() {
		
	}
	
	@Test
	public void renew_loan() {
		
	}
	
	@Test
	public void return_loan() {
		
	}
	
	private void truncateLoans() {
		String sql = "TRUNCATE loans CASCADE";
		jdbcTemplate.update(sql);
	}
	
	
	private Tool makeATool() {
		List<Long> categoryList = new ArrayList<Long>();
		categoryList.add(1l);
		String toolName = "Hammer";
		String description = "It's a banger";
		String imgName = "hammer.png";
		long brandId = 1l;

		String sql = "INSERT INTO tools(name, description, img_name, brand_id) " + "VALUES (?, ?, ?, ?) RETURNING id";
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql, toolName, description, imgName, brandId);
		row.next();
		long newToolId = row.getLong("id");

		Tool tool = toolDao.getToolById(newToolId);
		return tool;
	}

}
