package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcToolDAO implements ToolDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcToolDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Tool> getAllTools() {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, brands.name FROM tools "
				+ "JOIN brands ON tools.brand_id=brands.id";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getAllAvailableTools() {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, brands.name "
				+ "FROM tools JOIN reservations ON tools.id=reservations.tool_id "
				+ "JOIN brands ON tools.brand_id=brands.id WHERE (returned_on IS NOT NULL) AND (";
		// TODO selects tools which have been returned but checked out again
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}
	
	@Override
	public Tool getToolById(long id) {
		Tool tool = null;
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, brands.name FROM tools "
				+ "JOIN brands ON tools.brand_id=brands.id WHERE tools.id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		if(result.next()) {
			tool = mapRowToTool(result);
		}
		return tool;
	}

	@Override
	public List<Tool> getToolsByCategory(String category) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, " + "tools.img_name, brands.name "
				+ "FROM tools JOIN tool_category ON tools.id=tool_category.tool_id "
				+ "JOIN category ON tool_category.category_id=category.id "
				+ "JOIN brands ON tools.brand_id=brands.id WHERE category.name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, category);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getToolsByBrand(String brand) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, brands.name "
				+ "FROM tools JOIN brands ON tools.brand_id=brands.id WHERE brands.name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, brand);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getToolsByKeyword(String keyword) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, brands.name FROM tools "
				+ "JOIN brands ON tools.brand_id=brands.id WHERE name LIKE ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, "%" + keyword + "%");
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<String> getListOfBrandNames() {
		List<String> brands = new ArrayList<String>();
		String sql = "SELECT name FROM brands";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			brands.add(result.toString());
		}
		return brands;
	}

	@Override
	public Tool addTool(String toolName, String description, String brand, String imgName, String category) {
		String sql = "SELECT id FROM brands WHERE name=?";
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql, brand);
		row.next();
		long brandId = row.getLong("id");

		sql = "INSERT INTO tools(name, description, img_name, brand_id) " + "VALUES (?, ?, ?, ?) RETURNING id";
		row = jdbcTemplate.queryForRowSet(sql, toolName, description, brandId, imgName);
		row.next();
		long newToolId = row.getLong("id");

		sql = "SELECT id FROM category WHERE name=?";
		row = jdbcTemplate.queryForRowSet(sql, category);
		row.next();
		long newCategoryId = row.getLong("id");

		sql = "INSERT INTO tool_category(tool_id, category_id) VALUES (?, ?)";
		jdbcTemplate.update(sql, newToolId, newCategoryId);

		Tool newTool = new Tool();
		newTool.setToolId(newToolId);
		newTool.setToolName(toolName);
		newTool.setToolDescription(description);
		newTool.setToolBrand(brand);
		newTool.setToolImgName(imgName);
		return newTool;
	}

	private Tool mapRowToTool(SqlRowSet row) {
		Tool tool = new Tool();
		tool.setToolId(row.getLong("tools.id"));
		tool.setToolName(row.getString("tools.name"));
		tool.setToolDescription(row.getString("tools.description"));
		tool.setToolImgName(row.getString("tools.img_name"));
		tool.setToolBrand(row.getString("brands.name"));
		return tool;
	}

}