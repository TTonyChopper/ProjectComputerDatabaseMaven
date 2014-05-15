<%@ tag body-content="empty" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ attribute type="com.excilys.formation.webproject.common.PageWrapper" name="pageWrapper" %>

<c:set var="lPattern"> <spring:message code="langPattern"/> </c:set>

	<table class ="table table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th> <spring:message code="name"/> 							
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=cpu.name" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-up"></span></a>				 					 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=cpu.name&order=DESC" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span></a>			
					</th>			
					<!-- Table header for Introduced Date -->
					<th> <spring:message code="introduced"/> 			
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=cpu.introduced" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-up"></span></a>					 					 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=cpu.introduced&order=DESC" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span></a>	
					</th>	
					<!-- Table header for Discontinued Date -->
					<th> <spring:message code="discontinued"/>
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=cpu.discontinued" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-up"></span></a>					 					 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=cpu.discontinued&order=DESC" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span></a>	

					</th>
					<!-- Table header for Company Name-->
					<th> <spring:message code="company"/> 		
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=cpy.name" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-up"></span></a>					 					 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=cpy.name&order=DESC" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span></a>		
					</th>				
					<!-- Table header for Edit buttons-->
					<th> </th>
					<!-- Table header for Delete buttons-->
					<th> </th>
				</tr>
			</thead>
			
			
			
	<!--  Affichage de la page I de Computer de la base de données -->	
			<tbody>			
			<c:forEach items="${pageWrapper.computerList}" var="cpu">
				<tr>
					<td><a href="#">${cpu.name}</a></td>
					<td>
						<joda:format value="${cpu.introduced}" pattern="${lPattern}" />				
					</td>
					<td>
						<joda:format value="${cpu.discontinued}" pattern="${lPattern}" />
					</td>
					<td>${cpu.company.name}</td>
					<td> <form action="editComputer" method="GET">
						 <input type="submit" class="btn btn-warning" name="sbutton" value="<spring:message code="pagination.editComp"/>" >
						 <input type="hidden" name="eid" value="${cpu.id}">
						 </form>
					</td>
					<td> <form action="removeComputer" method="GET">
						 <input type="submit" class="btn btn-danger" name="rbutton" value="<spring:message code="pagination.deleteComp"/>" >
						 <input type="hidden" name="rid" value="${cpu.id}">
						 </form>
					</td>
				</tr>
			</c:forEach>
					
			<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}"><span class="glyphicon glyphicon-fast-backward"></span></a>
		
			<c:forEach items="${pageWrapper.pageIncrement}" var="INC">
				
				<c:if test="${pageWrapper.pageNumber + INC > 0 && pageWrapper.pageNumber + INC < (pageWrapper.computerListSize/25+1)}">
					<c:choose>
					<c:when test="${INC == 0}">
					<a class="btn btn-success" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=${pageWrapper.pageNumber}">${pageWrapper.pageNumber}</a>
					</c:when>
					<c:otherwise>
					<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=${pageWrapper.pageNumber + INC}">${pageWrapper.pageNumber + INC}</a>
					</c:otherwise>
					</c:choose>
				</c:if>
				
			</c:forEach>
			
			<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=last"><span class="glyphicon glyphicon-fast-forward"></span></a>
					
			</tbody>
			
		</table>
		
			<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}"><span class="glyphicon glyphicon-fast-backward"></span></a>
		
			<c:forEach items="${pageWrapper.pageIncrement}" var="INC">
				
				<c:if test="${pageWrapper.pageNumber + INC > 0 && pageWrapper.pageNumber + INC < (pageWrapper.computerListSize/25+1)}">
					<c:choose>
					<c:when test="${INC == 0}">
					<a class="btn btn-success" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=${pageWrapper.pageNumber}">${pageWrapper.pageNumber}</a>
					</c:when>
					<c:otherwise>
					<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=${pageWrapper.pageNumber + INC}">${pageWrapper.pageNumber + INC}</a>
					</c:otherwise>
					</c:choose>
				</c:if>
				
			</c:forEach>
			
			<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=last"><span class="glyphicon glyphicon-fast-forward"></span></a>
				