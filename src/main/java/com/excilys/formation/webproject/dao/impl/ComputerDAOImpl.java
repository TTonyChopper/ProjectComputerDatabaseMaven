package com.excilys.formation.webproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.webproject.dao.ComputerDAO;
import com.excilys.formation.webproject.db.impl.ConnectionFactoryImpl;
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
public enum ComputerDAOImpl implements ComputerDAO{

	Singleton;	 
	
	/**
	 * 
	 * @param rs The ResulSet from the query on the database Root
	 * @return A List of Computer
	 */
	public List extractFromResultSet(ResultSet rs) throws SQLException{
		ArrayList<Computer> liste  = new ArrayList<>();
		
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
	public Computer find(Connection cn,Long id) {
		ArrayList<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Computer computer = null;

		try {
			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.prepareStatement("SELECT DISTINCT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu "
					  				  +"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id WHERE cpu.id = ?;");
			stmt.setString(1,String.valueOf(id));	
			
			rs = stmt.executeQuery();
			
			liste = (ArrayList<Computer>) extractFromResultSet(rs);
			
			if (liste.size() == 0) computer = null;
			else computer = liste.get(0);
			
		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(stmt,rs);
		}
		return computer;
	}
	/**
	 * 
	 * @return
	 */
	public Integer getListSize(Connection cn) {

		Integer computerListSize = null;
		ResultSet rs = null ;
		Statement stmt = null;	

		try {

			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) as computerlistsize FROM computer");
			
			rs.next();
			computerListSize = rs.getInt("computerListSize"); 

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(stmt,rs);
		}
		return computerListSize;
	}
	/**
	 * 
	 * @return A List<Computer> of Computer in the table computer
	 */
	public List getList(Connection cn) {

		ArrayList<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		Statement stmt = null;

		try {

			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu "
								  +"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id;");
			
			liste = (ArrayList<Computer>) extractFromResultSet(rs);

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(stmt,rs);
		}
		return liste;
	}
	/**
	 * 
	 * @param pagewrapper
	 */
	public void getList(Connection cn,PageWrapper pageWrapper) {

		ArrayList<Computer> liste  = new ArrayList<>(25);
		ResultSet rs = null ;
		PreparedStatement stmt = null;

		try {

			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.prepareStatement("SELECT DISTINCT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu "
								  	  +"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id ORDER BY "+pageWrapper.getFieldOrder()+" "+pageWrapper.getOrder()+", cpu.name ASC LIMIT ?,?;");
			
			stmt.setString(1,String.valueOf(25*(pageWrapper.getPageNumber()-1)));
			stmt.setString(2,String.valueOf(pageWrapper.getPerPage()));
			
			rs = stmt.executeQuery();
			
			liste = (ArrayList<Computer>) extractFromResultSet(rs);
			pageWrapper.setComputerList(liste);

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(stmt,rs);
		}
	}
	/**
	 * 
	 * @param pageWrapper
	 * @return
	 */
	public Integer getListSizeWithName(Connection cn,PageWrapper pageWrapper) {	

		Integer computerListSize = null;
		ResultSet rs = null ;
		PreparedStatement stmt = null;

		try {
			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.prepareStatement("SELECT COUNT(*) AS computerListSize, cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu " 
									  +"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id WHERE cpu.name LIKE ? OR cpy.name LIKE ? ");
			stmt.setString(1,"%"+pageWrapper.getNameFilter()+"%");
			stmt.setString(2,"%"+pageWrapper.getNameFilter()+"%");
			rs = stmt.executeQuery();		

			while(rs.next()){
			computerListSize = rs.getInt("computerListSize"); 
			}

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(stmt,rs);
		}
		return computerListSize;	
	}
	/**
	 * 
	 * @return A List<Computer> of Computer in the table computer containing namefilter
	 */
	public List getListWithName(Connection cn,PageWrapper pageWrapper) {	

		ArrayList<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		PreparedStatement stmt = null;

		try {
			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.prepareStatement("SELECT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu " 
									  +"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id WHERE cpu.name LIKE ? OR cpy.name LIKE ? "
									  +"ORDER BY "+pageWrapper.getFieldOrder()+" "+pageWrapper.getOrder()+", cpu.name ASC LIMIT ?,?;");
			stmt.setString(1,"%"+pageWrapper.getNameFilter()+"%");
			stmt.setString(2,"%"+pageWrapper.getNameFilter()+"%");
			stmt.setString(3,String.valueOf(25*(pageWrapper.getPageNumber()-1)));
			stmt.setString(4,String.valueOf(pageWrapper.getPerPage()));

			rs = stmt.executeQuery();		
			
			liste = (ArrayList<Computer>) extractFromResultSet(rs);		
			pageWrapper.setComputerList(liste);

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(stmt,rs);
		}
		return liste;	
	}
	/**
	 * 
	 * @param cn
	 * @param comp
	 * @throws SQLException
	 */
	public void insert(Connection cn,Computer comp) throws SQLException{

		PreparedStatement stmt = null;

		cn = ConnectionFactoryImpl.Singleton.getConnection();
		String companyname = comp.getCompany().getName();
		if (comp.getCompany().getName()==null) stmt = cn.prepareStatement("INSERT into computer(name,introduced,discontinued) VALUES (?,?,?);");
		else stmt = cn.prepareStatement("INSERT into computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?);"); 
			
		stmt.setString(1,comp.getName());
		stmt.setString(2,String.valueOf(comp.getIntroduced()));
		if (companyname != null) {
		stmt.setString(3,String.valueOf(comp.getDiscontinued()));
		stmt.setString(4,String.valueOf(comp.getCompany().getId()));
		} else stmt.setString(3,String.valueOf(comp.getDiscontinued()));
			
		stmt.executeUpdate();

		ConnectionFactoryImpl.Singleton.closeStatement(stmt);	
	}
	/**
	 * 
	 * @param cn
	 * @param comp
	 * @param id
	 * @throws SQLException
	 */
	public void edit(Connection cn,Computer comp,Long id) throws SQLException{

		PreparedStatement stmt = null;
			
		Long companyid = comp.getCompany().getId();
		if (comp.getCompany().getId()==null) stmt = cn.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=? WHERE id = ?");
		else stmt = cn.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?");
			
		stmt.setString(1,comp.getName());
		stmt.setString(2,String.valueOf(comp.getIntroduced()));
		stmt.setString(3,String.valueOf(comp.getDiscontinued()));
		if (companyid != null) {
			stmt.setString(4,String.valueOf(companyid));
			stmt.setString(5,String.valueOf(id));
		}else stmt.setString(4,String.valueOf(id));
						
		stmt.executeUpdate();
		ConnectionFactoryImpl.Singleton.closeStatement(stmt);
	}
	/**
	 * 
	 * @param cn
	 * @param id
	 * @throws SQLException
	 */
	public void remove(Connection cn,Long id) throws SQLException{

		PreparedStatement stmt = null;

		cn = ConnectionFactoryImpl.Singleton.getConnection();
		stmt = cn.prepareStatement("DELETE FROM computer WHERE id = ?;");

		stmt.setString(1,String.valueOf(id));
			
		stmt.executeUpdate();

		ConnectionFactoryImpl.Singleton.closeStatement(stmt);
	}
}