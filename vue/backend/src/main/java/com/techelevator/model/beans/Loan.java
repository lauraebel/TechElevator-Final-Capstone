package com.techelevator.model.beans;

import java.time.LocalDate;

public class Loan {
	
	private long loanId;	
	private long userId;	
	private Tool tool;	
	private LocalDate dateLoaned;	
	private LocalDate dateReturned;	
	private LocalDate dueDate;

	public long getLoanId() {
		return loanId;
	}
	
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public Tool getTool() {
		return tool;
	}
	
	public void setTool(Tool tool) {
		this.tool = tool;
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