package com.techelevator.integration;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.dao.JdbcLoanDAO;
import com.techelevator.model.dao.LoanDAO;

public class JdbcLoanDAOIntegrationTest {
	
	private LoanDAO dao;
	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private static final String TEST_RESERVATION = "Fake Reservation";

	
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
	public void get_all_loans() {
		
	}
	
	private void truncateLoans() {
		String sql = "TRUNCATE loans CASCADE";
		jdbcTemplate.update(sql);
	}
	
	//getAllLoans()
	//test when normal table -> returns list of correct length
	//test when table empty
	
	//getAllActiveLoans()
	//test when normal table
	//test when table empty
	
	//addLoan()
	//test when loan is good
	//test when invalid data
	//test when empty
	
	//renewLoan()
	
	//returnLoan()

}
