package com.techelevator.model.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.beans.Cart;
import com.techelevator.model.beans.Tool;

@Component
public class JdbcCartDAO implements CartDAO {

	private JdbcTemplate jdbc;
	
	@Autowired
	private ToolDAO toolDao;
	
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
	public Cart getCartByUser(Long userId) {
		
		String sql = "SELECT tool_id FROM cart_items WHERE user_id = ?";
		
		SqlRowSet results = jdbc.queryForRowSet(sql, userId);
		
		Cart cart = new Cart();
		cart.setId(userId);
		
		while(results.next()) {
			cart.addItem(toolDao.getToolById(results.getLong("tool_id")));
		}
		
		return cart;
	}
	
	@Override
	public Cart addToCart(Long userId, Long toolId) {
		String sql = "INSERT INTO cart_items (user_id, tool_id) VALUES (?, ?)";
		
		jdbc.update(sql, userId, toolId);
		
		return getCartByUser(userId);
	}
	
	@Override
	public Cart removeFromCart(Long userId, Long toolId) {
		String sql = "DELETE FROM cart_items WHERE user_id = ? AND tool_id = ?";
		
		jdbc.update(sql, userId, toolId);
		
		return getCartByUser(userId);
	}
	
	@Override
	public Cart clearCart(Long userId) {
		String sql = "DELETE FROM cart_items WHERE user_id = ?";
		
		jdbc.update(sql, userId);
		
		return getCartByUser(userId);
	}

	public ToolDAO getToolDao() {
		return toolDao;
	}

	public void setToolDao(ToolDAO toolDao) {
		this.toolDao = toolDao;
	}
	
	
}