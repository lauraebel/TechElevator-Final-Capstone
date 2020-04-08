package com.techelevator.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcReservationDAO implements ReservationDAO{
	
	private static final String SQL_SELECT_RESERVATION = "SELECT id, user_id, tool_id, loaned_on, due_on, "
			+ "returned_on FROM reservations";
	
	private final long RESERVATION_PERIOD = 7;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcReservationDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}	

	@Override
	public List<Reservation> searchByBorrowerName(String name) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = "SELECT reservations.id, reservations.user_id, reservations.tool_id, "
				+ "reservations.loaned_on, reservations.due_on, "
				+ "reservations.returned_on FROM reservations "
				+ "JOIN users ON reservations.user_id=users.id"
				+ "WHERE users.first_name LIKE '%?%' OR users.last_name LIKE '%?%'";
		// TODO check on like '%?%' syntax
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, name, name);
		while (result.next()) {
			reservations.add(mapRowToReservation(result));
		}
		return reservations;
	}

	@Override
	public List<Reservation> searchByBorrowerLicenseNumber(String licenseNumber) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = "SELECT reservations.id, reservations.user_id, reservations.tool_id, "
				+ "reservations.loaned_on, reservations.due_on, "
				+ "reservations.returned_on FROM reservations "
				+ "JOIN users ON reservations.user_id=users.id"
				+ "WHERE users.license_number=?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, licenseNumber);
		while (result.next()) {
			reservations.add(mapRowToReservation(result));
		}
		return reservations;
	}

	@Override
	public List<Reservation> searchByToolId(long toolId) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = SQL_SELECT_RESERVATION + " WHERE tool_id=?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, toolId);
		while (result.next()) {
			reservations.add(mapRowToReservation(result));
		}
		return reservations;
	}

	@Override
	public Reservation addReservation(long toolId, long userId) {
		String sql = "INSERT INTO reservations(tool_id, user_id, loaned_on, due_on) "
				+ "VALUES (?, ?, ?, ?) RETURNING id";
		long newId = jdbcTemplate.queryForObject(sql, toolId, userId, 
				LocalDate.now(), LocalDate.now().plusDays(RESERVATION_PERIOD));
		// TODO determine appropriate syntax for above query
		
		Reservation newReservation = new Reservation();
		newReservation.setReservationId(newId);
		newReservation.setToolId(toolId);
		newReservation.setUserId(userId);
		newReservation.setDateLoaned(LocalDate.now());
		newReservation.setDueDate(LocalDate.now().plusDays(RESERVATION_PERIOD));
		return newReservation;
	}

	@Override
	public Reservation renewReservation(long reservationId) {
		LocalDate newDueDate = LocalDate.now().plusDays(RESERVATION_PERIOD);
		String sql = "UPDATE reservations SET due_on = ? WHERE id = ?";
		long id = jdbcTemplate.update(sql, newDueDate, reservationId);
		
		Reservation result = jdbcTemplate.queryForObject(SQL_SELECT_RESERVATION 
				+ "WHERE id = ?", reservationId);
		// TODO determine appropriate syntax for above query
		
		return result;
	}

	@Override
	public void returnReservation(long reservationId) {
		String sql = "UPDATE reservations SET returned_on = ? WHERE id = ?";
		jdbcTemplate.update(sql, LocalDate.now(), reservationId);		
	}
	
	private Reservation mapRowToReservation(SqlRowSet row) {
		Reservation reservation = new Reservation();		
		reservation.setReservationId(row.getLong("id"));
		reservation.setUserId(row.getLong("user_id"));
		reservation.setToolId(row.getLong("tool_id"));
		reservation.setDateLoaned(row.getDate("loaned_on").toLocalDate());
		reservation.setDueDate(row.getDate("due_on").toLocalDate());
		reservation.setDateReturned(row.getDate("returned_on").toLocalDate());
		return reservation;
	}

}
