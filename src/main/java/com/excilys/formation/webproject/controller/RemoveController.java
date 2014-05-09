//package com.excilys.formation.webproject.controller;
//
//import java.io.IOException;
//
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;
//
//import com.excilys.formation.webproject.service.impl.MainServiceImpl;
//
///**
// * 
// * @author excilys
// *
// */
//@RequestMapping("/removeComputer")
//@Controller
//public class RemoveController{
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
//		String ridS = request.getParameter("rid");
//		Long removedid;
//		
//		if ( (ridS.isEmpty()) || (ridS == null) ) this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request,response);
//		else {
//		removedid = Long.decode(request.getParameter("rid"));		
//		 
//		request.setAttribute("rcomputer", mainService.findComputer(removedid));
//		
//		this.getServletContext().getRequestDispatcher("/WEB-INF/removeComputer.jsp").forward(request,response);
//		}
//	}
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
//		
//		String ridS = request.getParameter("rid");
//		Long removedid;
//		
//		if ( (ridS.isEmpty()) || (ridS == null) ) this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request,response);
//		else {
//		removedid = Long.decode(request.getParameter("rid"));	
//		mainService.removeComputer(removedid);	
//		 
//		this.getServletContext().getRequestDispatcher("/dashboard").forward(request,response);
//		}
//	}	
//}