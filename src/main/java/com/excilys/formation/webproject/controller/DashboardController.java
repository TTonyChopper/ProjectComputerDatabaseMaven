package com.excilys.formation.webproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.excilys.formation.webproject.common.PageWrapper;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.impl.MainServiceImpl;

/**
 * 
 * @author excilys
 *
 */
@Controller
public class DashboardController {

	@Autowired
	private MainServiceImpl mainService;
	
	@RequestMapping(value="/dashboard",method = RequestMethod.GET)
	 public String getDashboard(@RequestParam(value="nameFilter", required=false, defaultValue="") String nameFilter, 
			 			  @RequestParam(value="fieldOrder", required=false, defaultValue="cpu.id") String fieldOrder, 
			 			  @RequestParam(value="order", required=false, defaultValue="ASC") String order, 
			 			  @RequestParam(value="pageNumber", required=false, defaultValue="1") String pageNumberS,
			              Model model) {		

		System.out.println("I ABOUT TO DO it yay !");
		PageWrapper pageWrapper = null;
			
		//Search OFF
		if ( (nameFilter == null) || (nameFilter.isEmpty()) ) {
				
			if (nameFilter == null) nameFilter = "";
			
			//3-computerListSize
			Integer computerListSize = mainService.getListComputerSize();
			
			//4-pageNumber
			
			Integer pageNumber = null;
			if (pageNumberS.equals("last")) {
				pageNumber = (int) Math.ceil(computerListSize / 25.0);	
				if (pageNumber == 0) pageNumber = 1;
			}else if (!pageNumberS.matches("^[0-9]*$")) {
					pageNumber = 1;
			}else pageNumber = (int) Double.parseDouble(pageNumberS);					
			
			//Build with all except computerList,namefilter
			pageWrapper = PageWrapper.builder().pageNumber(pageNumber).fieldOrder(fieldOrder).order(order).computerListSize(computerListSize).build();
			System.out.println("test loop1"+pageWrapper.toString());
		
			//5-Set the computerList
			mainService.getListComputer(pageWrapper);

		//Search ON
		}else {		
			
			//Build partial pageWrapper, countains nameFilter
			pageWrapper = PageWrapper.builder().nameFilter(nameFilter).build();
			System.out.println("test loop2"+pageWrapper.toString());
			//3-computerListSize
			Integer computerListSize = mainService.getListComputerSizeWithName(pageWrapper);
		
			//4-pageNumber
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
			List<Computer> computerList = mainService.getListComputerWithName(pageWrapper); 
			
			//Build complete PageWrapper
			pageWrapper = PageWrapper.builder().nameFilter(nameFilter).pageNumber(pageNumber).fieldOrder(fieldOrder).order(order).computerList(computerList).computerListSize(computerListSize).build();		
		}
		System.out.println("I DIT it yay !");
		
		//Set the PageWrapper
		model.addAttribute("pageWrapper", pageWrapper);
		
		return "dashboard";
}

}