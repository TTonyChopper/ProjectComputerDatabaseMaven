<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="../../include/header.jsp" />

<script type="text/javascript" src="js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>

<!--               JQuery               -->
 <script type="text/javascript">
 $(document).ready(function(){
	 
			jQuery.validator.addMethod("dateValid", function (value,element) { 
				var valid = true;  
				if ( (/^((19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12]\d|3[01]).*/i.test(value)) ) {
					var date = value.split('-');
					var year = parseInt(date[0]);
					var month = parseInt(date[1]);
					var day = parseInt(date[2]);
					if (day > 31 ) {valid = false;} 
					else if ( (day == 31) && ( (month == 2) || (month == 4) || (month == 6) || (month == 9) || (month == 11) ) ) {valid = false;} 
					else if ( ( month == 2 ) && (day > 29) ) {valid = false;} 
					else if ( ( month == 2 ) && (day == 29) && ( (year % 4) != 0) ) {valid = false;} 
				} else {valid = false;}			
				return ( this.optional(element) || valid);
				},"Date Invalide"	        
			);								
			
			jQuery.validator.addMethod("dateComparison", function (value,element) {
				if ( ($.trim(value).length > 0) && ($.trim($('#introduced').val()).length > 0) ) { return Date.parse($('#introduced').val()) <= Date.parse(value); }
				else {return true;}
				},"Dates Impossibles"
			);
			
     // Initialisation du plugin
	    $("#form").validate({
	    	rules : {
	        "name":{
	            required: true,
	            maxlength: 255
	        },
	    	"introduced":{
	    		maxlength: 255,
	    		dateValid: true
        	},
	    	"discontinued":{
	    		maxlength: 255,
	    		dateValid: true,
	    		dateComparison: true
        	},
	    	"company":{
	    		maxlength: 255
        	}
        	}
	    });
 });
 </script>
 <!--              /JQuery               -->

<section id="main">

	<h1><spring:message code="editComp"/></h1>
	
	<form:form id="form" action="editComputer" method="POST" commandName="computerDTO">
		<fieldset>
			<div class="clearfix">
				<label for="name"><spring:message code="name"/></label>
				<div class="input">
					<form:input type="text" name="name" value="${ecomputer.name}" path="name"/>
					<span class="help-inline"><spring:message code="required"/></span>
					<input readonly size="${errorlist.get(0).length()}" type="text" value="${errorlist.get(0)}"/>
				</div>
			</div>
	
			<div class="clearfix">
				<label for="introduced"><spring:message code="introduced"/></label>
				<div class="input">
					<form:input id="introduced" type="date" name="introduced" path="introduced" value="${ecomputer.introduced}" />
					<span class="help-inline"><spring:message code="pattern"/></span>
					<input readonly size="${errorlist.get(1).length()}" type="text" value="${errorlist.get(1)}"/>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued"><spring:message code="discontinued"/></label>
				<div class="input">
					<form:input id="discontinued" type="date" name="discontinued" path="discontinued" value="${ecomputer.discontinued}"/>
					<span class="help-inline"><spring:message code="pattern"/></span>
					<input readonly size="${errorlist.get(2).length()}" type="text" value="${errorlist.get(2)}"/>
				</div>
			</div>
			<div class="clearfix">
				<label for="company"><spring:message code="company"/></label>
				<div class="input">
					<form:select name="company" path="company">
							<option selected value="${ecomputer.company.id}">${ecomputer.company.name}</option>
						<c:forEach items="${companylist}" var="company">
							<option value="${company.id}">${company.name}</option>
						</c:forEach>
					</form:select>
					<span class="help-inline"><spring:message code="choose"/></span>
					<input readonly size="${errorlist.get(3).length()}" type="text" value="${errorlist.get(3)}"/>
				</div>
			</div>
		</fieldset>
		
		<div class="actions">
			<input type="hidden" name="eid" value="${ecomputer.id}">
			<input type="submit" value="<spring:message code="confirm"/>" class="btn btn-success">			
			<spring:message code="or"/> <a href="dashboard" class="btn btn-danger"><spring:message code="cancel"/></a>
		</div>
	</form:form>
</section>

<jsp:include page="../../include/footer.jsp" />