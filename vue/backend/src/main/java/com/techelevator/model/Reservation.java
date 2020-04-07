package com.techelevator.model;

import java.time.LocalDate;

public class Reservation {
	
	private long reservationId;	
	private long userId;	
	private long toolId;	
	private LocalDate dateLoaned;	
	private LocalDate dateReturned;	
	private LocalDate dueDate;

	public long getReservationId() {
		return reservationId;
	}
	
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getToolId() {
		return toolId;
	}
	
	public void setToolId(long toolId) {
		this.toolId = toolId;
	}
	
	public LocalDate getDateLoaned() {
		return dateLoaned;
	}
	
	public void setDateLoaned(LocalDate dateLoaned) {
		this.dateLoaned = dateLoaned;
	}
	
	public LocalDate getDateReturned() {
		return dateReturned;
	}
	
	public void setDateReturned(LocalDate dateReturned) {
		this.dateReturned = dateReturned;
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
}