package com.techelevator.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.beans.Brand;

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

	@Override
	public void addBrand(String brandName) {
		String sqlInsertBrand = "INSERT INTO brands(name) VALUES (?)";
		jdbcTemplate.update(sqlInsertBrand, brandName);
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

	private Brand mapRowToBrand(SqlRowSet row) {
		Brand brand = new Brand();
		brand.setBrandId(row.getLong("id"));
		brand.setBrandName(row.getString("name"));
		return brand;
	}
}
