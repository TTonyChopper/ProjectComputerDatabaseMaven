package com.excilys.formation.webproject.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
public class AddController extends FormController {
	
	@RequestMapping(value="/dashboard",method = RequestMethod.GET)
	 public String getAdd(@RequestParam(value="nameFilter", required=false, defaultValue="") String nameFilter, 
			 			  @RequestParam(value="fieldOrder", required=false, defaultValue="cpu.id") String fieldOrder, 
			 			  @RequestParam(value="order", required=false, defaultValue="ASC") String order, 
			 			  @RequestParam(value="pageNumber", required=false, defaultValue="1") String pageNumberS,
			              Model model) {		
		
		List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
		model.addAttribute("companylist", companylist);
		model.addAttribute("companylistsize", companylist.size());
		
		return "addComputer";
	}
	
	@RequestMapping(value="/dashboard",method = RequestMethod.GET)
	 public String postAdd(@RequestParam(value="nameFilter", required=false, defaultValue="") String nameFilter, 
			 			  @RequestParam(value="fieldOrder", required=false, defaultValue="cpu.id") String fieldOrder, 
			 			  @RequestParam(value="order", required=false, defaultValue="ASC") String order, 
			 			  @RequestParam(value="pageNumber", required=false, defaultValue="1") String pageNumberS,
			 			 @RequestParam(value="companyid", required=true) Long companyid,
			              Model model) {		
			
		Company company = mainService.findCompanyById(companyid);
		Computer computer = retrieveComputer(computerDTO);
		
		List errorlist = Validator.check(computerDTO);
		
		if (!(Validator.validate(errorlist))) {
			
			List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
			model.addAttribute("companylist", companylist);
			model.addAttribute("companylistsize", companylist.size());
			model.addAttribute("errorlist", errorlist);
			
			return "index";
		}
		else { 
			mainService.insertComputer(computer);
			
			return "index";
		}
	}	
}