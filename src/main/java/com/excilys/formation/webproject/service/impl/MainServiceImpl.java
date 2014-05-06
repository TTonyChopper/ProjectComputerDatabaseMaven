package com.excilys.formation.webproject.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
public enum MainServiceImpl implements MainService{
	
	Singleton;

	private void abortTransaction(Connection cn,String S) {
		try {
			cn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new IllegalStateException("Error while setting back auto-commit to true"+S);
		}
		finally {
			ConnectionFactoryImpl.Singleton.closeConnection(cn);
		}	
	}	
	/**
	 * @return the Computer in the table computer matching the id
	 */
	@Override
	public Computer findComputer(Long id) {
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		Computer comp  = ComputerDAOImpl.Singleton.find(cn,id);
		ConnectionFactoryImpl.Singleton.closeConnection(cn);
		return comp;
	}
	
	/**
	 * 
	 * @return the size of the table computer
	 */
	@Override
	public Integer getListComputerSize() {
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		Integer size  = ComputerDAOImpl.Singleton.getListSize(cn);
		ConnectionFactoryImpl.Singleton.closeConnection(cn);
		return size; 	
	}
	
	/**
	 * 
	 * @param pagewrapper An object countaining the info for the next query
	 */
	@Override
	public void getListComputer(PageWrapper pageWrapper) {
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		ComputerDAOImpl.Singleton.getList(cn,pageWrapper);
		ConnectionFactoryImpl.Singleton.closeConnection(cn);	
	}
	
	/**
	 * 
	 * @param pageWrapper
	 * @return the size of the List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public Integer getListComputerSizeWithName(PageWrapper pageWrapper) {
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		Integer size  = ComputerDAOImpl.Singleton.getListSizeWithName(cn,pageWrapper);
		ConnectionFactoryImpl.Singleton.closeConnection(cn);
		return size; 
	}
	
	/**
	 * 
	 * @param pageWrapper
	 * @return a List<Computer> of Computer in the table computer to be displayed
	 */
	@Override
	public List getListComputerWithName(PageWrapper pageWrapper) {
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		ArrayList<Computer> list  = (ArrayList<Computer>) ComputerDAOImpl.Singleton.getListWithName(cn,pageWrapper);
		ConnectionFactoryImpl.Singleton.closeConnection(cn);
		return list; 
	}

	/**
	 * 
	 * @param comp A Computer to be put in the table computer to be displayed
	 */
	@Override
	public void insertComputer(Computer comp) {
		
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		
		//Transaction
		try {
		cn.setAutoCommit(false);
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on insertion");
		}
		try {
			ComputerDAOImpl.Singleton.insert(cn,comp);
			try {
				cn.rollback();
			} catch (SQLException e2) {
				abortTransaction(cn," on insertion");
			}
		} catch (SQLException e3) {
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
		
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		
		//Transaction
		try {
		cn.setAutoCommit(false);
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on edition");
		}
		try {
			ComputerDAOImpl.Singleton.edit(cn,comp,id);
			try {
				cn.rollback();
			} catch (SQLException e2) {
				abortTransaction(cn," on edition");
			}
		} catch (SQLException e3) {
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
		
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		
		//Transaction
		try {
		cn.setAutoCommit(false);
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on removal");
		}
		try {
			ComputerDAOImpl.Singleton.remove(cn,id);
			try {
				cn.rollback();
			} catch (SQLException e2) {
				abortTransaction(cn," on removal");
			}
		} catch (SQLException e3) {
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
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		Company comp  = CompanyDAOImpl.Singleton.findById(cn,id);
		ConnectionFactoryImpl.Singleton.closeConnection(cn);
		return comp; 
	}
	
	/**
	 * @return the Company in the table company matching the name
	 */
	@Override
	public Company findCompanyByName(String name) {
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		Company comp  = CompanyDAOImpl.Singleton.findByName(cn,name);
		ConnectionFactoryImpl.Singleton.closeConnection(cn);
		return comp;
	}	
	
	/**
	 * 
	 * @return a List<Company> of every Company in the table company
	 */
	@Override
	public List getListCompany() {
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		ArrayList<Company> list  = (ArrayList<Company>) CompanyDAOImpl.Singleton.getList(cn);
		ConnectionFactoryImpl.Singleton.closeConnection(cn);
		return list; 
	}
	
	/**
	 * 
	 * @param comp A Computer to be put in the table company
	 */
	@Override
	public void insertCompany(Company comp) {
		
		Connection cn = ConnectionFactoryImpl.Singleton.getConnection();
		
		//Transaction
		try {
		cn.setAutoCommit(false);
		}catch (SQLException e) {
			throw new IllegalStateException("Error while setting auto-commit to false on company insertion");
		}
		try {
			CompanyDAOImpl.Singleton.insert(cn,comp);
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