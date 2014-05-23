package com.excilys.formation.webproject.common;

import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.stereotype.Component;

@Component
public class Closer {

	/**
	 * 
	 * @param stmt
	 */
	public void closeStatement(Statement stmt) {
		try {
			if (stmt != null) stmt.close();
		} catch (Exception e) {
			throw new IllegalStateException("Could not close statement");
		}
	}
	
	/**
	 * 
	 * @param rs
	 */
	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (Exception e) {
			throw new IllegalStateException("Could not close result set");
		} 
	}

}
