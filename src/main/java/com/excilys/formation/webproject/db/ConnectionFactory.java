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
	 * @param conn
	 */
	public void closeConnection(Connection conn);
	/**
	 * 
	 * @param stmt
	 * @param rs
	 * @param conn
	 */
	public void disconnect(Statement stmt,Connection conn);
	/**
	 * 
	 * @param stmt
	 * @param rs
	 * @param conn
	 */
	public void disconnect(Statement stmt,ResultSet rs,Connection conn);
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