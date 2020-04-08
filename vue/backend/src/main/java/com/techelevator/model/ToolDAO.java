package com.techelevator.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface ToolDAO {
	
	public List<Tool> getAllTools();

	/**
	 * View all available tools.
	 * 
	 * @return a List of tool objects
	 */
    public List <Tool> getAllAvailableTools();
    
    public Tool getToolById(long id);
    
    /**
     * Search tool collection by category.
     * 
     * @param category the category selected from given list
     * @return a List of tool objects
     */
    public List <Tool> getToolsByCategoryId(long categoryId);
    
    /**
     * Search tool collection by brand.
     *     
     * @param brand the brand selected from given list
     * @return a List of tool objects
     */
    public List <Tool> getToolsByBrandId(long brandId);
    
    /**
     * Search tool collection by name.
     * 
     * @param name the entered tool name
     * @return a List of tool objects
     */
    public List <Tool> getToolsByKeyword(String keyword);

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
