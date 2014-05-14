package com.excilys.formation.webproject.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.jolbox.bonecp.BoneCP;

/**
 * 
 * @author excilys
 *
 */
public interface ConnectionFactory {

	/**
	 * 
	 * @return
	 */
	public Connection getConnection();
	/**
	 * 
	 * @param stmt
	 */
	public void closeStatement(Statement stmt);
	/**
	 * 
	 * @param rs
	 */
	public void closeResultSet(ResultSet rs);
	/**
	 * 
	 */
	public void disconnect();
	/**
	 * 
	 * @return
	 */
	public BoneCP getConnectionPool();
	/**
	 * 
	 * @param connectionPool
	 */
	public void setConnectionPool(BoneCP connectionPool);

}