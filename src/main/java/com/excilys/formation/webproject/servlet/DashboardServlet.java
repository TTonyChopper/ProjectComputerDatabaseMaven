package com.excilys.formation.webproject.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.webproject.common.PageWrapper;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.impl.MainServiceImpl;

/**
 * 
 * @author excilys
 *
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {		
		//
		PageWrapper pageWrapper = null;
		String nameFilter = request.getParameter("nameFilter");
		
		//1-fieldOrder
		String fieldOrder = request.getParameter("fieldOrder");
		if (fieldOrder == null) fieldOrder = "cpu.id";
		
		//2-order
		String order = request.getParameter("order");
		if (order == null) order = "ASC";
			
		//Search OFF
		if ( (nameFilter == null) || (nameFilter.isEmpty()) ) {
				
			if (nameFilter == null) nameFilter = "";
			
			//3-computerListSize
			Integer computerListSize = MainServiceImpl.Singleton.getListComputerSize();
			
			//4-pageNumber
			String pageNumberS = request.getParameter("pageNumber");
			
			Integer pageNumber = null;
			if (pageNumberS == null) pageNumber = 1;
			else if (pageNumberS.equals("last")) {
				pageNumber = (int) Math.ceil(computerListSize / 25.0);	
				if (pageNumber == 0) pageNumber = 1;
			}else if (!pageNumberS.matches("^[0-9]*$")) {
					pageNumber = 1;
			}else pageNumber = (int) Double.parseDouble(pageNumberS);					
			
			//Build with all except computerList,namefilter
			pageWrapper = PageWrapper.builder().pageNumber(pageNumber).fieldOrder(fieldOrder).order(order).computerListSize(computerListSize).build();
		
			//5-Set the computerList
			MainServiceImpl.Singleton.getListComputer(pageWrapper);

		//Search ON
		}else {		
			
			//Build partial pageWrapper, countains nameFilter
			pageWrapper = PageWrapper.builder().nameFilter(nameFilter).build();
			
			//3-computerListSize
			Integer computerListSize = MainServiceImpl.Singleton.getListComputerSizeWithName(pageWrapper);
		
			//4-pageNumber
			String pageNumberS = request.getParameter("pageNumber");
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
			List<Computer> computerList = MainServiceImpl.Singleton.getListComputerWithName(pageWrapper); 
			
			//Build complete PageWrapper
			pageWrapper = PageWrapper.builder().nameFilter(nameFilter).pageNumber(pageNumber).fieldOrder(fieldOrder).order(order).computerList(computerList).computerListSize(computerListSize).build();		
		}
		
		//Set the PageWrapper
		request.setAttribute("pageWrapper", pageWrapper);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request,response);
}
}