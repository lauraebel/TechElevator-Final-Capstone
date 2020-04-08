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
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, tools.brand_id "
				+ "tool_category.catetory_id JOIN tool_category ON tools.id=tool_category.tool_id FROM tools";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getAllAvailableTools() {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, tools.brand_id "
				+ "FROM tools JOIN reservations ON tools.id=reservations.tool_id "
				+ "WHERE tools.id NOT IN (SELECT tools.id FROM reservations JOIN tools ON "
				+ "reservation.tool_id=tools.id WHERE reservations.returned_on IS NULL)";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
		
	}
	
	@Override
	public Tool getToolById(long id) {
		Tool tool = null;
		String sql = "SELECT id, name, description, img_name, brand_id FROM tools WHERE id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		if(result.next()) {
			tool = mapRowToTool(result);
		}
		return tool;
	}

	@Override
	public List<Tool> getToolsByCategoryId(long categoryId) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, tools.brand_id "
				+ "FROM tools JOIN tool_category ON tools.id=tool_category.tool_id "
				+ "WHERE tool_category.category_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, categoryId);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getToolsByBrandId(long brandId) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT id, name, description, img_name, brand_id "
				+ "FROM tools WHERE brand_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, brandId);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public List<Tool> getToolsByKeyword(String keyword) {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT id, name, description, img_name, brand_id FROM tools "
				+ "WHERE name LIKE ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, "%" + keyword + "%");
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		return tools;
	}

	@Override
	public Tool addTool(String toolName, String description, long brandId, String imgName, List<Long> toolCategories) {

		String sql = "INSERT INTO tools(name, description, img_name, brand_id) " + "VALUES (?, ?, ?, ?) RETURNING id";
		SqlRowSet row = jdbcTemplate.queryForRowSet(sql, toolName, description, brandId, imgName);
		row.next();
		long newToolId = row.getLong("id");

		for (Long toolCategory : toolCategories) {
			sql = "INSERT INTO tool_category(tool_id, category_id) VALUES (?, ?)";
			jdbcTemplate.update(sql, newToolId, toolCategory);
		}
		

		Tool newTool = new Tool();
		newTool.setToolId(newToolId);
		newTool.setToolName(toolName);
		newTool.setToolDescription(description);
		newTool.setToolBrandId(brandId);
		newTool.setToolImgName(imgName);
		newTool.setToolCategories(toolCategories);
		return newTool;
	}

	private Tool mapRowToTool(SqlRowSet row) {		
		
		Tool tool = new Tool();
		tool.setToolId(row.getLong("tools.id"));
		tool.setToolName(row.getString("tools.name"));
		tool.setToolDescription(row.getString("tools.description"));
		tool.setToolImgName(row.getString("tools.img_name"));
		tool.setToolBrandId(row.getLong("tools.brand_id"));
		return tool;
	}

}
