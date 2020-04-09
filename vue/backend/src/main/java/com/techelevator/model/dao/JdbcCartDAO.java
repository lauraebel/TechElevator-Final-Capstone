package com.techelevator.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.beans.Cart;

public class JdbcCartDAO implements CartDAO {

	private JdbcTemplate jdbc;
	
	@Override
	public List<Cart> getCarts() {
		List<Cart> carts = new ArrayList<Cart>();
		
		String cartQuery = "SELECT id, user_id FROM carts";
		String cartItemsQuery = "SELECT * FROM cart_items";
		String sql = "SELECT cart_items.cart_id, carts.user_id, cart_items.tool_id FROM carts JOIN cart_items ON carts.id = cart_items.cart_id";
		
		SqlRowSet cartResults = jdbc.queryForRowSet(cartQuery);
		
		while (results.next()) {
			Cart cart = mapCartFromRow(results);
			carts.add(cart);
		}
		
		return carts;
	}

	@Override
	public Cart getCartByUser(long userId) {
		
		String sql = "SELECT id FROM carts WHERE userId = ?";
		
		SqlRowSet results = jdbc.queryForRowSet(sql, userId);
		
		results.next();
		
		return mapCartFromRow(results);
	}

	@Override
	public Cart updateCart(Cart cart) {
		String sql = "";
		
		return null;
	}
	
	private Cart mapCartFromRow (SqlRowSet results) {
		Cart cart = new Cart();
		
		return cart;
	}
	
	
}
