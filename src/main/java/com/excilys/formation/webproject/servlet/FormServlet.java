package com.excilys.formation.webproject.servlet;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.webproject.dto.ComputerDTO;
import com.excilys.formation.webproject.dto.ComputerDTO.DTOBuilder;
import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.impl.MainServiceImpl;

/**
 * 
 * @author excilys
 *
 */
public class FormServlet extends HttpServlet {

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ComputerDTO retrieveComputerDTO(HttpServletRequest request,HttpServletResponse response) {
		
		DTOBuilder cpurDTObuilder = ComputerDTO.builder();
		cpurDTObuilder.name(request.getParameter("name"));
		cpurDTObuilder.introduced(request.getParameter("introducedDate"));
		cpurDTObuilder.discontinued(request.getParameter("discontinuedDate"));
		Long companyid = Long.decode(request.getParameter("company"));
		Company company = MainServiceImpl.Singleton.findCompanyById(companyid);	
		cpurDTObuilder.company(company.getName());
		
		return cpurDTObuilder.build();
	}
	
	/**
	 * 
	 * @param computerDTO
	 * @return
	 */
	public Computer retrieveComputer(ComputerDTO computerDTO) {
		String	name = computerDTO.getName();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition parseposition = new ParsePosition(0);
		Date introduceddate = null;
		Date discontinueddate = null;
		Timestamp introduced = null;
		Timestamp discontinued = null;
		if (computerDTO.getIntroduced() != null) {
			try {
			introduceddate = formatter.parse(computerDTO.getIntroduced(),parseposition);
			introduced = new Timestamp(introduceddate.getTime());
			} catch(NullPointerException e) {
				introduced = new Timestamp(0);
			}					
		}else {
			introduced = new Timestamp(0);
		}
		parseposition.setIndex(0);	
		if (computerDTO.getDiscontinued() != null) {
			try {
			discontinueddate = formatter.parse(computerDTO.getDiscontinued(),parseposition);
			discontinued = new Timestamp(discontinueddate.getTime());
			} catch(NullPointerException e) {
				discontinued = new Timestamp(0);
			}
		}else {
			discontinued = new Timestamp(0);	
		}	
		
		Company company = MainServiceImpl.Singleton.findCompanyByName(computerDTO.getCompany()); 
		
		return new Computer.CpuBuilder().name(name).introduced(introduced).discontinued(discontinued).company(company).build();	
	}
}