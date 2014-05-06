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
@WebServlet("/addComputer")
public class AddServlet extends FormServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
		
		List<Company> companylist = (ArrayList<Company>)MainServiceImpl.Singleton.getListCompany();
		request.setAttribute("companylist", companylist);
		request.setAttribute("companylistsize", companylist.size());
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request,response);
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
			
		ComputerDTO computerDTO = retrieveComputerDTO(request,response);
		Computer computer = retrieveComputer(computerDTO);
		
		List errorlist = Validator.check(computerDTO);
		
		if (!(Validator.validate(errorlist))) {
			
			List<Company> companylist = (ArrayList<Company>)MainServiceImpl.Singleton.getListCompany();
			request.setAttribute("companylist", companylist);
			request.setAttribute("companylistsize", companylist.size());
			request.setAttribute("errorlist", errorlist);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(request,response);
		}
		else { 
			MainServiceImpl.Singleton.insertComputer(computer);
			
			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		}
	}	
}