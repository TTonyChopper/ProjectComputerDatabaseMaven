package com.excilys.formation.webproject.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@WebServlet("/editComputer")
public class EditServlet extends FormServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
		
		List<Company> companylist = (ArrayList<Company>)MainServiceImpl.Singleton.getListCompany();
		request.setAttribute("companylist", companylist);
		request.setAttribute("companylistsize", companylist.size());
		
		Long editedid = null;
		if (request.getParameter("eid") != null) editedid = Long.decode(request.getParameter("eid"));
		Computer editedcomputer = MainServiceImpl.Singleton.findComputer(editedid);
		request.setAttribute("ecomputer", editedcomputer);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request,response);
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
		
		Long savedid = Long.decode(request.getParameter("eid"));	
		ComputerDTO computerDTO = retrieveComputerDTO(request,response);
		Computer computer = retrieveComputer(computerDTO);
		
		List errorlist = Validator.check(computerDTO);
		if (!(Validator.validate(errorlist))) {
			request.setAttribute("errorlist", errorlist);
			request.setAttribute("editedcomputer", MainServiceImpl.Singleton.findComputer(savedid));
			
			List<Company> companylist = (ArrayList<Company>)MainServiceImpl.Singleton.getListCompany();
			request.setAttribute("companylist", companylist);
			request.setAttribute("companylistsize", companylist.size());
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(request,response);
		}
		else {	
			MainServiceImpl.Singleton.editComputer(computer,savedid);	
		 
			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		}
	}	
}