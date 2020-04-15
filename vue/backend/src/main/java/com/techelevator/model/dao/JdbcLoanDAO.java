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

	private JdbcTemplate jdbcTemplate;


	private final long LOAN_PERIOD = 7;

	private static final String SQL_SELECT_LOAN = 
			"SELECT "
					+ "loans.id, "
					+ "loans.user_id, "
					+ "loans.loaned_on, "
					+ "loans.due_on, "
					+ "loans.returned_on, "
					+ "tools.id AS tool_id, "
					+ "tools.name, "
					+ "tools.description, "
					+ "tools.img_name, "
					+ "tools.brand_id "
					+ "FROM "
					+ "loans "
					+ "JOIN tools ON loans.tool_id = tools.id ";




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
	public List<Loan> getLoansByUser(Long userId){
		List<Loan> loans = new ArrayList<Loan>();

		String sql = SQL_SELECT_LOAN + "WHERE user_id = ? AND returned_on IS NULL";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

		while (results.next()) {
			Loan loan = mapRowToLoan(results);
			loans.add(loan);
		}

		return loans;
	}

	@Override
	public void addLoan(Cart cart) {
		String sql = "INSERT INTO loans(user_id, tool_id, loaned_on, due_on) VALUES (?, ?, ?, ?)";

		for (Tool tool : cart.getItems()) {
			jdbcTemplate.update(sql, cart.getId(), tool.getToolId(), LocalDate.now(), LocalDate.now().plusDays(LOAN_PERIOD));
		}
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
	public Loan returnLoan(long loanId) {
		String sql = "UPDATE loans SET returned_on = ? WHERE id = ? RETURNING *";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, LocalDate.now(), loanId);

		results.next();

		return mapRowToLoan(results);
	}

	private Loan mapRowToLoan(SqlRowSet row) {
		Tool reservationTool = new Tool();
		reservationTool.setToolId(row.getLong("tool_id"));
		reservationTool.setToolName(row.getString("name"));
		reservationTool.setToolDescription(row.getString("description"));
		reservationTool.setToolImgName(row.getString("img_name"));
		reservationTool.setToolBrandId(row.getLong("brand_id"));

		Loan reservation = new Loan();
		reservation.setLoanId(row.getLong("id"));
		reservation.setUserId(row.getLong("user_id"));
		reservation.setTool(reservationTool);
		reservation.setDateLoaned(row.getDate("loaned_on").toLocalDate());
		reservation.setDueDate(row.getDate("due_on").toLocalDate());
		if (row.getDate("returned_on") != null) {
			reservation.setDateReturned(row.getDate("returned_on").toLocalDate());
		}
		return reservation;
	}

}
