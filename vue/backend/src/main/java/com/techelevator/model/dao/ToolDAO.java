package com.techelevator.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.techelevator.model.beans.Tool;

@Component
public interface ToolDAO {
	
	public List<Tool> getAllTools();

    public List <Tool> getAllAvailableTools();
    
    public List<Tool> getfilteredTools(boolean onlyAvailable, long categoryId, long brandId);
    
    public Tool getToolById(long id);

   /**
    * Save a new tool to the database. Category & Brand will be cross-referenced
    * with category and brand tables to add to those tables as necessary.
    * 
    * @param toolName name of tool
    * @param description a brief description of the tool
    * @param brand selected from list of available brands
    * @param category selected from list of available categories
    * @return the new tool object
    */
    public Tool addTool(String toolName, String description, long brandId, String imgName, List<Long> toolCategories);
	
}