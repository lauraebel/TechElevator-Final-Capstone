package com.techelevator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.beans.Brand;
import com.techelevator.model.beans.Cart;
import com.techelevator.model.beans.Category;
import com.techelevator.model.beans.Loan;
import com.techelevator.model.beans.Tool;
import com.techelevator.model.beans.User;
import com.techelevator.model.dao.BrandDAO;
import com.techelevator.model.dao.CartDAO;
import com.techelevator.model.dao.CategoryDAO;
import com.techelevator.model.dao.LoanDAO;
import com.techelevator.model.dao.ToolDAO;
import com.techelevator.model.dao.UserDao;

/**
 * ApiController
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private AuthProvider authProvider;

	@Autowired
	private BrandDAO brandDao;

	@Autowired
	private CartDAO cartDao;

	@Autowired
	private CategoryDAO categoryDao;

	@Autowired
	private LoanDAO loanDao;

	@Autowired
	private ToolDAO toolDao;
	
	@Autowired
	private UserDao userDao;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String authorizedOnly() throws UnauthorizedException {
		/*
		 * You can lock down which roles are allowed by checking if the current user has
		 * a role.
		 * 
		 * In this example, if the user does not have the admin role we send back an
		 * unauthorized error.
		 */
		if (!authProvider.userHasRole(new String[] { "admin" })) {
			throw new UnauthorizedException();
		}
		return "Success";
	}

	@GetMapping("/tools")
	public List<Tool> listAllTools() {
		return toolDao.getAllTools();
	}

	@GetMapping("/available")
	public List<Tool> listAvailableTools() {
		return toolDao.getAllAvailableTools();
	}

	@GetMapping("/tools{id}")
	public Tool getToolById(@PathVariable long id) throws Exception {
		Tool tool = toolDao.getToolById(id);
		if (tool != null) {
			return tool;
		} else {
			throw new Exception("Tool not found");
		}
	}

	@GetMapping("/categories")
	public List<Category> listAllCategories() {
		return categoryDao.getAllCategories();
	}

	@GetMapping("/brands")
	public List<Brand> getListOfBrandNames() throws Exception {
		List<Brand> brands = brandDao.getAllBrands();
		if (brands != null) {
			return brands;
		} else {
			throw new Exception("No brands found.");
		}
	}

	@GetMapping("/loans")
	public List<Loan> listEntireReservationHistory() throws Exception {
		List<Loan> loans = loanDao.getAllLoans();
		if (loans != null) {
			return loans;
		} else {
			throw new Exception("No loans found.");
		}
	}

	@GetMapping("/carts")
	public List<Cart> listAllCarts() throws Exception {
		List<Cart> carts = cartDao.getCarts();
		if (carts != null) {
			return carts;
		} else {
			throw new Exception("No carts found.");
		}
	}

	@GetMapping("/cart/{userId}")
	public Cart listUserCart(@PathVariable long userId) {
		Cart cart = cartDao.getCartByUser(userId);
		return cart;
	}

	@PutMapping("/cart/{userId}")
	public void update(@RequestBody Cart cart, @PathVariable long userId) {
		Cart requestedCart = cartDao.getCartByUser(userId);
		if (requestedCart != null) {
			cartDao.updateCart(cart);
		}
	}
	
	@GetMapping("/users")
	public List<User> listAllUsers() throws Exception {
		List<User> allUsers = userDao.getAllUsers();
		if (allUsers != null) {
			return allUsers;
		} else {
			throw new Exception("No users found.");
		}
	}
	
	@GetMapping("/users/{username}")
	public User getCurrentUser(@RequestBody String username) throws Exception{
		User user = userDao.getUserByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new Exception ("No user found.");
		}
		
	}

}