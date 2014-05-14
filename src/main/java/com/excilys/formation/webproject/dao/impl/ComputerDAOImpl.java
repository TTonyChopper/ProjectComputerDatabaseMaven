package com.excilys.formation.webproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.webproject.dao.ComputerDAO;
import com.excilys.formation.webproject.db.ConnectionFactory;
import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.om.Computer.CpuBuilder;
import com.excilys.formation.webproject.common.PageWrapper;

/**
 * attributenumber : associates an Integer to any field of Computer
 *  	-Long id : 0;
 *  	-String	name : 1;
 *  	-Timestamp introduced : 2;
 *  	-Timestamp discontinued : 3;
 *  	-Company company : 4;
 * 
 * @author excilys
 *
 */
@Repository
public class ComputerDAOImpl implements ComputerDAO{

	@Autowired
	private ConnectionFactory cnFactory;

	/**
	 * 
	 * @param rs The ResulSet from the query on the database Root
	 * @return A List of Computer
	 */
	private List<Computer> extractFromResultSet(ResultSet rs) throws SQLException{
		List<Computer> liste  = new ArrayList<>();
		while ((rs != null)&&rs.next()) {
			CpuBuilder b = Computer.builder().id(new Long(rs.getLong(1))).name(rs.getString(2));
			try {
				b.introduced(rs.getTimestamp(3));
			}catch (java.sql.SQLException e) {
				//System.out.println("Timestamp introduced Null on " + b.getName());
			}
			try {
				b.discontinued(rs.getTimestamp(4));
			}catch (java.sql.SQLException e) {
				//System.out.println("Timestamp discontinued Null on " + b.getName());
			}		
			b.company(new Company.CpyBuilder().id(new Long(rs.getLong(5))).name(rs.getString(6)).build());				
			liste.add(b.build());
		}
		return liste;
	}
	/**
	 * @return The Computer in the table computer matching the id
	 */
	@Override
	public Computer find(Long id) {
		List<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Computer computer = null;
		Connection cn = null;

		try {
			cn = cnFactory.getConnection();
			stmt = cn.prepareStatement("SELECT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu "
					+"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id WHERE cpu.id = ?");
			stmt.setLong(1,id);	

			rs = stmt.executeQuery();

			liste = (ArrayList<Computer>) extractFromResultSet(rs);

			if (liste.size() == 0) computer = null;
			else computer = liste.get(0);
		
		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSetlk,l");
		} finally {
			cnFactory.closeResultSet(rs);
			cnFactory.closeStatement(stmt);
		}
		return computer;
	}
	/**
	 * 
	 * @return
	 */
	@Override
	public Integer getListSize() {

		Integer computerListSize = null;
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = null;

		try {

			cn = cnFactory.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) as computerlistsize FROM computer");

			while(rs.next()){
				computerListSize = rs.getInt("computerListSize"); 
			}

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			cnFactory.closeResultSet(rs);
			cnFactory.closeStatement(stmt);
		}
		return computerListSize;
	}
	/**
	 * 
	 * @return A List<Computer> of Computer in the table computer
	 */
	@Override
	public List<Computer> getList() {

		List<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = null;
		
		try {

			cn = cnFactory.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu "
					+"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id");

			liste = (ArrayList<Computer>) extractFromResultSet(rs);

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			cnFactory.closeResultSet(rs);
			cnFactory.closeStatement(stmt);
		}
		return liste;
	}
	/**
	 * 
	 * @param pagewrapper
	 */
	@Override
	public void getList(PageWrapper pageWrapper) {

		List<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;

		try {

			cn = cnFactory.getConnection();
			stmt = cn.prepareStatement("SELECT DISTINCT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu "
					+"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id ORDER BY "+pageWrapper.getFieldOrder()+" "+pageWrapper.getOrder()+", cpu.name ASC LIMIT ?,?");

			stmt.setLong(1,pageWrapper.getPerPage()*(pageWrapper.getPageNumber()-1));
			stmt.setLong(2,pageWrapper.getPerPage());

			rs = stmt.executeQuery();

			liste = (ArrayList<Computer>) extractFromResultSet(rs);
			pageWrapper.setComputerList(liste);

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			cnFactory.closeResultSet(rs);
			cnFactory.closeStatement(stmt);
		}
	}
	/**
	 * 
	 * @param pageWrapper
	 * @return
	 */
	@Override
	public Integer getListSizeWithName(PageWrapper pageWrapper) {	

		Integer computerListSize = null;
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;

		try {
			cn = cnFactory.getConnection();
			stmt = cn.prepareStatement("SELECT COUNT(*) AS computerListSize, cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu " 
					+"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id WHERE cpu.name LIKE ? OR cpy.name LIKE ?");
			stmt.setString(1,"%"+pageWrapper.getNameFilter()+"%");
			stmt.setString(2,"%"+pageWrapper.getNameFilter()+"%");
			rs = stmt.executeQuery();		

			while(rs.next()){
				computerListSize = rs.getInt("computerListSize"); 
			}

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			cnFactory.closeResultSet(rs);
			cnFactory.closeStatement(stmt);
		}
		return computerListSize;	
	}
	/**
	 * 
	 * @return A List<Computer> of Computer in the table computer containing namefilter
	 */
	@Override
	public List<Computer> getListWithName(PageWrapper pageWrapper) {	

		List<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;

		try {
			cn = cnFactory.getConnection();
			stmt = cn.prepareStatement("SELECT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu " 
					+"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id WHERE cpu.name LIKE ? OR cpy.name LIKE ? "
					+"ORDER BY "+pageWrapper.getFieldOrder()+" "+pageWrapper.getOrder()+", cpu.name ASC LIMIT ?,?");
			
			stmt.setString(1,"%"+pageWrapper.getNameFilter()+"%");
			stmt.setString(2,"%"+pageWrapper.getNameFilter()+"%");
			stmt.setLong(3,pageWrapper.getPerPage()*(pageWrapper.getPageNumber()-1));
			stmt.setLong(4,pageWrapper.getPerPage());

			rs = stmt.executeQuery();		

			liste = (ArrayList<Computer>) extractFromResultSet(rs);		
			pageWrapper.setComputerList(liste);

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			cnFactory.closeResultSet(rs);
			cnFactory.closeStatement(stmt);
		}
		return liste;	
	}
	/**
	 * 
	 * @param cn
	 * @param comp
	 * @throws SQLException
	 */
	@Override
	public void create(Computer comp) throws SQLException{

		Connection cn = cnFactory.getConnection();
		Long companyid = comp.getCompany().getId();
		PreparedStatement stmt = cn.prepareStatement("INSERT into computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)"); 

		stmt.setString(1,comp.getName());
		stmt.setTimestamp(2,comp.getIntroduced());
		stmt.setTimestamp(3,comp.getDiscontinued());
		if (companyid == null) stmt.setNull(4,Types.NULL);
		else stmt.setLong(4,companyid);

		stmt.executeUpdate();
		cnFactory.closeStatement(stmt);	
	}
	/**
	 * 
	 * @param cn
	 * @param comp
	 * @param id
	 * @throws SQLException
	 */
	@Override
	public void save(Computer comp,Long id) throws SQLException{

		Connection cn = cnFactory.getConnection();
		Long companyid = comp.getCompany().getId();
		PreparedStatement stmt = cn.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?");

		stmt.setString(1,comp.getName());
		stmt.setTimestamp(2,comp.getIntroduced());
		stmt.setTimestamp(3,comp.getDiscontinued());
		if (companyid == null) stmt.setNull(4,Types.NULL);
		else stmt.setLong(4,companyid);
		stmt.setLong(5,id);

		stmt.executeUpdate();
		cnFactory.closeStatement(stmt);
	}
	/**
	 * 
	 * @param cn
	 * @param id
	 * @throws SQLException
	 */
	@Override
	public void delete(Long id) throws SQLException{

		Connection cn = cnFactory.getConnection();	
		PreparedStatement stmt = cn.prepareStatement("DELETE FROM computer WHERE id = ?");
		
		stmt.setLong(1,id);

		stmt.executeUpdate();
		cnFactory.closeStatement(stmt);
	}
}