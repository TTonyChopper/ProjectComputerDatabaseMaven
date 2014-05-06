package com.excilys.formation.webproject.dto;

import java.io.Serializable;

/**
 * 	id - The String id of the computer in the database
 *  name - The String name of the computer
 *  introduced - The String introduced corresponding to the moment the computer entry has been put on the market
 *  discontinued - The String discontinued corresponding to the moment the computer has been removed from the market
 *  company - The String Company manufacturing the computer
 *
 * @author excilys
 *
 */
public class ComputerDTO implements Serializable {
	
	/**
	 * 
	 * @author excilys
	 *
	 */
	public static class DTOBuilder {
		private String	name;
		private String introduced;
		private String discontinued;
		private String company;
		
		/**
		 * 
		 * @param name
		 * @return
		 */
		public DTOBuilder name(String name) {
			this.name = name;
			return this;
		}		
		/**
		 * 
		 * @param introduced
		 * @return
		 */
		public DTOBuilder introduced(String introduced) {
			this.introduced = introduced;
			return this;
		}
		/**
		 * 
		 * @param discontinued
		 * @return
		 */
		public DTOBuilder discontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		/**
		 * 
		 * @param company
		 * @return
		 */
		public DTOBuilder company(String company) {
			this.company = company;
			return this;
		}
		
		/**
		 * 
		 * @return the name of the company
		 */
		public String getName() {
			return name;
		}

		/**
		 * 
		 * @return
		 */
	    public ComputerDTO build() {
	        return new ComputerDTO(this);
	    }
	}
	
	/**
	 * 
	 * @return
	 */
	public static DTOBuilder builder() {
		return new DTOBuilder();
	}
	
	/**
	 * 
	 * @param builder
	 */
	private ComputerDTO(DTOBuilder builder) {
        this.name = builder.name; 
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }	

	//Attributs
	private String	name;
	private String introduced;
	private String discontinued;
	private String company;

	//Accesseurs
	/**
	 * 
	 * @return the String name of the company
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name The String name of the company
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * time
	 * @return the String introduced
	 */
	public String getIntroduced() {
		return introduced;
	}
	/**
	 * 
	 * @param introduced The String introduced
	 */
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	/**
	 * 
	 * @return the String discontinued
	 */
	public String getDiscontinued() {
		return discontinued;
	}
	/**
	 * 
	 * @param discontinued The String of discontinued
	 */
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	/**
	 * 
	 * @return the String name of the manufacturing company 
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * 
	 * @param company_id The String name of the manufacturing company
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		return "ComputerDTO [name=" + name + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + ", company=" + company
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result
				+ ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComputerDTO))
			return false;
		ComputerDTO other = (ComputerDTO) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}