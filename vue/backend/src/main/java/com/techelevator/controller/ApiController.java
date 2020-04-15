package com.techelevator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.techelevator.model.beans.Reservation;
import com.techelevator.model.beans.Tool;
import com.techelevator.model.beans.User;
import com.techelevator.model.dao.BrandDAO;
import com.techelevator.model.dao.CartDAO;
import com.techelevator.model.dao.CategoryDAO;
import com.techelevator.model.dao.LoanDAO;
import com.techelevator.model.dao.ReservationDAO;
import com.techelevator.model.dao.ToolDAO;
import com.techelevator.model.dao.UserDao;
import com.techelevator.model.exceptions.AddToCartException;
import com.techelevator.model.exceptions.FetchUserLoansException;
import com.techelevator.model.exceptions.RemoveFromCartException;

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
	
	@Autowired
	private ReservationDAO reservationDao;

//	@RequestMapping(path = "/", method = RequestMethod.GET)
//	public String authorizedOnly() throws UnauthorizedException {
//		/*
//		 * You can lock down which roles are allowed by checking if the current user has
//		 * a role.
//		 * 
//		 * In this example, if the user does not have the admin role we send back an
//		 * unauthorized error.
//		 */
//		if (!authProvider.userHasRole(new Long[] { 1l, 2l })) {
//			throw new UnauthorizedException();
//		}
//		return "Success";
//	}

	@GetMapping("/tools")
	public List<Tool> listAllTools() {
		return toolDao.getAllTools();
	}

	@GetMapping("/available")
	public List<Tool> listAvailableTools() {
		return toolDao.getAllAvailableTools();
	}

	@GetMapping("/tools/{id}")
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

	@GetMapping("/cart/{username}")
	public Cart listUserCart(@PathVariable String username) {
		User user = userDao.getUserByUsername(username);
		Cart cart = cartDao.getCartByUser(user.getId());
		return cart;
	}

	@PostMapping("/cart/add/{userId}")
	public void add(@RequestBody Tool tool, @PathVariable long userId) throws AddToCartException {
		Cart cart = cartDao.getCartByUser(userId);
		
		if (cart != null) {
			cartDao.addToCart(userId, tool.getToolId());
		} else {
			throw new AddToCartException();
		}
		
	}
	
	@PostMapping("/cart/remove")
	public void remove(@RequestBody Long userId, Long toolId) throws RemoveFromCartException {
		Cart cart = cartDao.getCartByUser(userId);

		if (cart != null) {
			cartDao.removeFromCart(userId, toolId);
		} else {
			throw new RemoveFromCartException();
		}
	}
	
	@PostMapping("/cart/checkout/{username}")
	public void checkout(@PathVariable String username) {
		User user = userDao.getUserByUsername(username);
		Cart cart = cartDao.getCartByUser(user.getId());
		
		for (Tool item : cart.getItems()) {
			if (reservationDao.getReservationByIds(user.getId(), item.getToolId()) != null) {
				reservationDao.removeReservation(user.getId(), item.getToolId());
			}
		}
		
		loanDao.addLoan(cart);
		
		cartDao.clearCart(cart.getId());
	}
	
	@GetMapping("/loans/{username}")
	public List<Loan> getActiveLoans(@PathVariable String username) throws FetchUserLoansException {
		User user = userDao.getUserByUsername(username);
		
		if (user != null) {
			List<Loan> loans = loanDao.getLoansByUser(user.getId());
			
			return loans;
		} else {
			throw new FetchUserLoansException();
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
	
	@GetMapping("/reservations")
	public List<Reservation> listAllReservations() throws Exception {
		List<Reservation> allReservations = reservationDao.getAllReservations();
		if(allReservations != null) {
			return allReservations;
		} else {
			throw new Exception ("No reservations found.");
		}
	}
	
	@GetMapping("/reservations/{userId}")
	public List<Reservation> listUserReservations(@RequestBody String username) throws Exception {
		User user = userDao.getUserByUsername(username);
		List<Reservation> userReservations = new ArrayList<Reservation>();
		List<Reservation> allReservations = reservationDao.getAllReservations();
		if(allReservations != null) {
			for (Reservation reservation : allReservations) {
				if(reservation.getUserId() == user.getId()) {
					userReservations.add(reservation);
				}
			}
			return userReservations;
		} else {
			throw new Exception ("No reservations found.");
		}
	}
	
	@PostMapping("/reservations/add/{userId}")
	public void addReservation (@RequestBody Tool tool, @PathVariable long userId) throws Exception {
		long toolId = tool.getToolId();	
		if ((Long) toolId != null && (Long) userId != null) {
			reservationDao.addReservation(userId, toolId);
		} else {
			throw new Exception("Unable to add reservation.");
		}
		
	}

}