package com.excilys.formation.webproject.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.webproject.dao.CompanyDAO;
import com.excilys.formation.webproject.dao.ComputerDAO;
import com.excilys.formation.webproject.om.Company;
import com.excilys.formation.webproject.om.Computer;
import com.excilys.formation.webproject.service.MainService;
import com.excilys.formation.webproject.common.PageWrapper;


/**
 * 
 * @author excilys
 *
 */

@Service
@Transactional
public class MainServiceImpl implements MainService{

	@Autowired
	private ComputerDAO cpuDAO;
	@Autowired
	private CompanyDAO cpyDAO;
	
	@Transactional (readOnly=true)
	@Override
	public Computer findComputer(Long id) {
		return cpuDAO.find(id);
	}
	
	@Transactional (readOnly=true)
	@Override
	public Integer getListComputerSize() {
		return cpuDAO.getListSize();	
	}
	
	@Transactional (readOnly=true)
	@Override
	public void getListComputer(PageWrapper pageWrapper) {
		cpuDAO.getList(pageWrapper);	
	}
	
	@Transactional (readOnly=true)
	@Override
	public Integer getListComputerSizeWithName(PageWrapper pageWrapper) {
		return cpuDAO.getListSizeWithName(pageWrapper);
	}
	
	@Transactional (readOnly=true)
	@Override
	public List<Computer> getListComputerWithName(PageWrapper pageWrapper) {
		return cpuDAO.getListWithName(pageWrapper);
	}
	
	@Transactional
	@Override
	public void createComputer(Computer comp) {
			cpuDAO.create(comp);
	}	
	
	@Transactional
	@Override
	public void saveComputer(Computer comp, Long id){
			cpuDAO.save(comp,id);
	}
	@Transactional
	@Override
	public void deleteComputer(Long id) {
			cpuDAO.delete(id);
	}
	
	@Transactional (readOnly=true)
	@Override
	public Company findCompanyById(String id) {
		Company comp = new Company();
		Long idL = Long.decode(id);
		if (idL > 0) comp = cpyDAO.findById(idL);
		return comp; 
	}
	
	@Transactional (readOnly=true)
	@Override
	public List<Company> getListCompany() {
		return (ArrayList<Company>) cpyDAO.getList();
	}
	
	@Transactional
	@Override
	public void createCompany(Company comp) {
			cpyDAO.create(comp);
	}		
}