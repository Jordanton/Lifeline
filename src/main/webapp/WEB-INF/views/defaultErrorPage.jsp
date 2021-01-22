<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Lifeline - Error</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

</head>

<body>
<div id="page-container">
	<div id="header">
		<jsp:include page="header.jsp" />
	</div>
	
	<div id="content">
	
		<nav aria-label="breadcrumb mb-4">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="main">Start</a></li>
				<li class="breadcrumb-item active">Confirm Application</li>
			</ol>
		</nav>
	
		<div class="container">
			<div class="well">
	
				<div align="center">
					<h2 align="center">System Error</h2>
				</div>
	
				<div class="alert alert-info">
					<!--
					<strong>Please notify Office of Finance of your problem at
						1-844-663-4411 (during weekday business hours) or send an email to
						Customer Service at <a
						href="mailto:Finance.CustomerService@lacity.org?subject=System Problem with Web Cannabis Renewal"
						onmouseout="window.status='';return true"
						onmouseover="window.status='Send email to Customer Service';return true">
							Finance.CustomerService@lacity.org</a>.
					</strong>
					-->
					<strong>
					Please notify the web administrator of this issue.
					</strong>
					<p>
					${exceptionMsg}
					</p>
				</div>
	
			</div>
		</div>
	</div> <!-- End of content -->


</div> <!-- End of page-container -->
<footer class="footer">
	<jsp:include page="footer.jsp" />
</footer>
</body>
</html>