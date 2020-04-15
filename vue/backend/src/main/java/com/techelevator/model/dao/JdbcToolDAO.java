package com.techelevator.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.beans.Tool;

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
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, tools.brand_id FROM tools";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		while (result.next()) {
			tools.add(mapRowToTool(result));
		}
		for (Tool tool : tools) {
			List<Long> categories = new ArrayList<Long>();
			sql = "SELECT id FROM category JOIN tool_category ON category.id=tool_category.category_id "
					+ "WHERE tool_category.tool_id=?";
			result = jdbcTemplate.queryForRowSet(sql, tool.getToolId());
			while (result.next()) {
				categories.add(result.getLong("id"));
			}
			tool.setToolCategories(categories);
		}
		return tools;
	}

	@Override
	public List<Tool> getAllAvailableTools() {
		List<Tool> tools = new ArrayList<Tool>();
		String sql = "SELECT tools.id, tools.name, tools.description, tools.img_name, tools.brand_id "
				+ "FROM tools FULL JOIN loans ON tools.id=loans.tool_id "
				+ "WHERE tools.id NOT IN (SELECT tools.id FROM tools JOIN loans ON "
				+ "loans.tool_id=tools.id WHERE loans.returned_on IS NULL)";
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
		if (result.next()) {
			tool = mapRowToTool(result);
		}

		List<Long> categories = new ArrayList<Long>();
		sql = "SELECT category.id FROM category JOIN tool_category ON category.id=tool_category.category_id "
				+ "WHERE tool_category.tool_id=?";
		result = jdbcTemplate.queryForRowSet(sql, tool.getToolId());
		while (result.next()) {
			categories.add(result.getLong("id"));
		}
		tool.setToolCategories(categories);

		return tool;
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
		tool.setToolId(row.getLong("id"));
		tool.setToolName(row.getString("name"));
		tool.setToolDescription(row.getString("description"));
		tool.setToolImgName(row.getString("img_name"));
		tool.setToolBrandId(row.getLong("brand_id"));
		return tool;
	}

}
