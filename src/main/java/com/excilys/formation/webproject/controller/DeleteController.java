package com.excilys.formation.webproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.webproject.service.MainService;

@Controller
@RequestMapping(value="/removeComputer")
public class DeleteController {

	@Autowired
	private MainService mainService;

	/**
	 * 
	 * @param rid
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getRemove(@RequestParam(value="rid") String rid,
			Model model) {

		Long removedid;

		if ( (rid == null) || (rid.isEmpty()) ) return"dashboard";
		else {
			removedid = Long.decode(rid);		

			model.addAttribute("rcomputer", mainService.findComputer(removedid));

			return "removeComputer";
		}	
	}
	/**
	 * 
	 * @param rid
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String postRemove(@RequestParam(value="rid") String rid,
			Model model) {

		Long removedid;

		if ( (rid == null) || (rid.isEmpty()) ) return "dashboard";
		else {
			removedid = Long.decode(rid);

			mainService.deleteComputer(removedid);

			return "redirect:dashboard";
		}	
	}
}