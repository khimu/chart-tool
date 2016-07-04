package com.icsm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

public class ConnectionFactory {
	
	
	private String jdbcUrl;
	
	private String username;
	
	private String password;
	
	private String driver;
	
	public ConnectionFactory(String driver, String jdbcUrl, String username, String password){
		this.driver = driver;
		this.jdbcUrl = jdbcUrl;
		this.username = username;
		this.password = password;
	}

	public Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(
					jdbcUrl, username, password);
		} catch (SQLException sqle) {
			throw sqle;
		}
		return conn;
	}

}
