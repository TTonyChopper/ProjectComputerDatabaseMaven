package com.excilys.formation.webproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.webproject.common.Closer;
import com.excilys.formation.webproject.dao.CompanyDAO;
import com.excilys.formation.webproject.om.Company;

/**
 * 
 * @author excilys
 *
 */
@Repository
public class CompanyDAOImpl implements CompanyDAO{

	@Autowired
	private DataSource datasource;
	
	@Autowired
	private Closer closer;
	
	private List<Company> extractFromResultSet(ResultSet rs) throws SQLException{
		List<Company> liste  = new ArrayList<>();

		while (rs.next()) {
			Company p = Company.builder().id(new Long(rs.getLong(1))).name(rs.getString(2)).build();	

			liste.add(p);
		}
		return liste;
	}
	
	@Override
	public Company findById(Long id) {
		if (id==0) return Company.builder().name("").build();	
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Company company = new Company();
		Connection cn = null;

		try {
			try {
				cn = datasource.getConnection();
			} catch (SQLException e1) {
				throw new IllegalStateException("No connection available");
			}
			stmt = cn.prepareStatement("SELECT * FROM company WHERE id = ?;");
			stmt.setLong(1,id);

			rs = stmt.executeQuery();	

			while(rs.next()){
				company = Company.builder().id(id).name(rs.getString("name")).build();				
			}

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}
		return company;
	}
	
	@Override
	public Company findByName(String name) {
		if (name == "") return Company.builder().build();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Company company = new Company();
		Connection cn = null;

		try {
			try {
				cn = datasource.getConnection();
			} catch (SQLException e1) {
				throw new IllegalStateException("No connection available");
			}
			stmt = cn.prepareStatement("SELECT * FROM company WHERE name = ?;");
			stmt.setString(1,name);
			rs = stmt.executeQuery();	

			while(rs.next()){
				company = Company.builder().id(rs.getLong("id")).name(name).build();				
			}

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}
		return company;
	}
	
	@Override
	public List<Company> getList() {
		List<Company> liste  = new ArrayList<>();
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = null;

		try {
			try {
				cn = datasource.getConnection();
			} catch (SQLException e1) {
				throw new IllegalStateException("No connection available");
			}
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM company;");

			liste = (ArrayList<Company>) extractFromResultSet(rs);

		} catch (SQLException e) {
			throw new IllegalStateException("Error while querying the database");
		} finally {
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}
		return liste;
	}
	
	@Override
	public void create(Company comp){

		PreparedStatement stmt = null;

		Connection cn = null;
		try {
			cn = datasource.getConnection();
		} catch (SQLException e1) {
			throw new IllegalStateException("No connection available");
		}
		try {
		stmt = cn.prepareStatement("INSERT into company(id,name) VALUES(?,?);");

		stmt.setLong(1,comp.getId());
		stmt.setString(2,comp.getName());
		} catch (SQLException e) {
			throw new IllegalStateException("Error while querying the database");
		} finally {
			closer.closeStatement(stmt);
		}
	}
}