package com.techelevator.model.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.beans.Cart;

public class JdbcCartDAO implements CartDAO {

	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcCartDAO(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Cart> getCarts() {
		List<Cart> carts = new ArrayList<Cart>();
		
		String sql = "SELECT user_id FROM cart_items";
		
		SqlRowSet results = jdbc.queryForRowSet(sql);
		
		Set<Long> users = new HashSet<Long>();
		
		while (results.next()) {
			users.add(results.getLong("user_id"));
		}
		
		for (Long user : users) {
			Cart cart = getCartByUser(user);
			carts.add(cart);
		}
		
		return carts;
	}

	@Override
	public Cart getCartByUser(long userId) {
		
		String sql = "SELECT tool_id FROM cart_items WHERE user_id = ?";
		
		SqlRowSet results = jdbc.queryForRowSet(sql, userId);
		
		Cart cart = new Cart();
		cart.setId(userId);
		
		while(results.next()) {
			cart.addItem(results.getLong("tool_id"));
		}
		
		return cart;
	}

	@Override
	public Cart updateCart(Cart cart) {
		Set<Long> items = new HashSet<Long>();
		
		for (Long item : cart.getItems()) {
			items.add(item);
		}
		
		clearCart(cart.getId());
		
		for (Long item : items) {
			addToCart(cart.getId(), item);
		}
		
		Cart updatedCart = getCartByUser(cart.getId());
		
		return updatedCart;
	}
	
	private void clearCart(Long userId) {
		String sql = "DELETE FROM cart_items WHERE user_id = ?";
		
		jdbc.update(sql, userId);
	}
	
	private void addToCart(Long userId, Long toolId) {
		String sql = "INSERT INTO cart_items (user_id, tool_id) VALUES (?, ?)";
		
		jdbc.update(sql, userId, toolId);
	}
}
