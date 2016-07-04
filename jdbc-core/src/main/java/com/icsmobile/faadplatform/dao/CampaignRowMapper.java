package com.icsmobile.faadplatform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CampaignRowMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CampaignDTO customer = new CampaignDTO();
		customer.setApplicationName(rs.getString("name"));
		customer.setDescription(rs.getString("description"));
		customer.setStartDate(rs.getDate("start_date"));
		customer.setEndDate(rs.getDate("endDate"));
		return customer;
	}

}
