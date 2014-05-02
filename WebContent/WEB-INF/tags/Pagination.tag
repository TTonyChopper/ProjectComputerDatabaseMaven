<%@ tag body-content="empty" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute type="com.excilys.formation.webproject.dto.PageWrapper" name="pageWrapper" %>


	<table class ="table table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->
					<th> Computer Name 							
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=cpu.name&order=ASC&pageNumber=1" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-up"></span></a>				 					 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=cpu.name&order=DESC&pageNumber=1" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span></a>			
					</th>			
					<!-- Table header for Introduced Date -->
					<th>Introduced Date		
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=cpu.introduced&order=ASC&pageNumber=1" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-up"></span></a>					 					 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=cpu.introduced&order=DESC&pageNumber=1" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span></a>	
					</th>	
					<!-- Table header for Discontinued Date -->
					<th>Discontinued Date 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=cpu.discontinued&order=ASC&pageNumber=1" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-up"></span></a>					 					 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=cpu.discontinued&order=DESC&pageNumber=1" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span></a>	

					</th>
					<!-- Table header for Company Name-->
					<th> Company 		
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=cpy.name&order=ASC&pageNumber=1" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-up"></span></a>					 					 	
						<a href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=cpy.name&order=DESC&pageNumber=1" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-arrow-down"></span></a>		
					</th>				
					<!-- Table header for Edit buttons-->
					<th> </th>
					<!-- Table header for Delete buttons-->
					<th> </th>
				</tr>
			</thead>
			
			
			
	<!--  Affichage de la page I de Computer de la base de donn�es -->	
			<tbody>			
			<c:forEach items="${pageWrapper.computerList}" var="cpu">
				<tr>
					<td><a href="#">${cpu.name}</a></td>
					<td>${cpu.introduced}</td>
					<td>${cpu.discontinued}</td>
					<td>${cpu.company.name}</td>
					<td> <form action="editComputer" method="GET">
						 <input type="submit" class="btn btn-warning" name="ebutton" value="Edit" >
						 <input type="hidden" name="eid" value="${cpu.id}">
						 </form>
					</td>
					<td> <form action="removeComputer" method="GET">
						 <input type="submit" class="btn btn-danger" name="rbutton" value="Delete" >
						 <input type="hidden" name="rid" value="${cpu.id}">
						 </form>
					</td>
				</tr>
			</c:forEach>
					
			<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=1"><span class="glyphicon glyphicon-fast-backward"></span></a>
		
			<c:forEach items="${pageWrapper.pageIncrement}" var="INC">
				
				<c:if test="${pageWrapper.pageNumber + INC > 0 && pageWrapper.pageNumber + INC < (pageWrapper.computerListSize/25)}">
					<c:choose>
					<c:when test="${INC == 0}">
					<a class="btn btn-success" href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=${pageWrapper.pageNumber}">${pageWrapper.pageNumber}</a>
					</c:when>
					<c:otherwise>
					<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=${pageWrapper.pageNumber + INC}">${pageWrapper.pageNumber + INC}</a>
					</c:otherwise>
					</c:choose>
				</c:if>
				
			</c:forEach>
			
			<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=last"><span class="glyphicon glyphicon-fast-forward"></span></a>
					
			</tbody>
			
		</table>
		
			<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=1"><span class="glyphicon glyphicon-fast-backward"></span></a>
		
			<c:forEach items="${pageWrapper.pageIncrement}" var="INC">
				
				<c:if test="${pageWrapper.pageNumber + INC > 0 && pageWrapper.pageNumber + INC < (pageWrapper.computerListSize/25)}">
					<c:choose>
					<c:when test="${INC == 0}">
					<a class="btn btn-success" href="dashboard?nameFilter=${pageWrapper.nameFilter}&fieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=${pageWrapper.pageNumber}">${pageWrapper.pageNumber}</a>
					</c:when>
					<c:otherwise>
					<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=${pageWrapper.pageNumber + INC}">${pageWrapper.pageNumber + INC}</a>
					</c:otherwise>
					</c:choose>
				</c:if>
				
			</c:forEach>
			
			<a class="btn btn-default" href="dashboard?nameFilter=${pageWrapper.nameFilter}&ffieldOrder=${pageWrapper.fieldOrder}&order=${pageWrapper.order}&pageNumber=last"><span class="glyphicon glyphicon-fast-forward"></span></a>
				