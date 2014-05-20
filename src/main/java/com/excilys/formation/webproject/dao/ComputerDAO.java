package com.excilys.formation.webproject.dao;

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
	 * @return The Computer in the table computer matching the id
	 */
	public Computer find(Long id);
	/**
	 * 
	 * @return
	 */
	public Integer getListSize();	
	/**
	 * 
	 * @return A List<Computer> of Computer in the table computer
	 */
	public List<Computer> getList();
	/**
	 * 
	 * @param pagewrapper
	 * @return
	 */
	public void getList(PageWrapper pagewrapper);
	/**
	 * 
	 * @param pageWrapper
	 * @return
	 */
	public Integer getListSizeWithName(PageWrapper pageWrapper);
	/**
	 * 
	 * @param pageWrapper
	 * @return
	 */
	public List<Computer> getListWithName(PageWrapper pageWrapper);
	/**
	 * 
	 * @param cn
	 * @param comp
	 * @throws SQLException
	 */
	public void create(Computer comp);
	/**
	 * 
	 * @param cn
	 * @param comp
	 * @param id
	 * @throws SQLException
	 */
	public void save(Computer comp,Long id);
	/**
	 * 
	 * @param cn
	 * @param id
	 * @throws SQLException
	 */
	public void delete(Long id);
}