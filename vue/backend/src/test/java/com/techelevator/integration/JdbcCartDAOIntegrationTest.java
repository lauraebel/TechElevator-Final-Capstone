package com.techelevator.integration;

import java.sql.SQLException;
import java.util.List;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.beans.Cart;
import com.techelevator.model.dao.JdbcCartDAO;
import com.techelevator.model.dao.JdbcToolDAO;

public class JdbcCartDAOIntegrationTest {

	private JdbcCartDAO dao;
	private JdbcToolDAO toolDao;
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
		toolDao = new JdbcToolDAO(dataSource);
		dao.setToolDao(toolDao);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void get_carts_with_empty_cart_items_table_returns_0() {
		truncateCart();
		
		List<Cart> carts = dao.getCarts();
		Assert.assertEquals(0, carts.size());
	}
		
	@Test
	public void get_size_2_when_2_carts_added_to_truncated_table() {
		truncateCart();
		
		dao.addToCart(2l, 1l);
		dao.addToCart(1l,  2l);
		
		List<Cart> carts = dao.getCarts();
		
		Assert.assertEquals(2, carts.size());
	}
	
	@Test
	public void get_1_item_per_cart() {
		truncateCart();
			
		dao.addToCart(2l, 1l);
		dao.addToCart(1l,  2l);
		
		List<Cart> carts = dao.getCarts();
		
		for (Cart cart : carts) {
			Assert.assertEquals(1, cart.getItems().size());
		}
	}
	
	@Test
	public void get_cart_by_user_2_returns_cart_with_2_items() {
		truncateCart();
		
		dao.addToCart(2l, 1l);
		dao.addToCart(2l, 3l);
			
		Cart cart = dao.getCartByUser(2l);
		
		Assert.assertEquals(2, cart.getItems().size());
	}
	
	@Test
	public void get_cart_by_user() {
		truncateCart();
		Cart cart = dao.getCartByUser(1l);
		Assert.assertEquals(0, cart.getItems().size());
		
		dao.addToCart(1l, 1l);
		cart = dao.getCartByUser(1l);
		Assert.assertEquals(1, cart.getItems().size());		
	}
	
	@Test
	public void remove_from_cart() {
		truncateCart();		
		dao.addToCart(2l, 1l);
		dao.addToCart(2l, 3l);			
		Cart cart = dao.getCartByUser(2l);		
		Assert.assertEquals(2, cart.getItems().size());
		
		dao.removeFromCart(2l, 1l);
		cart = dao.getCartByUser(2l);
		Assert.assertEquals(1,  cart.getItems().size());
	}
	
	@Test
	public void clear_cart() {
		truncateCart();
		dao.addToCart(2l, 1l);
		dao.addToCart(2l, 3l);			
		Cart cart = dao.getCartByUser(2l);		
		Assert.assertEquals(2, cart.getItems().size());
		
		dao.clearCart(2l);
		cart = dao.getCartByUser(2l);
		Assert.assertEquals(0,  cart.getItems().size());
	}
	
	private void truncateCart() {
		String sql = "TRUNCATE cart_items CASCADE";
		jdbcTemplate.update(sql);
	}
	
}
