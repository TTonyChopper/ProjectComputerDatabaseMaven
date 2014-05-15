<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<c:forEach items="${param}" var="currentParam">
		<c:if test="${currentParam.key != 'lang'}">
			<c:set var="params" value="${params}&${currentParam.key}=${currentParam.value}" />
		</c:if>
    </c:forEach>

<!DOCTYPE html>
<html>
<head>
<title>EPF Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">

</head>
<body>
	<header class="navbar navbar-fixed-top navbar-inverse">
		<h1 class="fill">
			<a href="index.jsp"> <spring:message code="header.name"/> </a>
		</h1>
		
		<a href="?lang=en${params}"> <spring:message code="en"/> </a>
		<a href="?lang=fr${params}"> <spring:message code="fr"/> </a>
	</header>