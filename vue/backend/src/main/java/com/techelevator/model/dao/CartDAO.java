package com.techelevator.model.dao;

import java.util.List;

import com.techelevator.model.beans.Cart;
import com.techelevator.model.beans.Tool;

public interface CartDAO {

	public List<Cart> getCarts();
	
	public Cart getCartByUser(Long userId);
		
	public Cart addToCart(Long userId, Long toolId);
	
	public Cart removeFromCart(Long userId, Long toolId);
	
	public Cart clearCart(Long userId);
}
