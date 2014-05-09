//package com.excilys.formation.webproject.controller;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;
//
//import com.excilys.formation.webproject.common.Validator;
//import com.excilys.formation.webproject.dto.ComputerDTO;
//import com.excilys.formation.webproject.om.Company;
//import com.excilys.formation.webproject.om.Computer;
//import com.excilys.formation.webproject.service.impl.MainServiceImpl;
//
///**
// * 
// * @author excilys
// *
// */
//@RequestMapping("/editComputer")
//@Controller
//public class EditController extends FormController{
//	
//	@Autowired
//	private MainServiceImpl mainService;
//	
//	public void init(ServletConfig config) throws ServletException {
//        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
//        super.init(config);
//    }
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
//		
//		List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
//		request.setAttribute("companylist", companylist);
//		request.setAttribute("companylistsize", companylist.size());
//		
//		Long editedid = null;
//		if (request.getParameter("eid") != null) editedid = Long.decode(request.getParameter("eid"));
//		Computer editedcomputer = mainService.findComputer(editedid);
//		request.setAttribute("ecomputer", editedcomputer);
//		
//		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request,response);
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public void saveComputer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
//		
//		Long savedid = Long.decode(request.getParameter("eid"));	
//		ComputerDTO computerDTO = retrieveComputerDTO(request,response);
//		Computer computer = retrieveComputer(computerDTO);
//		
//		List errorlist = Validator.check(computerDTO);
//		if (!(Validator.validate(errorlist))) {
//			request.setAttribute("errorlist", errorlist);
//			request.setAttribute("editedcomputer", mainService.findComputer(savedid));
//			
//			List<Company> companylist = (ArrayList<Company>)mainService.getListCompany();
//			request.setAttribute("companylist", companylist);
//			request.setAttribute("companylistsize", companylist.size());
//			
//			this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request,response);
//		}
//		else {	
//			mainService.editComputer(computer,savedid);	
//		 
//			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
//		}
//	}	
//}