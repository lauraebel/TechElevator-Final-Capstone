package com.techelevator.controller;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Tool;
import com.techelevator.model.ToolDAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiController
 */
@RestController
@RequestMapping("/tools")
public class ApiController {

	@Autowired
	private ToolDAO toolDao;
	
	@Autowired
	private ReservationDAO reservationDao;
	
    @Autowired
    private AuthProvider authProvider;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String authorizedOnly() throws UnauthorizedException {
        /*
        You can lock down which roles are allowed by checking
        if the current user has a role.
        
        In this example, if the user does not have the admin role
        we send back an unauthorized error.
        */
        if (!authProvider.userHasRole(new String[] { "admin" })) {
            throw new UnauthorizedException();
        }
        return "Success";
    }
    
    @GetMapping
    public List<Tool> listAllTools() {
    	return toolDao.getAllTools();
    }
    
	@GetMapping("/{id}")
	public Tool getToolById(@PathVariable long id) throws Exception {
		Tool tool = toolDao.getToolById(id);
		if(tool != null) {
			return tool;
		} else {
			throw new Exception("Tool not found");
		}
	}
	
	@GetMapping("/brand")
	public List<String> getListOfBrandNames() throws Exception {
		List<String> brands = toolDao.getListOfBrandNames();
		if(brands != null) {
			return brands;
		} else {
			throw new Exception("No tool brands available");
		}
	}
	
	@GetMapping("/{brand}")
	public List<Tool> getToolByBrand(@PathVariable String brandName) throws Exception {
		List<Tool> tools = toolDao.getToolsByBrand(brandName);
		if(tools != null) {
			return tools;
		} else {
			throw new Exception("No tools by that brand");
		}
	}
	
	@GetMapping("/{keyword}")
	public List<Tool> getToolsByKeyword(@PathVariable String keyword) throws Exception {
		List<Tool> tools = toolDao.getToolsByKeyword(keyword);
		if(tools != null) {
			return tools;
		} else {
			throw new Exception("No tools by that name");
		}
	}
	
	@GetMapping("/available")
	public List<Tool> listAvailableTools() {
		return toolDao.getAllAvailableTools();
	}
	
	
	@GetMapping("/{name}")
	public List<Reservation> listReservationsByBorrowerName(@PathVariable String name) throws Exception {
		List<Reservation> reservations = reservationDao.getReservationByBorrowerName(name);
		if(reservations != null) {
			return reservations;
		} else {
			throw new Exception("There are no reservations under that name");
		}
	}
	
	@GetMapping("/{licenseNumber}")
	public List<Reservation> listReservationsByLicenseNumber(@PathVariable String licenseNumber) throws Exception {
		List<Reservation> reservations = reservationDao.getReservationByLicenseNumber(licenseNumber);
		if(reservations != null) {
			return reservations;
		} else {
			throw new Exception("There are no reservations attached to that Driver's License Number");
		}
	}
	
	@GetMapping("/{toolId}")
	public List<Reservation> listReservationsByToolId(@PathVariable long toolId) throws Exception {
		List<Reservation> reservations = reservationDao.getReservationByToolId(toolId);
		if(reservations != null) {
			return reservations;
		} else {
			throw new Exception("There are no reservations for that tool");
		}
	}
	
	@GetMapping("/loaned")
	public List<Reservation> listCheckedOutToolsAndDueDates() throws Exception {
		List<Reservation> reservations = reservationDao.getAllCurrentlyOnLoan();
		if(reservations != null) {
			return reservations;
		} else {
			throw new Exception("There are no tools currently on loan");
		}
	}
	
	
}