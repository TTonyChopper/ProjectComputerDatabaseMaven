package com.excilys.formation.webproject.om;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 	id - The Long id of the computer in the database
 *  name - The String name of the computer
 *  introduced - The Timestamp introduced corresponding to the moment the computer entry has been put on the market
 *  discontinued - The Timestamp discontinued corresponding to the moment the computer has been removed from the market
 *  company - The Company manufacturing the computer
 *  
 *  attributenumber : associates an Integer to any field of Computer
 *  	-Long id : 0;
 *  	-String	name : 1;
 *  	-Timestamp introduced : 2;
 *  	-Timestamp discontinued : 3;
 *  	-Company company : 4;
 * @author excilys
 *
 */
@Component
@Scope("Prototype")
public class Computer implements Serializable {
	
/*****************************Builder*****************************/
	public static class CpuBuilder {
		private Long id;
		private String	name;
		private Timestamp introduced;
		private Timestamp discontinued;
		private Company company;
		
		/**
		 * 
		 * @param id
		 * @return
		 */
		public CpuBuilder id(Long id) {
			this.id = id;
			return this;
		}
		/**
		 * 
		 * @param name
		 * @return
		 */
		public CpuBuilder name(String name) {
			this.name = name;
			return this;
		}		
		/**
		 * 
		 * @param introduced
		 * @return
		 */
		public CpuBuilder introduced(Timestamp introduced) {
			this.introduced = introduced;
			return this;
		}
		/**
		 * 
		 * @param discontinued
		 * @return
		 */
		public CpuBuilder discontinued(Timestamp discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		/**
		 * 
		 * @param company
		 * @return
		 */
		public CpuBuilder company(Company company) {
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
	    public Computer build() {
	        return new Computer(this);
	    }
	}
/*****************************Builder*****************************/	
	
	/**
	 * 
	 * @return
	 */
	public static CpuBuilder builder() {
		return new CpuBuilder();
	}
	
	/**
	 * 
	 * @param builder
	 */
	private Computer(CpuBuilder builder) {
        this.id = builder.id;
        this.name = builder.name; 
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }	

	//Attributs
	private Long id;
	private String	name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;

	//Accesseurs
	/**
	 * 
	 * @return the id of the company
	 */
	public long getId() {
		return id;
	}
	/**
	 * 
	 * @param id The id of the company
	 */
	public void setId(long id) {
		this.id = id;
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
	 * @param name The name of the company
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return the Timestamp introduced
	 */
	public Timestamp getIntroduced() {
		return introduced;
	}
	/**
	 * 
	 * @param introduced The Timestamp introduced
	 */
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	/**
	 * 
	 * @return the Timestamp discontinued
	 */
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	/**
	 * 
	 * @param discontinued The Timestamp of discontinued
	 */
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	/**
	 * 
	 * @return the id of the manufacturing company 
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * 
	 * @param company_id The id of the manufacturing company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued + ", company="
				+ company + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof Computer))
			return false;
		Computer other = (Computer) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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