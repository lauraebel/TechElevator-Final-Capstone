package com.techelevator.model.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.beans.Reservation;

@Component
public class JdbcReservationDAO implements ReservationDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcReservationDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = "SELECT user_id, tool_id, cancel_date FROM reservations";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			reservations.add(mapRowToReservation(results));
		}		
		return reservations;
	}
	
	@Override
	public Reservation getReservationByIds(long userId, long toolId) {
		Reservation reservation = new Reservation();
		String sql = "SELECT user_id, tool_id, cancel_date FROM reservations "
				+ "WHERE user_id = ? AND tool_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, toolId);
		
		reservation = mapRowToReservation(results);
		return reservation;
	}
	
	@Override
	public Reservation getFirstReservationByToolId(long toolId) {
		Reservation reservation = new Reservation();
		String sql = "SELECT user_id, tool_id, cancel_date FROM reservations "
				+ "WHERE tool_id = ? ORDER BY id LIMIT 1";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, toolId);
		
		if (results.next()) {
			reservation = mapRowToReservation(results);
			
			LocalDate cancelDate = LocalDate.now().plusDays(2l);
			sql = "UPDATE reservations SET cancel_date = ? WHERE tool_id = ? AND user_id = ?";
			jdbcTemplate.update(sql, cancelDate, toolId, reservation.getUserId());
			
			return reservation;
		} else {
			return null;
		}
	}

	@Override
	public void addReservation(long userId, long toolId) {
		String sql = "INSERT INTO reservations (user_id, tool_id) VALUES (?, ?)";
		jdbcTemplate.update(sql, userId, toolId);		
	}

	@Override
	public void removeReservation(long userId, long toolId) {
		String sql = "DELETE FROM reservations WHERE user_id = ? AND tool_id = ?";
		jdbcTemplate.update(sql, userId, toolId);
	}
	
	private Reservation mapRowToReservation(SqlRowSet row) {
		Reservation reservation = new Reservation();
		reservation.setUserId(row.getLong("user_id"));
		reservation.setToolId(row.getLong("tool_id"));
		if (row.getDate("cancel_date") != null){
			reservation.setCancelDate(row.getDate("cancel_date").toLocalDate());
		}
		return reservation;
	}
	
}
