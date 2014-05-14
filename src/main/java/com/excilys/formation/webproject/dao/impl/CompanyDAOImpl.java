package com.excilys.formation.webproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.webproject.dao.CompanyDAO;
import com.excilys.formation.webproject.db.ConnectionFactory;
import com.excilys.formation.webproject.om.Company;

/**
 * 
 * @author excilys
 *
 */
@Repository
public class CompanyDAOImpl implements CompanyDAO{

	@Autowired
	private ConnectionFactory cnFactory;

	/**
	 * 
	 * @param rs The ResulSet from the query on the database Root
	 * @return A List of Company
	 */
	private List<Company> extractFromResultSet(ResultSet rs) throws SQLException{
		List<Company> liste  = new ArrayList<>();

		while (rs.next()) {
			Company p = Company.builder().id(new Long(rs.getLong(1))).name(rs.getString(2)).build();	

			liste.add(p);
		}
		return liste;
	}
	/**
	 * @return The Company in the table company matching the id
	 */
	@Override
	public Company findById(Long id) {
		if (id==0) return Company.builder().name("").build();	
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Company company = new Company();
		Connection cn = null;

		try {
			cn = cnFactory.getConnection();
			stmt = cn.prepareStatement("SELECT * FROM company WHERE id = ?;");
			stmt.setLong(1,id);

			rs = stmt.executeQuery();	

			while(rs.next()){
				company = Company.builder().id(id).name(rs.getString("name")).build();				
			}

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			cnFactory.disconnect(stmt,rs,cn);
		}
		return company;
	}
	/**
	 * @return The Company in the table company matching the name
	 */
	@Override
	public Company findByName(String name) {
		if (name == "") return Company.builder().build();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Company company = new Company();
		Connection cn = null;

		try {
			cn = cnFactory.getConnection();
			stmt = cn.prepareStatement("SELECT * FROM company WHERE name = ?;");
			stmt.setString(1,name);
			rs = stmt.executeQuery();	

			while(rs.next()){
				company = Company.builder().id(rs.getLong("id")).name(name).build();				
			}

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			cnFactory.disconnect(stmt,rs,cn);
		}
		return company;
	}
	/**
	 * 
	 * @return A List<Company> of Company in the table company
	 */
	@Override
	public List<Company> getList() {
		List<Company> liste  = new ArrayList<>();
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = null;

		try {

			cn = cnFactory.getConnection();
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM company;");

			liste = (ArrayList<Company>) extractFromResultSet(rs);

		} catch (SQLException e) {
			throw new IllegalStateException("Error while querying the database");
		} finally {
			cnFactory.disconnect(stmt,rs,cn);
		}
		return liste;
	}
	/**
	 * 
	 * @param comp A Company to be added in the table company
	 */
	@Override
	public void create(Company comp) throws SQLException{

		PreparedStatement stmt = null;

		Connection cn = cnFactory.getConnection();
		stmt = cn.prepareStatement("INSERT into company(id,name) VALUES(?,?);");

		stmt.setLong(1,comp.getId());
		stmt.setString(2,comp.getName());
		stmt.executeUpdate();

		cnFactory.disconnect(stmt,cn);	
	}
}