package com.icsmobile.faadplatform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FeaturedAppRowMapper implements RowMapper<FeaturedApp>
{
	public FeaturedApp mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeaturedApp app = new FeaturedApp();
		app.setEndDate(rs.getDate("end_date"));
		app.setStartDate(rs.getDate("start_date"));
		app.setItunesAppId(rs.getString("ua.app_store_id"));
		return app;
	}

}
