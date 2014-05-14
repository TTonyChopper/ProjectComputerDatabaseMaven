package com.excilys.formation.webproject.db.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.excilys.formation.webproject.db.ConnectionFactory;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * 
 * @author excilys
 *
 */
@Component
public class ConnectionFactoryImpl implements ConnectionFactory {

	private final static String JDBC_URL = "jdbc:mysql://localhost:3306/computer-database-db";
	private final static String JDBC_USERNAME = "root";
	private final static String JDBC_PASSWORD = "root";

	private BoneCP connectionPool = null;
	private static final ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();

	/**
	 * 
	 */
	private void configureConnPool() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Could not find the driver for jdbc");
		}
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(JDBC_URL);
		config.setUsername(JDBC_USERNAME);
		config.setPassword(JDBC_PASSWORD);
		config.setMinConnectionsPerPartition(5);   
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(2); //2*5 = 10 connection will be available
		config.setLazyInit(true);
		try {
			connectionPool = new BoneCP(config); // setup the connection pool
		} catch (SQLException e) {
			throw new RuntimeException("Could not configure the connection pool");
		}
		System.out.println("contextInitialized.....Connection Pooling is configured");
		System.out.println("Total connections ==> " + connectionPool.getTotalCreatedConnections());
		setConnectionPool(connectionPool);
	}
	/**
	 * 
	 * @return
	 */
	@Override
	public Connection getConnection() {

		Connection conn = null;

		//first connection ?
		if (connectionPool ==null) configureConnPool();

		if(threadConnection.get()==null){	
			try {
				conn = getConnectionPool().getConnection();
			} catch (SQLException e) {
				throw new RuntimeException("Could not get a connection");
			}
			threadConnection.set(conn);
		}
		return threadConnection.get();
	}
	/**
	 * 
	 * @param stmt
	 */
	@Override
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
	@Override
	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) rs.close();
		} catch (Exception e) {
			throw new IllegalStateException("Could not close result set");
		} 
	}
	/**
	 * 
	 * @param conn
	 */
	@Override
	public void closeConnection(Connection conn) {
		if(threadConnection.get()!=null){
			try {
				threadConnection.get().close();
			} catch (SQLException e) {
				throw new IllegalStateException("Could not close connection");
			} finally{
				threadConnection.remove();
			}
		}
	}
	/**
	 * 
	 * @param stmt
	 * @param rs
	 */
	@Override
	public void disconnect(Statement stmt,Connection cn) {
		closeStatement(stmt);
		closeConnection(cn);
	}
	/**
	 * 
	 * @param stmt
	 * @param rs
	 * @param cn
	 */
	@Override
	public void disconnect(Statement stmt,ResultSet rs,Connection cn) {
		closeStatement(stmt);
		closeResultSet(rs);
		closeConnection(cn);
	}
	/**
	 * 
	 * @return
	 */
	@Override
	public BoneCP getConnectionPool() {
		return connectionPool;
	}
	/**
	 * 
	 * @param connectionPool
	 */
	@Override
	public void setConnectionPool(BoneCP connectionPool) {
		this.connectionPool = connectionPool;
	}
}