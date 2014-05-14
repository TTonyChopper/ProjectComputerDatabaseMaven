package com.excilys.formation.webproject.dto;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.impl.MainServiceImpl;

@Component
public class Mapper {

	@Autowired
	private MainServiceImpl mainService;

	/**
	 * 
	 * @param computerDTO
	 * @return
	 */
	public Computer fromDTO(ComputerDTO computerDTO) {

		if (computerDTO.getIntroduced()==null) computerDTO.setIntroduced("");
		if (computerDTO.getDiscontinued()==null) computerDTO.setDiscontinued("");
		if (computerDTO.getCompany()==null) computerDTO.setCompany("");

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

		Company company = mainService.findCompanyById(computerDTO.getCompany()); 

		return new Computer.CpuBuilder().name(name).introduced(introduced).discontinued(discontinued).company(company).build();	
	}
}
