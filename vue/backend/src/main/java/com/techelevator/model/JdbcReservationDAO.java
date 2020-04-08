package com.techelevator.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcReservationDAO implements ReservationDAO {

	private static final String SQL_SELECT_RESERVATION = "SELECT reservations.id, reservations.user_id, "
			+ "reservatuins.loaned_on, reservations.due_on, reservations.returned_on, tools.id, tools.name, "
			+ "tools.description, tools.img_name, brands.name "
			+ "FROM reservations JOIN tools ON reservation.tool_id=tools.id JOIN brands ON tools.brand_id=brands.id ";

	private final long RESERVATION_PERIOD = 7;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcReservationDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = SQL_SELECT_RESERVATION;
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			reservations.add(mapRowToReservation(result));
		}
		return reservations;
	}

	@Override
	public List<Reservation> getAllCurrentlyOnLoan() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = SQL_SELECT_RESERVATION + "WHERE reservations.returned_on IS NULL";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			reservations.add(mapRowToReservation(result));
		}
		return reservations;
	}

	@Override
	public List<Reservation> getReservationByBorrowerName(String name) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = SQL_SELECT_RESERVATION	+ "JOIN users ON reservations.user_id=users.id"
				+ "WHERE users.first_name LIKE ? OR users.last_name LIKE ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, "%" + name + "%", "%" + name + "%");
		while (result.next()) {
			reservations.add(mapRowToReservation(result));
		}
		return reservations;
	}

	@Override
	public List<Reservation> getReservationByLicenseNumber(String licenseNumber) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = SQL_SELECT_RESERVATION	+ "JOIN users ON reservations.user_id=users.id "
		+ "WHERE users.license_number= (CAST (? AS varchar))";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, licenseNumber);
		while (result.next()) {
			reservations.add(mapRowToReservation(result));
		}
		return reservations;
	}

	@Override
	public List<Reservation> getReservationByToolId(long toolId) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = SQL_SELECT_RESERVATION + " WHERE tools.id=?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, toolId);
		while (result.next()) {
			reservations.add(mapRowToReservation(result));
		}
		return reservations;
	}
	
	@Override
	public Reservation getReservationByReservationId(long reservationId) {
		String sql = SQL_SELECT_RESERVATION + " WHERE reservations.id=?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, reservationId);
		Reservation reservation = mapRowToReservation(result);
		return reservation;
	}

	@Override
	public Reservation addReservation(long toolId, long userId) {
		String sql = "INSERT INTO reservations(tool_id, user_id, loaned_on, due_on) "
				+ "VALUES (?, ?, ?, ?) RETURNING id";
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql, toolId, userId, LocalDate.now(),
				LocalDate.now().plusDays(RESERVATION_PERIOD));
		row.next();
		long newId = row.getLong("id");
		
		Reservation newReservation = getReservationByReservationId(newId);

		return newReservation;
	}

	@Override
	public Reservation renewReservation(long reservationId) {
		LocalDate newDueDate = LocalDate.now().plusDays(RESERVATION_PERIOD);
		String sql = "UPDATE reservations SET due_on = ? WHERE id = ?";
		jdbcTemplate.update(sql, newDueDate, reservationId);

		SqlRowSet reservationResults = jdbcTemplate.queryForRowSet(SQL_SELECT_RESERVATION + "WHERE reservations.id = ?",
				reservationId);
		reservationResults.next();
		Reservation reservation = mapRowToReservation(reservationResults);
		return reservation;
	}

	@Override
	public void returnReservation(long reservationId) {
		String sql = "UPDATE reservations SET returned_on = ? WHERE id = ?";
		jdbcTemplate.update(sql, LocalDate.now(), reservationId);
	}

	private Reservation mapRowToReservation(SqlRowSet row) {
		Tool reservationTool = new Tool();
		reservationTool.setToolId(row.getLong("tools.id"));
		reservationTool.setToolName(row.getString("tools.name"));
		reservationTool.setToolDescription(row.getString("tools.description"));
		reservationTool.setToolImgName(row.getString("tools.img_name"));
		reservationTool.setToolBrandId(row.getLong("tools.brand_id"));
		
		Reservation reservation = new Reservation();
		reservation.setReservationId(row.getLong("id"));
		reservation.setUserId(row.getLong("user_id"));
		reservation.setTool(reservationTool);
		reservation.setDateLoaned(row.getDate("loaned_on").toLocalDate());
		reservation.setDueDate(row.getDate("due_on").toLocalDate());
		reservation.setDateReturned(row.getDate("returned_on").toLocalDate());
		return reservation;
	}

}
