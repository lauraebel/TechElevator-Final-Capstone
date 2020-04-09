package com.techelevator.model;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class Tool {
	
	private long toolId;
	
	@NotBlank(message = "Tool name is required")
	private String toolName;
	
	@NotBlank(message = "Tool description is required")
	private String toolDescription;
	
	private String toolImgName;
	
	@NotBlank(message = "Tool brand required, if unknown please select OTHER")
	private long toolBrandId;
	
	private List<Long> toolCategoryIds;


	public long getToolId() {
		return toolId;
	}

	public void setToolId(long toolId) {
		this.toolId = toolId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolDescription() {
		return toolDescription;
	}

	public void setToolDescription(String toolDescription) {
		this.toolDescription = toolDescription;
	}

	public String getToolImgName() {
		return toolImgName;
	}

	public void setToolImgName(String toolImgName) {
		this.toolImgName = toolImgName;
	}

	public long getToolBrandId() {
		return toolBrandId;
	}

	public void setToolBrandId(long toolBrandId) {
		this.toolBrandId = toolBrandId;
	}
	
	public List<Long> getToolCategoryIds() {
		return toolCategoryIds;
	}

	public void setToolCategoryIds(List<Long> toolCategoryIds) {
		this.toolCategoryIds = toolCategoryIds;
	}
	
}
