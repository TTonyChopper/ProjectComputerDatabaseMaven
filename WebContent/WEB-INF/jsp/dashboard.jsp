<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="../../include/header.jsp" />

<section id="main">
	<h1 id="homeTitle">${pageWrapper.computerListSize} <spring:message code="dashboard.listResult"/></h1>
	<div id="actions">
		<form action="dashboard" method="GET">
			<input type="search" id="searchbox" name="nameFilter"
				value="${pageWrapper.nameFilter}" placeholder="<spring:message code="dashboard.searchbar"/>">
			<input type="submit" id="dashboard"
				value="<spring:message code="dashboard.search"/>"
				class="btn btn-primary">
		</form>
		
		<a class="btn btn-success" id="add" href="addComputer" ><spring:message code="addComp"/></a>
		
	</div>

	<page:Pagination pageWrapper="${pageWrapper}"/>
		
</section>

<jsp:include page="../../include/footer.jsp" />