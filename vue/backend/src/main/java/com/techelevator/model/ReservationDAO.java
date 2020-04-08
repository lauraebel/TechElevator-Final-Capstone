package com.techelevator.model;

import java.util.List;

public interface ReservationDAO {
	
	public List <Reservation> getAllReservations();
	
	public List <Reservation> getAllCurrentlyOnLoan();
	
	/**
	 * Search reservations by borrowers first or last name.
	 * 
	 * @param name borrower's first or last name
	 * @return a List of reservations
	 */
	public List <Reservation> getReservationByBorrowerName(String name);
	
	/**
	 * Search reservations by borrower's license number.
	 * 
	 * @param licenseNumber borrower's license number
	 * @return a List of reservations
	 */
	public List <Reservation> getReservationByLicenseNumber(String licenseNumber);
	
	/**
	 * Search reservations by tool id
	 * 
	 * @param toolId 
	 * @return a List of reservations
	 */
	public List <Reservation> getReservationByToolId(long toolId);

	public Reservation getReservationByReservationId(long reservationId);
	
	/**
	 * Add a reservation to the database.
	 * 
	 * @param toolId based on selected tool
	 * @param userId based on signed in user or id entered by administrator
	 * @return a reservation object
	 */
	public Reservation addReservation(long toolId, long userId);
	
	/**
	 * Renew an existing reservation by adding an additional 
	 * reservation period to the current date unless there is
	 * a pending hold on the item.
	 * 
	 * @param reservationId
	 * @return reservation object
	 */
	public Reservation renewReservation(long reservationId);
	
	/**
	 * Return a tool. Must be done by administrator. Puts
	 * tool back into available tools unless a hold has been
	 * placed on the tool.
	 * 
	 * @param reservationId
	 */
	public void returnReservation(long reservationId);
	
}
