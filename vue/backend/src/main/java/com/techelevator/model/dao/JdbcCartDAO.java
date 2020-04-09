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
		
		String sql = "SELECT * FROM user_carts";
		
		SqlRowSet results = jdbc.queryForRowSet(sql);
		
		while (results.next()) {
			Cart cart = mapCartFromRow(results);
			carts.add(cart);
		}
		
		return carts;
	}

	@Override
	public Cart getCartByUser(long userId) {
		
		String sql = "SELECT * FROM user_carts WHERE userId = ?";
		
		SqlRowSet results = jdbc.queryForRowSet(sql, userId);
		
		results.next();
		
		return mapCartFromRow(results);
	}

	
	private Cart mapCartFromRow (SqlRowSet results) {
		Cart cart = new Cart();
		
		cart.setUserId(results.getLong("userId"));
		
		if ((Long)results.getLong("tool0") != null) {
			cart.setToolId0(results.getLong("tool0"));
		}
		if ((Long)results.getLong("tool1") != null) {
			cart.setToolId1(results.getLong("tool1"));
		}
		if ((Long)results.getLong("tool2") != null) {
			cart.setToolId2(results.getLong("tool2"));
		}
		if ((Long)results.getLong("tool3") != null) {
			cart.setToolId3(results.getLong("tool3"));
		}
		if ((Long)results.getLong("tool4") != null) {
			cart.setToolId4(results.getLong("tool4"));
		}
		if ((Long)results.getLong("tool5") != null) {
			cart.setToolId5(results.getLong("tool5"));
		}
		if ((Long)results.getLong("tool6") != null) {
			cart.setToolId6(results.getLong("tool6"));
		}
		if ((Long)results.getLong("tool7") != null) {
			cart.setToolId7(results.getLong("tool7"));
		}
		if ((Long)results.getLong("tool8") != null) {
			cart.setToolId8(results.getLong("tool8"));
		}
		if ((Long)results.getLong("tool9") != null) {
			cart.setToolId9(results.getLong("tool9"));
		}
		
		return cart;
	}
}
