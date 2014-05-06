package com.excilys.formation.webproject.common;

import java.io.Serializable;
import java.util.List;

import com.excilys.formation.webproject.om.Computer;

/**
 * 
 * @author excilys
 *
 */
public class PageWrapper implements Serializable {

	/**
	 * 
	 * @author excilys
	 *
	 */
	public static class PwBuilder {
		private String nameFilter;
		private Integer pageNumber;
		private String	fieldOrder;
		private String order;
		private List<Computer> computerList;
		private Integer computerListSize;
		
		/**
		 * 
		 * @param nameFilter
		 * @return
		 */
		public PwBuilder nameFilter(String nameFilter) {
			this.nameFilter = nameFilter;
			return this;
		}
		/**
		 * 
		 * @param pagenumber
		 * @return
		 */
		public PwBuilder pageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
			return this;
		}
		/**
		 * 
		 * @param fieldorder
		 * @return
		 */
		public PwBuilder fieldOrder(String fieldOrder) {
			this.fieldOrder = fieldOrder;
			return this;
		}
		/**
		 * 
		 * @param order
		 * @return
		 */
		public PwBuilder order(String order) {
			this.order = order;
			return this;
		}
		/**
		 * 
		 * @param computerlist
		 * @return
		 */
		public PwBuilder computerList(List<Computer> computerList) {
			this.computerList = computerList;
			return this;
		}
		/**
		 * 
		 * @param computerlistsize
		 * @return
		 */
		public PwBuilder computerListSize(Integer computerListSize) {
			this.computerListSize = computerListSize;
			return this;
		}
		/**
		 * 
		 * @return
		 */
		public PageWrapper build() {
		    return new PageWrapper(this);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static PwBuilder builder() {
		return new PwBuilder();
	}
	
	/**
	 * 
	 * @param builder
	 */
	private PageWrapper(PwBuilder builder) {
		this.nameFilter = builder.nameFilter;
        this.pageNumber = builder.pageNumber;
        this.fieldOrder = builder.fieldOrder; 
        this.order = builder.order;
        this.computerList = builder.computerList;
        this.computerListSize = builder.computerListSize;
    }	

	//Attributs
	private String nameFilter;
	private Integer pageNumber;
	private String	fieldOrder;
	private String order;
	private List<Computer> computerList;
	private Integer computerListSize;
	public final static int PAGE_INCREMENT[]={-10,-5,-2,-1,0,1,2,5,10};
	public final static int PER_PAGE = 25;
	
	//Auto-generations		
	public int[] getPageIncrement() {
		return PAGE_INCREMENT;
	}
	
	public int getPerPage() {
		return PER_PAGE;
	}
	
	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(String fieldOrder) {
		this.fieldOrder = fieldOrder;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<Computer> getComputerList() {
		return computerList;
	}

	public void setComputerList(List<Computer> computerList){
		this.computerList = computerList;
	}

	public Integer getComputerListSize() {
		return computerListSize;
	}

	public void setComputerListSize(Integer computerListSize) {
		this.computerListSize = computerListSize;
	}

	@Override
	public String toString() {
		return "PageWrapper [nameFilter=" + nameFilter + ", pageNumber="
				+ pageNumber + ", fieldOrder=" + fieldOrder + ", order="
				+ order + ", computerList=" + computerList
				+ ", computerListSize=" + computerListSize + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computerList == null) ? 0 : computerList.hashCode());
		result = prime
				* result
				+ ((computerListSize == null) ? 0 : computerListSize.hashCode());
		result = prime * result
				+ ((fieldOrder == null) ? 0 : fieldOrder.hashCode());
		result = prime * result
				+ ((nameFilter == null) ? 0 : nameFilter.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result
				+ ((pageNumber == null) ? 0 : pageNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PageWrapper))
			return false;
		PageWrapper other = (PageWrapper) obj;
		if (computerList == null) {
			if (other.computerList != null)
				return false;
		} else if (!computerList.equals(other.computerList))
			return false;
		if (computerListSize == null) {
			if (other.computerListSize != null)
				return false;
		} else if (!computerListSize.equals(other.computerListSize))
			return false;
		if (fieldOrder == null) {
			if (other.fieldOrder != null)
				return false;
		} else if (!fieldOrder.equals(other.fieldOrder))
			return false;
		if (nameFilter == null) {
			if (other.nameFilter != null)
				return false;
		} else if (!nameFilter.equals(other.nameFilter))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (pageNumber == null) {
			if (other.pageNumber != null)
				return false;
		} else if (!pageNumber.equals(other.pageNumber))
			return false;
		return true;
	}
}