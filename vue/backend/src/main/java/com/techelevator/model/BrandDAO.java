package com.techelevator.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface BrandDAO {
	
	public List<Brand> getAllBrands();

}
