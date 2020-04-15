package com.techelevator.model.dao;

import java.util.List;

import com.techelevator.model.beans.Reservation;

public interface ReservationDAO {
	
	public List<Reservation> getAllReservations();
	
	public Reservation getReservationByIds(long userId, long toolId);
	
	public Reservation getFirstReservationByToolId(long toolId);
	
	public void addReservation(long userId, long toolId);
	
	public void removeReservation(long userId, long toolId);

}
