package com.techelevator.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techelevator.model.beans.Brand;

@Component
public interface BrandDAO {
	
	public List<Brand> getAllBrands();
	
	public void addBrand(String brandName);
	
	public Brand findBrandById(long id);

}
