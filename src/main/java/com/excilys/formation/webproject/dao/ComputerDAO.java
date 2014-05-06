package com.excilys.formation.webproject.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.webproject.common.PageWrapper;
import com.excilys.formation.webproject.om.Computer;

/**
 * 
 * @author excilys
 *
 */
public interface ComputerDAO{
	
	/**
	 * 
	 * @param rs The ResulSet from the query on the database Root
	 * @return A List of Computer
	 */
	public List extractFromResultSet(ResultSet rs) throws SQLException;
	
	/**
	 * @return The Computer in the table computer matching the id
	 */
	public Computer find(Connection cn,Long id);
	
	/**
	 * 
	 * @return A List<Computer> of Computer in the table computer
	 */
	public List getList(Connection cn);

	/**
	 * 
	 * @param pagewrapper
	 * @return
	 */
	public void getList(Connection cn,PageWrapper pagewrapper);
	
	/**
	 * 
	 * @param pageWrapper
	 * @return
	 */
	public Integer getListSizeWithName(Connection cn,PageWrapper pageWrapper);
	
	/**
	 * 
	 * @param pageWrapper
	 * @return
	 */
	public List getListWithName(Connection cn,PageWrapper pageWrapper);
	
	/**
	 * 
	 * @param cn
	 * @param comp
	 * @throws SQLException
	 */
	public void insert(Connection cn,Computer comp)throws SQLException;
	
	/**
	 * 
	 * @param cn
	 * @param comp
	 * @param id
	 * @throws SQLException
	 */
	public void edit(Connection cn,Computer comp,Long id)throws SQLException;
	
	/**
	 * 
	 * @param cn
	 * @param id
	 * @throws SQLException
	 */
	public void remove(Connection cn,Long id)throws SQLException;
}