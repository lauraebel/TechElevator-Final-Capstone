package com.techelevator.model.dao;

import java.util.List;

import com.techelevator.model.beans.Cart;
import com.techelevator.model.beans.Loan;

public interface LoanDAO {
	
	public List <Loan> getAllLoans();
	
	public List <Loan> getAllActiveLoans();
		
	public List<Loan> getLoansByUser(Long userId);
	/**
	 * Add a loan to the database.
	 * 
	 * @param toolId based on selected tool
	 * @param userId based on signed in user or id entered by administrator
	 * @return void
	 */
	public void addLoan(Cart cart);
	
	/**
	 * Renew an existing reservation by adding an additional 
	 * reservation period to the current date unless there is
	 * a pending hold on the item.
	 * 
	 * @param loanId
	 * @return Loan object
	 */
	public void renewLoan(long loanId);
	
	/**
	 * Must be done by administrator. Puts
	 * tool back into available tools unless a hold has been
	 * placed on the tool.
	 * 
	 * @param loanId
	 */
	public void returnLoan(long loanId);
	
}
