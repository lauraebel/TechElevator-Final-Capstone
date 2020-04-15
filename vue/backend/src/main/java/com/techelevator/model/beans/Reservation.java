package com.techelevator.model.beans;

import java.time.LocalDate;

public class Reservation {
	
	private long userId;
	private long toolId;
	private LocalDate cancelDate;
	
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
	
	public LocalDate getCancelDate() {
		return cancelDate;
	}
	
	public void setCancelDate(LocalDate cancelDate) {
		this.cancelDate = cancelDate;
	}
	
}
