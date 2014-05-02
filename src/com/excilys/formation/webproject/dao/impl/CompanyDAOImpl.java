package com.excilys.formation.webproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.webproject.dao.CompanyDAO;
import com.excilys.formation.webproject.db.impl.ConnectionFactoryImpl;
import com.excilys.formation.webproject.om.Company;

/**
 * 
 * @author excilys
 *
 */
public enum CompanyDAOImpl implements CompanyDAO{

	Singleton ;

	/**
	 * 
	 * @param rs The ResulSet from the query on the database Root
	 * @return A List of Company
	 */
	public List extractFromResultSet(ResultSet rs) throws SQLException{
		ArrayList<Company> liste  = new ArrayList<>();
		
		while (rs.next()) {
			Company p = Company.builder().id(new Long(rs.getLong(1))).name(rs.getString(2)).build();	

			liste.add(p);
		}
		return liste;
	}
	/**
	 * @return The Company in the table company matching the id
	 */
	public Company findById(Long id) {
		if (id==0) return Company.builder().name("").build();	
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		Company company = null;

		try {
			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.prepareStatement("SELECT * FROM company WHERE id = ?;");
			stmt.setString(1,String.valueOf(id));
			rs = stmt.executeQuery();	
			
			if (rs.next()) company = Company.builder().id(id).name(rs.getString("name")).build();				
		
		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(rs,stmt,cn);
		}
		return company;
	}
	/**
	 * @return The Company in the table company matching the name
	 */
	public Company findByName(String name) {
		if (name == "") return Company.builder().build();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;
		Company company = null;

		try {
			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.prepareStatement("SELECT * FROM company WHERE name = ?;");
			stmt.setString(1,name);
			rs = stmt.executeQuery();	
			
			if (rs.next()) company = Company.builder().id(rs.getLong("id")).name(name).build();				
		
		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(rs,stmt,cn);
		}
		return company;
	}
	/**
	 * 
	 * @return A List<Company> of Company in the table company
	 */
	public List getList() {
		ArrayList<Company> liste  = new ArrayList<>();
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = null;

		try {

			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM company;");
			
			liste = (ArrayList<Company>) extractFromResultSet(rs);

		} catch (SQLException e) {
			 throw new IllegalStateException("Error while querying the database");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(rs,stmt,cn);
		}
		return liste;
	}
	/**
	 * 
	 * @param comp A Company to be added in the table company
	 */
	public void insert(Company comp) {
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = null;

		try {		
			cn = ConnectionFactoryImpl.Singleton.getConnection();
			stmt = cn.prepareStatement("INSERT into company(id,name) VALUES(?,?);");

			stmt.setString(1,String.valueOf(comp.getId()));
			stmt.setString(2,comp.getName());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
		 throw new IllegalStateException("Error while querying the database");
		} finally {
			ConnectionFactoryImpl.Singleton.disconnect(rs,stmt,cn);
		}		
	}
}