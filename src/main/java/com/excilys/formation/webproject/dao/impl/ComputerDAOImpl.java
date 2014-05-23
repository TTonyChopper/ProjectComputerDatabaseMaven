package com.excilys.formation.webproject.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.formation.webproject.dao.ComputerDAO;
import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.om.Computer.CpuBuilder;
import com.excilys.formation.webproject.common.Closer;
import com.excilys.formation.webproject.common.PageWrapper;
import com.jolbox.bonecp.BoneCPDataSource;

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

	final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@Autowired
	private BoneCPDataSource datasource;
	
	@Autowired
	private Closer closer;
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<Computer> extractFromResultSet(ResultSet rs) throws SQLException{
		List<Computer> liste  = new ArrayList<>();
		while ((rs != null)&&rs.next()) {
			CpuBuilder b = Computer.builder().id(new Long(rs.getLong(1))).name(rs.getString(2));
			try {
				DateTime introducedTotal = new DateTime(rs.getTimestamp(3));
				DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
				String strDateOnly = fmt.print(introducedTotal);
				b.introduced(new DateTime(strDateOnly));
			}catch (java.sql.SQLException e) {
				logger.info("Timestamp introduced Null on " + b.getName());
			}
			try {
				DateTime discontinuedTotal = new DateTime(rs.getTimestamp(4));
				DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
				String strDateOnly = fmt.print(discontinuedTotal);
				b.discontinued(new DateTime(strDateOnly));
			}catch (java.sql.SQLException e) {
				logger.info("Timestamp discontinued Null on " + b.getName());
			}		
			b.company(new Company.CpyBuilder().id(new Long(rs.getLong(5))).name(rs.getString(6)).build());				
			liste.add(b.build());
		}
		return liste;
	}
	
	
	@Override
	public Computer find(Long id) {
		List<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Computer computer = null;
		Connection cn = DataSourceUtils.getConnection(datasource);

		try {		
			stmt = cn.prepareStatement("SELECT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu "
					+"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id WHERE cpu.id = ?");
			stmt.setLong(1,id);	

			rs = stmt.executeQuery();

			liste = (ArrayList<Computer>) extractFromResultSet(rs);

			if (liste.size() == 0) computer = null;
			else computer = liste.get(0);

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}
		return computer;
	}
	
	@Override
	public Integer getListSize() {

		Integer computerListSize = null;
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = DataSourceUtils.getConnection(datasource);

		try {
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) as computerlistsize FROM computer");

			while(rs.next()){
				computerListSize = rs.getInt("computerListSize"); 
			}

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}
		return computerListSize;
	}
	
	@Override
	public List<Computer> getList() {

		logger.debug("Connection:" + DataSourceUtils.getConnection(datasource));
		List<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		Statement stmt = null;
		Connection cn = DataSourceUtils.getConnection(datasource);
		
		System.out.println(	"verif de connection datasource si dans transaction=  "+DataSourceUtils.isConnectionTransactional(cn, datasource));
		
		try {
			stmt = cn.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu "
					+"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id");

			liste = (ArrayList<Computer>) extractFromResultSet(rs);

		} catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		} finally {
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}	
		return liste;
	}
	
	@Override
	public void getList(PageWrapper pageWrapper) {

		logger.debug("Connection:" + DataSourceUtils.getConnection(datasource));
		List<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = DataSourceUtils.getConnection(datasource);
		
		System.out.println(	"verif de connection datasource si dans transaction=  "+DataSourceUtils.isConnectionTransactional(cn, datasource));
		
		
		try {
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
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}	
	}
	
	@Override
	public Integer getListSizeWithName(PageWrapper pageWrapper) {	

		Integer computerListSize = null;
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = DataSourceUtils.getConnection(datasource);

		try {
			stmt = cn.prepareStatement("SELECT COUNT(*) AS computerListSize, cpu.id,cpu.name,cpu.introduced,cpu.discontinued,cpu.company_id,cpy.name FROM computer AS cpu " 
					+"LEFT OUTER JOIN company AS cpy ON cpu.company_id = cpy.id WHERE cpu.name LIKE ? OR cpy.name LIKE ?");
			stmt.setString(1,"%"+pageWrapper.getNameFilter()+"%");
			stmt.setString(2,"%"+pageWrapper.getNameFilter()+"%");
			rs = stmt.executeQuery();		

			while(rs.next()){
				computerListSize = rs.getInt("computerListSize"); 
			}

		}catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		}finally {
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}
		return computerListSize;	
	}
	
	
	@Override
	public List<Computer> getListWithName(PageWrapper pageWrapper) {	

		List<Computer> liste  = new ArrayList<>();
		ResultSet rs = null ;
		PreparedStatement stmt = null;
		Connection cn = DataSourceUtils.getConnection(datasource);

		try {
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

		}catch (SQLException e) {
			throw new IllegalStateException("SQL Exception on ResultSet");
		}finally {
			closer.closeResultSet(rs);
			closer.closeStatement(stmt);
		}
		return liste;	
	}
	
	@Override
	public void create(Computer comp) {

		Connection cn = DataSourceUtils.getConnection(datasource);
		Long companyid = comp.getCompany().getId();
		PreparedStatement stmt = null;
		
		try {
		stmt = cn.prepareStatement("INSERT into computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)"); 

		stmt.setString(1,comp.getName());
		stmt.setTimestamp(2,new Timestamp(comp.getIntroduced().getMillis()));
		stmt.setTimestamp(3,new Timestamp(comp.getDiscontinued().getMillis()));
		if (companyid == null) stmt.setNull(4,Types.NULL);
		else stmt.setLong(4,companyid);

		stmt.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException("rollback on creation");
		}finally {
			closer.closeStatement(stmt);
		}
	}
	
	@Override
	public void save(Computer comp,Long id) {

		Connection cn = DataSourceUtils.getConnection(datasource);
		Long companyid = comp.getCompany().getId();
		PreparedStatement stmt = null;
		
		try {
			stmt = cn.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?"); 

			stmt.setString(1,comp.getName());
			stmt.setTimestamp(2,new Timestamp(comp.getIntroduced().getMillis()));
			stmt.setTimestamp(3,new Timestamp(comp.getDiscontinued().getMillis()));
			if (companyid == null) stmt.setNull(4,Types.NULL);
			else stmt.setLong(4,companyid);
			stmt.setLong(5,id);

			stmt.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException("rollback on save");
		}finally {
			closer.closeStatement(stmt);
		}
	}
	
	@Override
	public void delete(Long id) {

		Connection cn = DataSourceUtils.getConnection(datasource);
		PreparedStatement stmt = null;
		
		try {
		stmt = cn.prepareStatement("DELETE FROM computer WHERE id = ?");

		stmt.setLong(1,id);

		stmt.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException("rollback on deletion");
		}finally {
			closer.closeStatement(stmt);
		}
	}
}