package com.techelevator.model.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.beans.Cart;
import com.techelevator.model.beans.Loan;
import com.techelevator.model.beans.Tool;

@Component
public class JdbcLoanDAO implements LoanDAO {

	private static final String SQL_SELECT_LOAN = 
			"SELECT "
					+ "loans.id, "
					+ "loans.user_id, "
					+ "loans.loaned_on, "
					+ "loans.due_on, "
					+ "loans.returned_on, "
					+ "tools.id, "
					+ "tools.name, "
					+ "tools.description, "
					+ "tools.img_name, "
					+ "brands.name "
			+ "FROM "
				+ "loans "
			+ "JOIN tools ON loans.tool_id = tools.id "
			+ "JOIN brands ON tools.brand_id = brands.id ";

	private final long LOAN_PERIOD = 7;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcLoanDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Loan> getAllLoans() {
		List<Loan> loans = new ArrayList<Loan>();
		String sql = SQL_SELECT_LOAN;
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			loans.add(mapRowToLoan(result));
		}
		return loans;
	}

	@Override
	public List<Loan> getAllActiveLoans() {
		List<Loan> loans = new ArrayList<Loan>();
		String sql = SQL_SELECT_LOAN + "WHERE loans.returned_on IS NULL";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			loans.add(mapRowToLoan(result));
		}
		return loans;
	}
	
	@Override
	public Loan addLoan(long toolId, long userId) {
		//refactor so takes userID, gets all tool ids associated with that user's cart id, and creates new row for each item
		String sql = "INSERT INTO loans(tool_id, user_id, loaned_on, due_on) VALUES (?, ?, ?, ?) RETURNING *";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, toolId, userId, LocalDate.now(), LocalDate.now().plusDays(LOAN_PERIOD));
		
		results.next();
		
		return mapRowToLoan(results);
	}

	@Override
	public Loan renewLoan(long loanId) {
		LocalDate newDueDate = LocalDate.now().plusDays(LOAN_PERIOD);
		
		String sql = "UPDATE loans SET due_on = ? WHERE id = ? RETURNING *";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, newDueDate, loanId);
		results.next();
		
		return mapRowToLoan(results);
	}

	@Override
	public Loan returnLoan(long reservationId) {
		String sql = "UPDATE loans SET returned_on = ? WHERE id = ? RETURNING *";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, LocalDate.now(), reservationId);

		results.next();

		return mapRowToLoan(results);
	}

	private Loan mapRowToLoan(SqlRowSet row) {
		Tool reservationTool = new Tool();
		reservationTool.setToolId(row.getLong("tools.id"));
		reservationTool.setToolName(row.getString("tools.name"));
		reservationTool.setToolDescription(row.getString("tools.description"));
		reservationTool.setToolImgName(row.getString("tools.img_name"));
		reservationTool.setToolBrandId(row.getLong("tools.brand_id"));
		
		Loan reservation = new Loan();
		reservation.setLoanId(row.getLong("id"));
		reservation.setUserId(row.getLong("user_id"));
		reservation.setTool(reservationTool);
		reservation.setDateLoaned(row.getDate("loaned_on").toLocalDate());
		reservation.setDueDate(row.getDate("due_on").toLocalDate());
		reservation.setDateReturned(row.getDate("returned_on").toLocalDate());
		return reservation;
	}

}
