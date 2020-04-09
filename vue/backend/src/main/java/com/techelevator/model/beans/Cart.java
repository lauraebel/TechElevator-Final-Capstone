package com.techelevator.model.beans;

import java.util.Set;

public class Cart {

	private Long id;
	private Long userID;
	private Set<Long> items;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Set<Long> getItems() {
		return items;
	}
	public void setItems(Set<Long> items) {
		this.items = items;
	}
	
	public void addItem(Long item) {
		items.add(item);
	}
}
