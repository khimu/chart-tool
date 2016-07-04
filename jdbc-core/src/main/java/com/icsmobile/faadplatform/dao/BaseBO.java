/*
 * @(#)com.ics.faad.app.entity
 *
 * Copyright 2011-2012 ICS Mobile, Inc. All rights reserved 
 * 
 * JDK version : 6.0
 * Version History : 1.0     February 22, 2011
 */

package com.icsmobile.faadplatform.dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.annotate.JsonFilter;


/**
 * Parent class for all the Business Objects
 * 
 * @author Athir Gillani
 */
@JsonFilter("BOFilter")
public class BaseBO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Date createdOn;
	
	private Date endedOn;
	
	private Date updatedOn;
	
	private Integer createdBy;
	
	private Integer updatedBy;
	
	/**
	 * Builds JSON representation for the Business Object Entity
	 * 
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @return String JSON representation
	 * 
	 */
	public String toJSON() throws JsonGenerationException, JsonMappingException, IOException {
		String obj = "";
		return obj;
	}
	
	/**
	 * @return createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}
	
	/**
	 * @return endedOn
	 */
	public Date getEndedOn() {
		return endedOn;
	}

	/**
	 * @param createdOn
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @param endedOn
	 */
	public void setEndedOn(Date endedOn) {
		this.endedOn = endedOn;
	}
	
	/**
	 * @return updatedOn
	 */
	public Date getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn
	 */
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	/**
	 * 
	 * a toString method that can be used in any class.
	 * uses reflection to dynamically print java class field
	 * values one line at a time.
	 * requires the Apache Commons ToStringBuilder class.
	 * 
	 * @return String
	 *  
	 */
	public String showBean()
	{
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	
	public String getCacheKey(){
		return "";
	}
}
