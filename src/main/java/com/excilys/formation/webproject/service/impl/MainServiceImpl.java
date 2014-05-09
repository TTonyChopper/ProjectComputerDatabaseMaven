package com.excilys.formation.webproject.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.webproject.dao.impl.CompanyDAOImpl;
import com.excilys.formation.webproject.dao.impl.ComputerDAOImpl;
import com.excilys.formation.webproject.db.impl.ConnectionFactoryImpl;
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
	private ConnectionFactoryImpl cnFactory;
	@Autowired
	private ComputerDAOImpl cpuDAO;
	@Autowired
	private CompanyDAOImpl cpyDAO;

	private void abortTransaction(Connection cn,String S) {
		try {
			cn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new IllegalStateException("Error while setting back auto-commit to true"+S);
		}
		finally {
			cnFactory.closeConnection(cn);
		}	
	}	
	/**
	 * @return the Computer in the table computer matching the id
	 */
	@Override
	public Computer findComputer(Long id) {
		Connection cn = cnFactory.getConnection();
		Computer comp  = cpuDAO.find(cn,id);
		cnFactory.closeConnection(cn);
		return comp;
	}
	
	/**
	 * 
	 * @return the size of the table computer
	 */
	@Override
	public Integer getListComputerSize() {
		Connection cn = cnFactory.getConnection();
		Integer size  = cpuDAO.getListSize(cn);
		cnFactory.closeConnection(cn);
		return size; 	
	}
	
	/**
	 * 
	 * @param pagewrapper An object countaining the info for the next query
	 */
	@Override
	public void getListComputer(PageWrapper pageWrapper) {
		Connection cn = cnFactory.getConnection();
		cpuDAO.getList(cn,pageWrapper);
		cnFactory.closeConnection(cn);	
	}
	
	/**
	 * 
	 * @param pageWrapper
	 * @return the size of the List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public Integer getListComputerSizeWithName(PageWrapper pageWrapper) {
		Connection cn = cnFactory.getConnection();
		Integer size  = cpuDAO.getListSizeWithName(cn,pageWrapper);
		cnFactory.closeConnection(cn);
		return size; 
	}
	
	/**
	 * 
	 * @param pageWrapper
	 * @return a List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public List getListComputerWithName(PageWrapper pageWrapper) {
		Connection cn = cnFactory.getConnection();
		ArrayList<Computer> list  = (ArrayList<Computer>) cpuDAO.getListWithName(cn,pageWrapper);
		cnFactory.closeConnection(cn);
		return list; 
	}

	/**
	 * 
	 * @param comp A Computer to be put in the table computer to be displayed
	 */
	@Override
	public void insertComputer(Computer comp) {
		
		System.out.println("I m IN !!");
		System.out.println(comp.toString());
		Connection cn = cnFactory.getConnection();
		
		//Transaction
		try {
		cn.setAutoCommit(false);
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on insertion");
		}
		try {
			cpuDAO.insert(cn,comp);
		} catch (SQLException e2) {
			try {
				cn.rollback();
			} catch (SQLException e3) {
				abortTransaction(cn," on insertion");
			}
			throw new IllegalStateException("Error on insertion");
		} finally {
			abortTransaction(cn," on insertion");
		}
	}	
	
	/**
	 * 
	 * @param comp A Computer to be edited in the table computer
	 * @param id The id of the edited Computer
	 */
	@Override
	public void editComputer(Computer comp, Long id) {
		
		Connection cn = cnFactory.getConnection();
		
		//Transaction
		try {
		cn.setAutoCommit(false);
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on edition");
		}
		try {
			cpuDAO.edit(cn,comp,id);
		} catch (SQLException e2) {
			try {
				cn.rollback();
			} catch (SQLException e3) {
				abortTransaction(cn," on edition");
			}
			throw new IllegalStateException("Error while setting auto-commit to false on edition");
		} finally {
			abortTransaction(cn," on edition");
		}	
	}
	
	/**
	 * 
	 * @param id The id of the Computer to be removed in the table computer
	 */
	@Override
	public void removeComputer(Long id) {
		
		Connection cn = cnFactory.getConnection();
		
		//Transaction
		try {
		cn.setAutoCommit(false);
		}catch (SQLException e) {
			throw new IllegalStateException("Errorcnfactory while setting auto-commit to false on removal");
		}
		try {
			cpuDAO.remove(cn,id);
		} catch (SQLException e2) {
			try {
				cn.rollback();
			} catch (SQLException e3) {
				abortTransaction(cn," on removal");
			}
			throw new IllegalStateException("Error on removal");
		} finally {
			abortTransaction(cn," on removal");
		}	
	}
	
	/**
	 * @return the Company in the table company matching the id
	 */
	@Override
	public Company findCompanyById(Long id) {
		Connection cn = cnFactory.getConnection();
		Company comp  = cpyDAO.findById(cn,id);
		System.out.println("company found :"+comp);
		cnFactory.closeConnection(cn);
		return comp; 
	}
	
	/**
	 * @return the Company in the table company matching the name
	 */
	@Override
	public Company findCompanyByName(String name) {
		Connection cn = cnFactory.getConnection();
		Company comp  = cpyDAO.findByName(cn,name);
		cnFactory.closeConnection(cn);
		return comp;
	}	
	
	/**
	 * 
	 * @return a List<Company> of every Company in the table company
	 */
	@Override
	public List getListCompany() {
		Connection cn = cnFactory.getConnection();
		ArrayList<Company> list  = (ArrayList<Company>) cpyDAO.getList(cn);
		cnFactory.closeConnection(cn);
		return list; 
	}
	
	/**
	 * 
	 * @param comp A Computer to be put in the table company
	 */
	@Override
	public void insertCompany(Company comp) {
		
		Connection cn = cnFactory.getConnection();
		
		//Transaction
		try {
		cn.setAutoCommit(false);
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on company insertion");
		}
		try {
			cpyDAO.insert(cn,comp);
			try {
				cn.rollback();
			} catch (SQLException e2) {
				abortTransaction(cn," on company insertion");
			}
		} catch (SQLException e3) {
			throw new IllegalStateException("Error on company insertion");
		} finally {
			abortTransaction(cn," on company insertion");
		}	
	}
}