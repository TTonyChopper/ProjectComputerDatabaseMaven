package com.excilys.formation.webproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.webproject.common.PageWrapper;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.MainService;

@Controller
@RequestMapping(value="/dashboard")
public class DashboardController {

	@Autowired
	private MainService mainService;

	/**
	 * 
	 * @param nameFilter
	 * @param fieldOrder
	 * @param order
	 * @param pageNumberS
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getDashboard(@RequestParam(value="nameFilter",defaultValue="") String nameFilter, 
			@RequestParam(value="fieldOrder",defaultValue="cpu.id") String fieldOrder, 
			@RequestParam(value="order",defaultValue="ASC") String order, 
			@RequestParam(value="pageNumber",defaultValue="1") String pageNumberS,
			Model model) {		

		PageWrapper pageWrapper = null;

		//Search OFF
		if ( (nameFilter.isEmpty()) || (nameFilter == null) ) {

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

			//5-Set the computerList
			mainService.getListComputer(pageWrapper);

			//Search ON
		}else {		

			//Build partial pageWrapper, countains nameFilter
			pageWrapper = PageWrapper.builder().nameFilter(nameFilter).build();
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

		//Set the PageWrapper
		model.addAttribute("pageWrapper", pageWrapper);

		return "dashboard";
	}
}
