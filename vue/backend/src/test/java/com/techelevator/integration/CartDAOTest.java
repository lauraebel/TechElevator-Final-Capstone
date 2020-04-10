package com.techelevator.integration;

import java.sql.SQLException;
import java.util.List;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.beans.Cart;
import com.techelevator.model.dao.CartDAO;
import com.techelevator.model.dao.JdbcCartDAO;

public class CartDAOTest {

	private CartDAO dao;
	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tool_library");
		dataSource.setUsername("capstone_owner");
		dataSource.setPassword("capstone_owner1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Before
	public void setup() {
		dao = new JdbcCartDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void get_carts_returns_5_carts_with_2_items_each() {
		List<Cart> carts = dao.getCarts();
		
		Assert.assertEquals(5, carts.size());
		
		for (Cart cart : carts) {
			Assert.assertEquals(2, cart.getItems().size());
		}
	}
	
	@Test
	public void get_cart_by_user_2_returns_cart_with_2_items() {
		Cart cart = dao.getCartByUser(2l);
		
		Assert.assertEquals(2, cart.getItems().size());
	}
	
	@Test
	public void update_cart_starting_2_items_ending_4_items() {
		Cart expected = dao.getCartByUser(1);
		
		expected.addItem(3l);
		expected.addItem(4l);
		
		Cart actual = dao.updateCart(expected);
		
		Assert.assertEquals(expected, actual);
		Assert.assertEquals(4, actual.getItems().size());
	}
	
}
