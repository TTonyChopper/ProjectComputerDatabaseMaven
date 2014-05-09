package com.excilys.formation.webproject.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.webproject.common.PageWrapper;
import com.excilys.formation.webproject.common.Validator;
import com.excilys.formation.webproject.dto.ComputerDTO;
import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.impl.MainServiceImpl;

/**
 * 
 * @author excilys
 *
 */
@Controller
public class MainController {
	
	@Autowired
	private MainServiceImpl mainService;
	
	/**
	 * 
	 * @param computerDTO
	 * @return
	 */
	private Computer retrieveComputer(ComputerDTO computerDTO) {
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
	
	@RequestMapping(value="/dashboard",method = RequestMethod.GET)
	public String getDashboard(@RequestParam(value="nameFilter", required=false, defaultValue="") String nameFilter, 
			 			  @RequestParam(value="fieldOrder", required=false, defaultValue="cpu.id") String fieldOrder, 
			 			  @RequestParam(value="order", required=false, defaultValue="ASC") String order, 
			 			  @RequestParam(value="pageNumber", required=false, defaultValue="1") String pageNumberS,
			              Model model) {		

		System.out.println("I ABOUT TO DO it yay !");
		PageWrapper pageWrapper = null;
			
		//Search OFF
		if ( (nameFilter == null) || (nameFilter.isEmpty()) ) {
				
			if (nameFilter == null) nameFilter = "";
			
			//3-computerListSize
			Integer computerListSize = mainService.getListComputerSize();
			
			//4-pageNumber
			
			Integer pageNumber = null;
			if (pageNumberS.equals("last")) {
				pageNumber = (int) Math.ceil(computerListSize / 25.0);	
				if (pageNumber == 0) pageNumber = 1;
			}else if (!pageNumberS.matches("^[0-9]*$")) {
					pageNumber = 1;
			}else pageNumber = (int) Double.parseDouble(pageNumberS);					
			
			//Build with all except computerList,namefilter
			pageWrapper = PageWrapper.builder().pageNumber(pageNumber).fieldOrder(fieldOrder).order(order).computerListSize(computerListSize).build();
			System.out.println("test loop1"+pageWrapper.toString());
		
			//5-Set the computerList
			mainService.getListComputer(pageWrapper);

		//Search ON
		}else {		
			
			//Build partial pageWrapper, countains nameFilter
			pageWrapper = PageWrapper.builder().nameFilter(nameFilter).build();
			System.out.println("test loop2"+pageWrapper.toString());
			//3-computerListSize
			Integer computerListSize = mainService.getListComputerSizeWithName(pageWrapper);
		
			//4-pageNumber
			Integer pageNumber = null;
			if (pageNumberS == null) pageNumber = 1;
			else if ((pageNumberS.equals("last"))) {
				pageNumber = (int) Math.ceil(computerListSize / 25.0);	
				if (pageNumber == 0) pageNumber = 1;
			}else if (!pageNumberS.matches("^[0-9]*$")) {
				pageNumber = 1;
			}else pageNumber = (int) Double.parseDouble(pageNumberS);
			
			//Build with 5-nameFilter, countains all except except computerList
			pageWrapper = PageWrapper.builder().nameFilter(nameFilter).pageNumber(pageNumber).fieldOrder(fieldOrder).order(order).computerListSize(computerListSize).build();
			
			//5-Set the computerList			
			List<Computer> computerList = mainService.getListComputerWithName(pageWrapper); 
			
			//Build complete PageWrapper
			pageWrapper = PageWrapper.builder().nameFilter(nameFilter).pageNumber(pageNumber).fieldOrder(fieldOrder).order(order).computerList(computerList).computerListSize(computerListSize).build();		
		}
		System.out.println("I DIT it yay !");
		
		//Set the PageWrapper
		model.addAttribute("pageWrapper", pageWrapper);
		
		return "dashboard";
	}
	
	@RequestMapping(value="/addComputer",method = RequestMethod.GET)
	public String getAdd(@RequestParam(value="name", required=false, defaultValue="") String name, 
			 			   @RequestParam(value="introducedDate", required=false, defaultValue="") String introduced, 
			 			   @RequestParam(value="discontinuedDate", required=false, defaultValue="") String discontinued, 
			 			   @RequestParam(value="company", required=false, defaultValue="") String company,
			 			   Model model) {			
		
		List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
		model.addAttribute("companylist", companylist);
		model.addAttribute("companylistsize", companylist.size());
		
		return "addComputer";
	}
	
	@RequestMapping(value="/addComputer",method = RequestMethod.POST)
	public String postAdd(@RequestParam(value="name", required=false, defaultValue="") String name, 
			 			    @RequestParam(value="introducedDate", required=false, defaultValue="") String introduced, 
			 			    @RequestParam(value="discontinuedDate", required=false, defaultValue="") String discontinued, 
			 			    @RequestParam(value="company", required=false, defaultValue="") String companyidS,
			                Model model) {		
			
		Long companyid = Long.decode(companyidS);
		Company company = mainService.findCompanyById(companyid);
		ComputerDTO computerDTO = ComputerDTO.builder().name(name).introduced(introduced).discontinued(discontinued).company(company.getName()).build();
		Computer computer = retrieveComputer(computerDTO);
		
		List errorlist = Validator.check(computerDTO);
		
		if (!(Validator.validate(errorlist))) {
			
			List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
			model.addAttribute("companylist", companylist);
			model.addAttribute("companylistsize", companylist.size());
			model.addAttribute("errorlist", errorlist);
			System.out.println("NOT VALIDATED");
			
			return "../../index";
		}
		else { 
			System.out.println("VALIDATED");
			mainService.insertComputer(computer);
			
			return "../../index";
		}
	}
	
	@RequestMapping(value="/editComputer",method = RequestMethod.GET)
	public String getEdit(@RequestParam(value="eid", required=true, defaultValue="") String eid, 
			                Model model) {
		
		List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
		model.addAttribute("companylist", companylist);
		model.addAttribute("companylistsize", companylist.size());
		
		Long editedid = null;
		if (!eid.isEmpty()) editedid = Long.decode(eid);
		Computer editedcomputer = mainService.findComputer(editedid);
		model.addAttribute("ecomputer", editedcomputer);
		
		return "editComputer";
	}
	
	@RequestMapping(value="/editComputer",method = RequestMethod.POST)
	public String postEdit(@RequestParam(value="eid", required=true) String eid,
							@RequestParam(value="name", required=false) String name, 
			 			    @RequestParam(value="introducedDate", required=false) String introduced, 
			 			    @RequestParam(value="discontinuedDate", required=false) String discontinued, 
			 			    @RequestParam(value="company", required=false, defaultValue="") String companyidS,
			                Model model) {
		
		Long savedid = Long.decode(eid);
		Long companyid = Long.decode(companyidS);
		Company company = mainService.findCompanyById(companyid);
		
		ComputerDTO computerDTO = ComputerDTO.builder().name(name).introduced(introduced).discontinued(discontinued).company(company.getName()).build();
		Computer computer = retrieveComputer(computerDTO);
		
		List errorlist = Validator.check(computerDTO);
		if (!(Validator.validate(errorlist))) {
			model.addAttribute("errorlist", errorlist);
			model.addAttribute("editedcomputer", mainService.findComputer(savedid));
			
			List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
			model.addAttribute("companylist", companylist);
			model.addAttribute("companylistsize", companylist.size());
			
			return "editComputer";
		}
		else {	
			mainService.editComputer(computer,savedid);	
		 
			return "../../index";
		}
	}

	@RequestMapping(value="/removeComputer",method = RequestMethod.GET)
	public String getRemove(@RequestParam(value="rid", required=true) String rid,
			                Model model) {
		
		Long removedid;
		
		if ( (rid.isEmpty()) || (rid == null) ) return"dashboard";
		else {
		removedid = Long.decode(rid);		
		 
		model.addAttribute("rcomputer", mainService.findComputer(removedid));
		
		return "removeComputer";
		}	
	}
	
	@RequestMapping(value="/removeComputer",method = RequestMethod.POST)
	public String postRemove(@RequestParam(value="rid", required=true) String rid,
			                Model model) {
	
		Long removedid;
		
		if ( (rid.isEmpty()) || (rid == null) ) return "dashboard";
		else {
		removedid = Long.decode(rid);
		
		mainService.removeComputer(removedid);
		
		return "../../index";
		}	
	}
}