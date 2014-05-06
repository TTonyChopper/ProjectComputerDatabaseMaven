package com.excilys.formation.webproject.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.webproject.service.impl.MainServiceImpl;

/**
 * 
 * @author excilys
 *
 */
@WebServlet("/removeComputer")
public class RemoveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
		
		String ridS = request.getParameter("rid");
		Long removedid;
		
		if ( (ridS.isEmpty()) || (ridS == null) ) this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request,response);
		else {
		removedid = Long.decode(request.getParameter("rid"));		
		 
		request.setAttribute("rcomputer", MainServiceImpl.Singleton.findComputer(removedid));
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/removeComputer.jsp").forward(request,response);
		}
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
		
		String ridS = request.getParameter("rid");
		Long removedid;
		
		if ( (ridS.isEmpty()) || (ridS == null) ) this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request,response);
		else {
		removedid = Long.decode(request.getParameter("rid"));	
		MainServiceImpl.Singleton.removeComputer(removedid);	
		 
		this.getServletContext().getRequestDispatcher("/dashboard").forward(request,response);
		}
	}	
}