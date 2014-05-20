package com.excilys.formation.webproject.dto;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;

@Component
public class Mapper {
	
	/**
	 * 
	 * @param computerDTO
	 * @return
	 */
	public Computer fromDTO(ComputerDTO computerDTO, Company company) {
		
		if (computerDTO.getIntroduced()==null) computerDTO.setIntroduced("");
		if (computerDTO.getDiscontinued()==null) computerDTO.setDiscontinued("");
		if (computerDTO.getCompany()==null) computerDTO.setCompany("");

		String	name = computerDTO.getName();
		DateTimeFormatter  dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime introduced = null;
		DateTime discontinued = null;
		if (!computerDTO.getIntroduced().isEmpty()) {
			try {
				introduced = dtf.parseDateTime(computerDTO.getIntroduced());
			} catch(NullPointerException e) {
				introduced = new DateTime(new Timestamp(0));
			}					
		}else {
			introduced = new DateTime(new Timestamp(0));
		}	
		if (!computerDTO.getDiscontinued().isEmpty()) {
			try {
				discontinued = dtf.parseDateTime(computerDTO.getDiscontinued());
			} catch(NullPointerException e) {
				discontinued = new DateTime(new Timestamp(0));
			}
		}else {
			discontinued = new DateTime(new Timestamp(0));	
		}	

		//Company company = mainService.findCompanyById(computerDTO.getCompany()); 

		return new Computer.CpuBuilder().name(name).introduced(introduced).discontinued(discontinued).company(company).build();	
	}
}
