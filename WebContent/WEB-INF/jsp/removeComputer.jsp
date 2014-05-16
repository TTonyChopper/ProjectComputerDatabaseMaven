<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<jsp:include page="../../include/header.jsp" />
<section id="main">

	<h1>
		<spring:message code="name" />
	</h1>

	<form action="removeComputer" method="POST">
		<fieldset>
			<div class="clearfix">
				<label for="name"><spring:message code="name" /></label>
				<div class="input">
					<input readonly type="text" name="name" value="${rcomputer.name}" />
					<span class="help-inline"><spring:message code="required" /></span>
				</div>
			</div>

			<div class="clearfix">
				<label for="introduced"><spring:message code="introduced" /></label>
				<div class="input">
					<joda:format value="${rcomputer.introduced}" pattern="yyyy-MM-dd"
						var="introducedFormatted" />
					<input readonly type="date" name="introduced"
						value="${introducedFormatted}" /> <span class="help-inline"><spring:message
							code="pattern" /></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued"><spring:message
						code="discontinued" /></label>
				<div class="input">
					<joda:format value="${rcomputer.discontinued}" pattern="yyyy-MM-dd"
						var="discontinuedFormatted" />
					<input readonly type="date" name="discontinued"
						value="${discontinuedFormatted}" /> <span class="help-inline"><spring:message
							code="pattern" /></span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company"><spring:message code="company" /></label>
				<div class="input">
					<input readonly type="text" name="company"
						value="${rcomputer.company.name}" />
				</div>
			</div>
		</fieldset>

		<div class="actions">
			<input type="hidden" name="rid" value="${rcomputer.id}"> <input
				type="submit" value="<spring:message code="confirm"/>"
				class="btn btn-success">
			<spring:message code="or" />
			<a href="dashboard" class="btn btn-danger"><spring:message
					code="cancel" /></a>
		</div>
	</form>
</section>

<jsp:include page="../../include/footer.jsp" />