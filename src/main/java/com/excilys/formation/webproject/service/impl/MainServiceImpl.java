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
	private void stopTransaction(Connection cn,String S) {
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
		return cpuDAO.find(id);
	}
	/**
	 * 
	 * @return the size of the table computer
	 */
	@Override
	public Integer getListComputerSize() {
		return cpuDAO.getListSize();	
	}
	/**
	 * 
	 * @param pagewrapper An object countaining the info for the next query
	 */
	@Override
	public void getListComputer(PageWrapper pageWrapper) {
		cpuDAO.getList(pageWrapper);
	}
	/**
	 * 
	 * @param pageWrapper
	 * @return the size of the List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public Integer getListComputerSizeWithName(PageWrapper pageWrapper) {
		return cpuDAO.getListSizeWithName(pageWrapper);
	}
	/**
	 * 
	 * @param pageWrapper
	 * @return a List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public List<Computer> getListComputerWithName(PageWrapper pageWrapper) {
		return cpuDAO.getListWithName(pageWrapper);
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
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on insertion");
		}
		try {
			cpuDAO.create(comp);
		} catch (SQLException e2) {
			try {
				cn.rollback();
			} catch (SQLException e3) {
				stopTransaction(cn," on insertion");
			}
			throw new IllegalStateException("Error on insertion");
		} finally {
			stopTransaction(cn," on insertion");
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
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on edition");
		}
		try {
			cpuDAO.save(comp,id);
		} catch (SQLException e2) {
			try {
				cn.rollback();
			} catch (SQLException e3) {
				stopTransaction(cn," on edition");
			}
			throw new IllegalStateException("Error while setting auto-commit to false on edition");
		} finally {
			stopTransaction(cn," on edition");
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
		}catch (SQLException e) {
			throw new IllegalStateException("Errorcnfactory while setting auto-commit to false on removal");
		}
		try {
			cpuDAO.delete(id);
		} catch (SQLException e2) {
			try {
				cn.rollback();
			} catch (SQLException e3) {
				stopTransaction(cn," on removal");
			}
			throw new IllegalStateException("Error on removal");
		} finally {
			stopTransaction(cn," on removal");
		}	
	}
	/**
	 * @return the Company in the table company matching the id
	 */
	@Override
	public Company findCompanyById(String id) {
		Connection cn = cnFactory.getConnection();
		Company comp = new Company();
		Long idL = Long.decode(id);
		if (idL > 0) comp = cpyDAO.findById(idL);

		cnFactory.closeConnection(cn);
		return comp; 
	}
	/**
	 * 
	 * @return a List<Company> of every Company in the table company
	 */
	@Override
	public List<Company> getListCompany() {
		Connection cn = cnFactory.getConnection();
		ArrayList<Company> list  = (ArrayList<Company>) cpyDAO.getList();
		cnFactory.closeConnection(cn);
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
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on company insertion");
		}
		try {
			cpyDAO.create(comp);
			try {
				cn.rollback();
			} catch (SQLException e2) {
				stopTransaction(cn," on company insertion");
			}
		} catch (SQLException e3) {
			throw new IllegalStateException("Error on company insertion");
		} finally {
			stopTransaction(cn," on company insertion");
		}	
	}
}