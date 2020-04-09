package com.techelevator.model.dao;

import java.util.List;

import com.techelevator.model.beans.Cart;

public interface CartDAO {

	public List<Cart> getCarts();
	
	public Cart getCartByUser(long userId);
	
}
