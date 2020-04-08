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
		brand.setBrandId(row.getLong("brands.id"));
		brand.setBrandName(row.getString("brands.name"));
		return brand;
	}

}
