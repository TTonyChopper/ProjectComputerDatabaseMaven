package com.excilys.formation.webproject.common;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.webproject.dto.ComputerDTO;

/**
 * 
 * @author excilys
 *
 */
public class Validator {
	
	/**
	 * 
	 * @param computerDTO
	 * @return
	 */
	public static List check(ComputerDTO computerDTO) {
		List<String> errorlist = new ArrayList<>(4);
		
		StringBuilder sb = new StringBuilder();
		sb.append(checkName(computerDTO.getName())).append(" : ").append(computerDTO.getName());
		errorlist.add(0,new String (sb));
		sb = new StringBuilder();
		sb.append(checkIntroduced(computerDTO.getIntroduced())).append(" : ").append(computerDTO.getIntroduced());
		errorlist.add(1,new String (sb));
		String errorintroduced = sb.substring(0,2);
		sb = new StringBuilder();
		sb.append(checkDiscontinued(computerDTO.getDiscontinued(),computerDTO.getIntroduced(),errorintroduced)).append(" : ").append(computerDTO.getDiscontinued());
		errorlist.add(2,new String (sb));
		sb = new StringBuilder();
		sb.append(checkCompany(computerDTO.getCompany())).append(" : ").append(computerDTO.getCompany());
		errorlist.add(3,new String (sb));
		
		return errorlist;
	}

	/**
	 * 
	 * @param errorlist
	 * @return
	 */
	public static boolean validate(List errorlist) {
		
		for (int i = 0;i < 4;i++) {		
			if (!(((String) errorlist.get(i)).substring(0,2).equals("OK"))) return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private static String checkName(String name) {
		String status = "OK";
		
		if (name == null) throw new IllegalStateException("The name entry is null");
		else if (name == "") status = "The name entry is void";
		else if (name.length() > 255) status = "The name entry is too long";
		return status;
	}
	
	/**
	 * 
	 * @param introduced
	 * @return
	 */
	private static String checkIntroduced(String introduced) {
		String status = "OK";
		
		if (introduced.equals("")) return "OK";
		else if (introduced.length() < 10) status = "The introduced entry is too long";
		else introduced = introduced.substring(0,10);

		if (introduced == null) throw new IllegalStateException("The introduced entry is null");
		//Pattern YYYY-MM-DD
		String pattern = ("^((19|20)\\d\\d)-(0[1-9]|1[012])-(0[0-9]|[12]\\d|3[01]).*");
		if (introduced.length() > 255) status = "The introduced entry is too long";
		else if (!introduced.matches(pattern)) status="The introduced entry is invalid";  
		else {
			if (!preciseCheck(introduced)) status="The introduced entry is invalid";
		}
		
		return status;
	}
	
	/**
	 * 
	 * @param introduced
	 * @param discontinued
	 * @param errorintroduced
	 * @return
	 */
	private static String checkDiscontinued(String discontinued,String introduced,String errorintroduced) {
		String status = "OK";
		
		if (discontinued.equals("")) return "OK";
		else if (discontinued.length() < 10) status = "The discontinued entry is too long";
		else discontinued = discontinued.substring(0,10);
		if (introduced == null) throw new IllegalStateException("The discontinued entry is null");
		//Pattern YYYY-MM-DD
		String pattern = ("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12]\\d|3[01]).*");

		if (discontinued.length() > 255) status = "The discontinued entry is too long";
		else if (!discontinued.matches(pattern) ) status="The discontinued entry is invalid";  
		else if (!preciseCheck(discontinued) ) status="The discontinued entry is invalid"; 
		else if ( (introduced != "") && errorintroduced.substring(0,2).equals("OK") ) status = preciseCheck(discontinued,introduced);

		return status;
	}
	
	/**
	 * 
	 * @param company
	 * @return
	 */
	private static String checkCompany(String company) {
		String status = "OK";
		
		if (company == null) throw new IllegalStateException("null");
		else if (company.length() > 255) status = "too long";
		return status;
	}
	
	private static boolean preciseCheck(String date) {	
		date = date.substring(0,10);
		
	    int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5,7));
		int day = Integer.parseInt(date.substring(8,10));
	 
		if (day > 31) return false; 
		else if ( (day == 31) && ( (month == 2) || (month == 4) || (month == 6) || (month == 9) || (month == 11) ) ) return false; 
		else if ( ( month == 2 ) && (day > 29) ) return false;
		else if ( ( month == 2 ) && (day == 29) && ( (year % 4) != 0) ) return false;
		
		else return true;
	}
	
	/**
	 * 
	 * @param discontinued
	 * @param introduced
	 * @return
	 */
	private static String preciseCheck(String discontinued,String introduced) {		
		String status = "OK";
		
		//date 1
	    int year = Integer.parseInt(discontinued.substring(0,4));
		int month = Integer.parseInt(discontinued.substring(5,7));
		int day = Integer.parseInt(discontinued.substring(8,10));
		//date 2
		int year2 = Integer.parseInt(introduced.substring(0,4));
		int month2 = Integer.parseInt(introduced.substring(5,7));
		int day2 = Integer.parseInt(introduced.substring(8,10));	

		//Check discontinued
		//alone
		if (day > 31) status = "The discontinued entry is invalid";
		else if ( (day == 31) && ( (month == 2) || (month == 4) || (month == 6) || (month == 9) || (month == 11) ) ) status = "The discontinued entry is invalid";
		else if ( ( month == 2 ) && (day > 29) ) status = "The discontinued entry is invalid";
		else if ( ( month == 2 ) && (day == 29) && ( (year % 4) != 0) ) status = "The discontinued entry is invalid";
	
		//comparatively to introduced
		else if ( (year < year2) || ((year == year2) && (month < month2)) || ((year == year2) && (month == month2) && (day < day2)) ) status = "The discontinued entry is paradoxical";
		
		return status;
	}
}
