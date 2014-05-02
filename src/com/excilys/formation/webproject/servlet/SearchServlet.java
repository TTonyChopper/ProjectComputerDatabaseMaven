package com.excilys.formation.webproject.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.MainService;

/**
 * 
 * @author excilys
 *
 */
public class SearchServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
		
		String namefilter = request.getParameter("namefilter");
		if (namefilter == null) namefilter = "";
		List<Computer> computerlist = MainService.Singleton.getListComputerWithName(namefilter);
		if (computerlist == null) {
			request.setAttribute("computerlistsize", 0);
		} else {
		request.setAttribute("computerlist", computerlist);
		request.setAttribute("computerlistsize", computerlist.size());
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request,response);
	}
	
}