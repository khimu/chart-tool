/*
 * @(#)com.icsmobile.faadplatform.dao
 * 
 * Copyright 2011-2012 ICS Mobile, Inc. All rights reserved 
 *
 *
 * JDK version : 6.0
 * Version History : 1.0    Feb 28, 2011
 */
package com.icsmobile.faadplatform.dao;


import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Its the parent Dao class of the all the daos defined in the FAAD Server.
 *
 * @author Athir Gillani
 */
public class BaseDao extends SimpleJdbcDaoSupport {

	//common logger for all the classes in the dao layer
	protected Logger logger = null;
	
}
