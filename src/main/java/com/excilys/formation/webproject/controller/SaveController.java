package com.excilys.formation.webproject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.webproject.common.Validator;
import com.excilys.formation.webproject.dto.ComputerDTO;
import com.excilys.formation.webproject.dto.Mapper;
import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.MainService;

@Controller
@RequestMapping(value="/editComputer")
public class SaveController {

	@Autowired
	private MainService mainService;

	@Autowired
	private Mapper mapper;

	/**
	 * 
	 * @param eid
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ComputerDTO getEdit(@RequestParam(value="eid",defaultValue="") String eid, 
			Model model) {

		List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
		model.addAttribute("companylist", companylist);
		model.addAttribute("companylistsize", companylist.size());

		Long editedid = null;
		if (!eid.isEmpty()) editedid = Long.decode(eid);
		Computer editedcomputer = mainService.findComputer(editedid);
		model.addAttribute("ecomputer", editedcomputer);

		return new ComputerDTO();
	}
	/**
	 * 
	 * @param eid
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyidS
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String postEdit(@ModelAttribute @Valid ComputerDTO computerDTO,BindingResult result,
			@RequestParam(value="eid") String eid,
			Model model) {

		Long savedid = Long.decode(eid);
		Company company = mainService.findCompanyById(computerDTO.getCompany());
		Computer computer = mapper.fromDTO(computerDTO, company);

		List<String> errorlist = Validator.check(computerDTO,computer.getCompany().getName());
		List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
		model.addAttribute("companylist", companylist);
		model.addAttribute("companylistsize", companylist.size());
		model.addAttribute("errorlist", errorlist);

		if( (result.hasErrors()) || (!(Validator.validate(errorlist))) ) {					
			return "addComputer";
		}
		else {	
			System.out.println("hahaha"+computer);
			mainService.saveComputer(computer,savedid);	

			return "redirect:dashboard";
		}
	}
}