package com.excilys.formation.webproject.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.webproject.dao.CompanyDAO;
import com.excilys.formation.webproject.dao.ComputerDAO;
import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.MainService;
import com.excilys.formation.webproject.common.PageWrapper;


/**
 * 
 * @author excilys
 *
 */
@Service
public class MainServiceImpl implements MainService{

	@Autowired
	private DataSourceTransactionManager tManager;
	@Autowired
	private ComputerDAO cpuDAO;
	@Autowired
	private CompanyDAO cpyDAO;

	/**
	 * 
	 * @param cn
	 * @param S
	 */
	private void endTransaction(Connection cn,String S) {
		try {
			cn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new IllegalStateException("Error while setting back auto-commit to true"+S);
		}	
	}	
	/**
	 * @return the Computer in the table computer matching the id
	 */
	@Override
	public Computer findComputer(Long id) {
		Computer comp = cpuDAO.find(id);
			try {
				DataSourceUtils.getConnection(tManager.getDataSource()).close();
			} catch (SQLException e) {
				throw new IllegalStateException("Could not close connection");
			}
		return comp;
	}
	/**
	 * 
	 * @return the size of the table computer
	 */
	@Override
	public Integer getListComputerSize() {
		Integer size = cpuDAO.getListSize();
		try {
			DataSourceUtils.getConnection(tManager.getDataSource()).close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}
		return size;	
	}
	/**
	 * 
	 * @param pagewrapper An object countaining the info for the next query
	 */
	@Override
	public void getListComputer(PageWrapper pageWrapper) {
		cpuDAO.getList(pageWrapper);
		try {
			DataSourceUtils.getConnection(tManager.getDataSource()).close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * 
	 * @param pageWrapper
	 * @return the size of the List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public Integer getListComputerSizeWithName(PageWrapper pageWrapper) {
		Integer size = cpuDAO.getListSizeWithName(pageWrapper);
		try {
			DataSourceUtils.getConnection(tManager.getDataSource()).close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}
		return size;
	}
	/**
	 * 
	 * @param pageWrapper
	 * @return a List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public List<Computer> getListComputerWithName(PageWrapper pageWrapper) {
		List<Computer> liste = cpuDAO.getListWithName(pageWrapper);
		try {
			DataSourceUtils.getConnection(tManager.getDataSource()).close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}
		return liste;
	}
	/**
	 * 
	 * @param comp A Computer to be put in the table computer to be displayed
	 */
	@Transactional
	@Override
	public void createComputer(Computer comp) {
	
		Connection cn=null;
		cn = DataSourceUtils.getConnection(tManager.getDataSource());

		//Transaction
		try {
			cpuDAO.create(comp);
			
		}catch(SQLException e){
			throw new RuntimeException("rollback on creation");
		}
			endTransaction(cn," on creation");
		try {
				cn.close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}	
		throw new RuntimeException("rollback on creation");
	}	
	/**
	 * 
	 * @param comp A Computer to be edited in the table computer
	 * @param id The id of the edited Computer
	 */
	@Transactional
	@Override
	public void saveComputer(Computer comp, Long id) {

		Connection cn = DataSourceUtils.getConnection(tManager.getDataSource());

		//Transaction
		try {
			cpuDAO.save(comp,id);
		}catch(SQLException e){
			throw new RuntimeException("rollback on save");
		}
			endTransaction(cn," on save");
		try {
				cn.close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}
	}
	/**
	 * 
	 * @param id The id of the Computer to be removed in the table computer
	 */
	@Transactional
	@Override
	public void deleteComputer(Long id) {

		Connection cn = DataSourceUtils.getConnection(tManager.getDataSource());

		//Transaction
		try {
			cpuDAO.delete(id);
		}catch(SQLException e){
			throw new RuntimeException("rollback on deletion");
		}
			endTransaction(cn," on deletion");
		try {
				cn.close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}
	}
	/**
	 * @return the Company in the table company matching the id
	 */
	@Override
	public Company findCompanyById(String id) {
		Company comp = new Company();
		Long idL = Long.decode(id);
		if (idL > 0) comp = cpyDAO.findById(idL);

		try {
			DataSourceUtils.getConnection(tManager.getDataSource()).close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}
		return comp; 
	}
	/**
	 * 
	 * @return a List<Company> of every Company in the table company
	 */
	@Override
	public List<Company> getListCompany() {
		ArrayList<Company> list  = (ArrayList<Company>) cpyDAO.getList();
		try {
			DataSourceUtils.getConnection(tManager.getDataSource()).close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}
		return list; 
	}
	/**
	 * 
	 * @param comp A Computer to be put in the table company
	 */
	@Transactional
	@Override
	public void createCompany(Company comp) {

		Connection cn = DataSourceUtils.getConnection(tManager.getDataSource());

		//Transaction
		try {
		cpyDAO.create(comp);
		}catch(SQLException e){
			throw new RuntimeException("rollback on creation");
		}
		endTransaction(cn," on save");
		try {
			cn.close();
		} catch (SQLException e) {
			throw new IllegalStateException("Could not close connection");
		}
	}		
}