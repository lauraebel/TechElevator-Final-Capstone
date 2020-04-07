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
		String sql = "SELECT id, name, description, img_name, brand_id FROM tools";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getAllAvailableTools() {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, " + "tools.img_name, tools.brand_id "
				+ "FROM tools " + "JOIN reservations ON tools.id=reservations.tool_id "
				+ "WHERE returned_on IS NOT NULL";
		// TODO selects tools which have been returned but checked out again
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}
	
	@Override
	public List<Tool> getToolById(long id) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT id, name, description, img_name, brand_id FROM tools WHERE id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getToolsByCategory(String category) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, " + "tools.img_name, tools.brand_id "
				+ "FROM tools " + "JOIN tool_category ON tools.id=tool_category.tool_id "
				+ "JOIN category ON tool_category.category_id=category.id " + "WHERE category.name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, category);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getToolsByBrand(String brand) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, " + "tools.img_name, tools.brand_id "
				+ "FROM tools " + "JOIN brands ON tools.brand_id=brands.id " + "WHERE brands.name = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, brand);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getToolsByName(String name) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT id, name, description, img_name, brand_id FROM tools " + "WHERE name LIKE ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, "%" + name + "%");
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getAllCheckedOutTools() {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, "
				+ "tools.img_name, tools.brand_id, reservations.due_on FROM tools "
				+ "JOIN reservations ON tools.id=reservations.tool_id WHERE returned_on IS NULL";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
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
		tool.setToolBrand(row.getString("tools.brand_id"));
		return tool;
	}

}
