package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcBrandDAO implements BrandDAO {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcBrandDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Brand> getAllBrands() {
		List<Brand> brands = new ArrayList<Brand>();
		String sql = "SELECT id, name FROM brands";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			brands.add(mapRowToBrand(result));
		}
		return brands;
	}
	
	private Brand mapRowToBrand(SqlRowSet row) {
		Brand brand = new Brand();
		brand.setBrandId(row.getLong("id"));
		brand.setBrandName(row.getString("name"));
		return brand;
	}

	@Override
	public void addBrand(Brand newBrand) {
		Long brandId = getNextBrandId();
		String sqlInsertBrand = "INSERT INTO brands(id, name) VALUES (?, ?)";
		jdbcTemplate.update(sqlInsertBrand, brandId, newBrand.getBrandName());
		newBrand.setBrandId(brandId);	
	}

	@Override
	public Brand findBrandById(long id) {
		Brand brand = null;
		String sql = "SELECT id, name FROM brands WHERE id = ? ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);
		
		if (rows.next()) {
			brand = mapRowToBrand(rows);
		}
		return brand;
	}
	
	
	private Long getNextBrandId() {
		String sqlSelectNextId = "SELECT NEXTVAL('brands_id_seq')";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectNextId);
		Long brandId = null;
		if(results.next()) {
			brandId = results.getLong(1);
		} else {
			throw new RuntimeException("Unable to get next Brand Id");
		}
		return brandId;
	}

}
