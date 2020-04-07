package com.techelevator.model;

import java.util.List;

public interface ToolDAO {

	/**
	 * View all available tools.
	 * 
	 * @return a List of tool objects
	 */
    public List <Tool> searchByAvailable();
    
    /**
     * Search tool collection by category.
     * 
     * @param category the category selected from given list
     * @return a List of tool objects
     */
    public List <Tool> searchByCategory(String category);
    
    /**
     * Search tool collection by brand.
     *     
     * @param brand the brand selected from given list
     * @return a List of tool objects
     */
    public List <Tool> searchByBrand(String brand);
    
    /**
     * Search tool collection by name.
     * 
     * @param name the entered tool name
     * @return a List of tool objects
     */
    public List <Tool> searchByName(String name);
    
    /**
     * View all checked out tools and when they will
     * be available next.
     * 
     * @return a List of tool objects
     */
    public List <Tool> searchByCheckedOut();

   /**
    * Save a new tool to the database. Category & Brand will be cross-referenced
    * with category and brand tables to ultimately add a category id and brand id.
    * 
    * @param toolName name of tool
    * @param description a brief description of the tool
    * @param brand selected from list of available brands
    * @param category selected from list of available categories
    * @return the new tool object
    */
    public Tool addTool(String toolName, String description, String brand, String imgName, String category);
	
}
