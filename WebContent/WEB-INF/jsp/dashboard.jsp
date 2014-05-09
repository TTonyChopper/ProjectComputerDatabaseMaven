<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/"%>
<jsp:include page="../../include/header.jsp" />

<section id="main">
	<h1 id="homeTitle">${pageWrapper.computerListSize} Computers found.</h1>
	<div id="actions">
		<form action="dashboard" method="GET">
			<input type="search" id="searchbox" name="nameFilter"
				value="${pageWrapper.nameFilter}" placeholder="Search name">
			<input type="submit" id="dashboard"
				value="Filter by name"
				class="btn btn-primary">
		</form>
		
		<a class="btn btn-success" id="add" href="addComputer" >Add Computer</a>
		
	</div>

	<page:Pagination pageWrapper="${pageWrapper}"/>
		
</section>

<jsp:include page="../../include/footer.jsp" />