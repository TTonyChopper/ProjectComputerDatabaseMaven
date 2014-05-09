package com.excilys.formation.webproject.controller;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
public class FormController {

	@Autowired
	protected MainServiceImpl mainService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
/*
	public ComputerDTO retrieveComputerDTO(Model model) {
		
		DTOBuilder cpurDTObuilder = ComputerDTO.builder();
		cpurDTObuilder.name(model.getParameter("name"));
		cpurDTObuilder.introduced(model.getParameter("introducedDate"));
		cpurDTObuilder.discontinued(model.getParameter("discontinuedDate"));
		Long companyid = Long.decode(model.getParameter("company"));
		Company company = mainService.findCompanyById(companyid);	
		cpurDTObuilder.company(company.getName());
		
		return cpurDTObuilder.build();
	}*/
	
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
		
		Company company = mainService.findCompanyByName(computerDTO.getCompany()); 
		
		return new Computer.CpuBuilder().name(name).introduced(introduced).discontinued(discontinued).company(company).build();	
	}
}