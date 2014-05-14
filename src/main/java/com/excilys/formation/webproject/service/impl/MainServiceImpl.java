package com.excilys.formation.webproject.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.webproject.dao.CompanyDAO;
import com.excilys.formation.webproject.dao.ComputerDAO;
import com.excilys.formation.webproject.db.ConnectionFactory;
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
	private ConnectionFactory cnFactory;
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
		cnFactory.disconnect();
		return comp;
	}
	/**
	 * 
	 * @return the size of the table computer
	 */
	@Override
	public Integer getListComputerSize() {
		Integer size = cpuDAO.getListSize();
		cnFactory.disconnect();
		return size;	
	}
	/**
	 * 
	 * @param pagewrapper An object countaining the info for the next query
	 */
	@Override
	public void getListComputer(PageWrapper pageWrapper) {
		cpuDAO.getList(pageWrapper);
		cnFactory.disconnect();	
	}
	/**
	 * 
	 * @param pageWrapper
	 * @return the size of the List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public Integer getListComputerSizeWithName(PageWrapper pageWrapper) {
		Integer size = cpuDAO.getListSizeWithName(pageWrapper);
		cnFactory.disconnect();
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
		cnFactory.disconnect();
		return liste;
	}
	/**
	 * 
	 * @param comp A Computer to be put in the table computer to be displayed
	 */
	@Override
	public void createComputer(Computer comp) {

		Connection cn = cnFactory.getConnection();

		//Transaction
		try {
			cn.setAutoCommit(false);
			cpuDAO.create(comp);
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e2) {
				throw new IllegalStateException("Could not roll back on creation");
			}
		} finally {
			endTransaction(cn," on creation");
			cnFactory.disconnect();
		}	
	}	
	/**
	 * 
	 * @param comp A Computer to be edited in the table computer
	 * @param id The id of the edited Computer
	 */
	@Override
	public void saveComputer(Computer comp, Long id) {

		Connection cn = cnFactory.getConnection();

		//Transaction
		try {
			cn.setAutoCommit(false);
			cpuDAO.save(comp,id);
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e2) {
				throw new IllegalStateException("Could not roll back on save");
			}
		} finally {
			endTransaction(cn," on save");
			cnFactory.disconnect();
		}	
	}
	/**
	 * 
	 * @param id The id of the Computer to be removed in the table computer
	 */
	@Override
	public void deleteComputer(Long id) {

		Connection cn = cnFactory.getConnection();

		//Transaction
		try {
			cn.setAutoCommit(false);
			cpuDAO.delete(id);
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e2) {
				throw new IllegalStateException("Could not roll back on deletion");
			}
		} finally {
			endTransaction(cn," on deletion");
			cnFactory.disconnect();
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

		cnFactory.disconnect();
		return comp; 
	}
	/**
	 * 
	 * @return a List<Company> of every Company in the table company
	 */
	@Override
	public List<Company> getListCompany() {
		ArrayList<Company> list  = (ArrayList<Company>) cpyDAO.getList();
		cnFactory.disconnect();
		return list; 
	}
	/**
	 * 
	 * @param comp A Computer to be put in the table company
	 */
	@Override
	public void createCompany(Company comp) {

		Connection cn = cnFactory.getConnection();

		//Transaction
		try {
			cn.setAutoCommit(false);
			cpyDAO.create(comp);
			cn.commit();
		} catch (SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e2) {
				throw new IllegalStateException("Could not roll back on save");
			}
		} finally {
			endTransaction(cn," on save");
			cnFactory.disconnect();
		}		
	}
}