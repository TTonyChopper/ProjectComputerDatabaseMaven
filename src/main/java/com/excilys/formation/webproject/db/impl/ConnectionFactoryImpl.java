package com.excilys.formation.webproject.db.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public enum ConnectionFactoryImpl {
	
	Singleton;
 
public final static String JDBC_URL = "jdbc:mysql://localhost:3306/computer-database-db";
public final static String JDBC_USERNAME = "root";
public final static String JDBC_PASSWORD = "root";
	
private BoneCP connectionPool = null;
private static final ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();
 
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
 
public void shutdownConnPool() {
		BoneCP connectionPool = getConnectionPool();
		System.out.println("contextDestroyed....");
		if (connectionPool != null) {
			connectionPool.shutdown(); //this method must be called only once when the application stops.
			//you don't need to call it every time when you get a connection from the Connection Pool
			System.out.println("contextDestroyed.....Connection Pooling shut down!");
		}
}
 
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
 
public void closeStatement(Statement stmt) {
	try {
		if (stmt != null) stmt.close();
	} catch (Exception e) {
		throw new IllegalStateException("Could not close statement");
	}
}
 
public void closeResultSet(ResultSet rs) {
	try {
		if (rs != null) rs.close();
	} catch (Exception e) {
		throw new IllegalStateException("Could not close result set");
	} 
}
 
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

public void disconnect(Statement stmt,ResultSet rs) {
	closeStatement(stmt);
	closeResultSet(rs);
}
 
public BoneCP getConnectionPool() {
	return connectionPool;
}
 
public void setConnectionPool(BoneCP connectionPool) {
	this.connectionPool = connectionPool;
}
 
}