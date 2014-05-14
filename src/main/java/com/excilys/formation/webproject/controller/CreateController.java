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

import com.excilys.formation.webproject.common.Validator;
import com.excilys.formation.webproject.dto.ComputerDTO;
import com.excilys.formation.webproject.dto.Mapper;
import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.MainService;

/**
 * 
 * @author excilys
 *
 */
@Controller
@RequestMapping(value="/addComputer")
public class CreateController {

	@Autowired
	private MainService mainService;

	@Autowired
	private Mapper mapper;
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ComputerDTO getAdd(Model model) {		

		List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
		model.addAttribute("companylist", companylist);
		model.addAttribute("companylistsize", companylist.size());

		return new ComputerDTO();
	}
	/**
	 * 
	 * @param computerDTO
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String postAdd(@ModelAttribute @Valid ComputerDTO computerDTO,BindingResult result,
			Model model) {		

		Computer computer = mapper.fromDTO(computerDTO);
		List<String> errorlist = Validator.check(computerDTO,computer.getCompany().getName());
		List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
		model.addAttribute("companylist", companylist);
		model.addAttribute("companylistsize", companylist.size());
		model.addAttribute("errorlist", errorlist);

		if( (result.hasErrors()) || (!(Validator.validate(errorlist))) ) {			

			return "addComputer";
		}
		else { 
			mainService.createComputer(computer);

			return "redirect:dashboard";
		}
	}
}