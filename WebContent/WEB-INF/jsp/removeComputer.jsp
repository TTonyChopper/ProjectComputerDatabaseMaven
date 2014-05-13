<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../../include/header.jsp" />
<section id="main">

	<h1>Remove Computer</h1>
	
	<form action="removeComputer" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<input readonly type="text" name="name" value="${rcomputer.name}" />
					<span class="help-inline">Required</span>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<input readonly type="date" name="introducedDate" value="${rcomputer.introduced}" />
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<input readonly type="date" name="discontinuedDate" value="${rcomputer.discontinued}" />
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<input readonly type="date" name="discontinuedDate" value="${rcomputer.company.name}" />
				</div>
			</div>
		</fieldset>
		
		<div class="actions">
			<input type="hidden" name="rid" value="${rcomputer.id}">
			<input type="submit" value="delete" class="btn btn-success">			
			or <a href="dashboard" class="btn btn-danger">Cancel</a>
		</div>
	</form>
</section>

<jsp:include page="../../include/footer.jsp" />