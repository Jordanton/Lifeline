<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
  <title>Lifeline - Senior Citizen Exception & Refund System</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js'></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script
	src='https://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />

<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Roboto Condensed" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/css/jasny-bootstrap.min.css">

<!-- Latest compiled and minified JavaScript -->
<!--script src="//cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/js/jasny-bootstrap.min.js"></script-->

<!--  Stylesheet for Office of Finance -->
<link rel="stylesheet" type="text/css" href="./resources/css/styles.css">
  
</head>
<body>
<div id="page-container">
	<div id="header">
			<jsp:include page="header.jsp" />
	</div>
	
	<div id="content">
		<!-- Breadcrumb Navbar -->
		<nav aria-label="breadcrumb mb-4">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="main">Start</a></li>
				<li class="breadcrumb-item active">New Applicant</li>
			</ol>
		</nav>

		<!-- Main container -->
		<div class="container">						

			<form:form class="form-horizontal" id="applicantForm" method="POST" action="submit" modelAttribute="applicant"
				enctype="multipart/form-data">
			<div class="card">
				<div class="card-body">
					<div class="heading-container center mb-3">
						<h3 class="card-title">UTILITY USER'S TAX EXEMPTION/ELECTRIC & WATER LIFELINE RATE APPLICATION</h3>
						<h4 class="card-subtitle mb-2 text-muted">Los Angeles City Residents Only</h4>
						<a tabindex="-1" href="javascript://" data-toggle="popover"  data-trigger="focus" 
							data-content="This discount is only available to seniors or people with disabilities residing within the city of Los Angeles.">
							<i class="fa fa-question-circle"></i>
						</a>
						<h5>
							<span class="text-danger ${not empty globalErrors ? '' : 'collapse'}" >Please correct following errors:</span>
						</h5>
						<h6 class="text-danger">
						<spring:hasBindErrors name="applicant">
							<c:forEach var="error" items="${globalErrors}">
								-<spring:message message="${error}"/><br/>
							</c:forEach>
						-<form:errors path="utility" class="col-form-label ${status.error ? 'text-danger' : ''}" />
						</spring:hasBindErrors>
						
						</h6>
					</div>
					
					 <!-- Applicant information -->
					<fieldset id="section-applicant">
						<legend class="px-3">Applicant Information</legend>
						<div class="card-body">
							<div class="form-row">
								
								<spring:bind path="firstName">
								<div class="form-group col ${status.error ? 'text-danger' : ''}">
									<label class="required" for="firstName">First Name</label>
									<form:input path="firstName" id="firstName" type="text" 
										class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
									<form:errors path="firstName" class="small" />
								</div>
								</spring:bind>
								
								<spring:bind path="lastName">
								<div class="form-group col ${status.error ? 'text-danger' : ''}">
									<label class="required" for="lastName">Last Name</label>
									<form:input path="lastName" id="lastName" type="text"
										class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
									<form:errors path="lastName" class="small" />
								</div>
								</spring:bind>
								
								<spring:bind path="middleName">
								<div class="form-group col ${status.error ? 'text-danger' : ''}">
									<label for="middleName">Middle Initial</label>
									<form:input path="middleName" id="middleName" type="text"
										class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
									<form:errors path="middleName" class="small" />
								</div>
								</spring:bind>
							</div>
							
							<div class="form-row">
								<spring:bind path="emailAddress">
								<div class="form-group col ${status.error ? 'text-danger' : ''}">
									<label for="emailAddress">Email Address</label>
									<form:input path="emailAddress" id="emailAddress" type="text"
										class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="name@domain.com" />
									<form:errors path="emailAddress" class="col-form-label" />
								</div>
								</spring:bind>
							</div>
		
							<div class="form-row">					
								<spring:bind path="classCode">
								<div class="form-group col ${status.error ? 'text-danger' : ''}">
									<label class="required" for="classCode">Class</label>
									<form:select path="classCode" id="classCode" class="form-control ${status.error ? 'is-invalid' : ''}">
										<form:option value="${null}" label="--- Select ---" />
										<form:option value="S" label="Senior" />
										<form:option value="D" label="Disabled" />
									</form:select>
									<form:errors path="classCode" class="col-form-label" />
								</div>
								</spring:bind>
								
								<spring:bind path="dateOfBirth">
								<div class="form-group col ${status.error ? 'text-danger' : ''}">
									<label class="required" for="dateOfBirth">Date of Birth</label>
									<form:input path="dateOfBirth" id="dateOfBirth" type="date" placeholder="YYYY-MM-DD"
										class="form-control ${status.error ? 'is-invalid' : ''}" required="required"/>
									<form:errors path="dateOfBirth" class="col-form-label" />
								</div>
								</spring:bind>
			
								<spring:bind path="dayPhone">
								<div class="form-group col ${status.error ? 'text-danger' : ''}">
									<label class="required" for="dayPhone">Day Phone</label>
									<form:input path="dayPhone" id="dayPhone" type="text"
										class="form-control ${status.error ? 'is-invalid' : ''}" maxlength="10" placeholder="9991234567" />
									<form:errors path="dayPhone" class="col-form-label" />
								</div>
								</spring:bind>
							</div>							
						</div> <!-- End of card-body for applicant information -->
					</fieldset> <!-- End of applicant information -->
	
					<!-- Service address -->
					<div class="form-horizontal">

						<div class="collapse-toggle-txt" id="headingService">
							<legend class="px-3 heading">Service Address</legend>
						</div>
						
						<div class="card-body" id="bodyService">
							<fieldset id="section-service">
								<h6 class="card-subtitle mb-2">Address Line 1</h6>
									
								<div class="form-row">
									<spring:bind path="serviceAddress.streetNum">
									<div class="form-group col-sm-2 ${status.error ? 'text-danger' : ''}">
									<label class="required" for="serviceAddress.streetNum" class="text-muted small">Street Number</label>
										<form:input path="serviceAddress.streetNum" id="serviceAddress.streetNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 12345" maxlength="5" />
										<form:errors path="serviceAddress.streetNum" class="small" />
									</div>
									</spring:bind>
									
									<spring:bind path="serviceAddress.fracNum">
									<div class="form-group col-sm-1 ${status.error ? 'text-danger' : ''}">
										<label for="serviceAddress.fracNum" class="text-muted small">Fraction</label>
										<form:input path="serviceAddress.fracNum" id="serviceAddress.fracNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 1/2" maxlength="4" />
										<form:errors path="serviceAddress.fracNum" class="small" />
									</div>
									
									</spring:bind>
									<spring:bind path="serviceAddress.streetDir">
									<div class="form-group col-sm-1 ${status.error ? 'text-danger' : ''}">
										<label for="serviceAddress.streetDir" class="text-muted small">Direction</label>
										<form:select path="serviceAddress.streetDir" name="serviceAddress.streetDir"
											class="form-control ${status.error ? 'is-invalid' : ''}">
											<form:option value="" label="---" />
											<form:options items="${streetDirections}"/>
										</form:select>
									</div>
									</spring:bind>
								</div> <!-- End of form-row -->
								
								<div class="form-row">
									<spring:bind path="serviceAddress.streetName">
									<div class="form-group col-sm-8 ${status.error ? 'text-danger' : ''}">
										<label class="required" for="serviceAddress.streetName" class="text-muted small">Street Name</label>
										<form:input path="serviceAddress.streetName" id="serviceAddress.streetName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. Broadway" maxlength="28" />
										<form:errors path="serviceAddress.streetName" class="small" />
									</div>
									</spring:bind>
									
									<spring:bind path="serviceAddress.streetType">
									<div class="form-group col-sm-4 ${status.error ? 'text-danger' : ''}">
										<label class="required" for="serviceAddress.streetType" class="text-muted small">Street Type</label>
										<form:input path="serviceAddress.streetType" id="serviceAddress.streetType" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. St" maxlength="2" />
										<form:errors path="serviceAddress.streetType" class="small" />
									</div>
									</spring:bind>
								</div> <!-- End of form-row -->
								
								<h6 class="card-subtitle mb-2">Address Line 2</h6>
								
								<div class="form-row">
									<spring:bind path="serviceAddress.unitNum">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label for="serviceAddress.unitNum" class="text-muted small">Unit Number</label>
										<form:input path="serviceAddress.unitNum" id="serviceAddress.unitNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 405" maxlength="7" />
										<form:errors path="serviceAddress.unitNum" class="small" />
									</div>
									</spring:bind>
								</div> <!-- End of form-row -->	
								
								<div class="form-row">
									<spring:bind path="serviceAddress.city">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="serviceAddress.city">City</label>
										<form:input path="serviceAddress.city" id="serviceAddress.city" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. Los Angeles" maxlength="25" />
										<form:errors path="serviceAddress.city" class="small" />
									</div>
									</spring:bind>
									
									<spring:bind path="serviceAddress.state">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="serviceAddress.state">State</label>
										<form:input path="serviceAddress.state" id="serviceAddress.state" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" value="CA" placeholder="CA" disabled="disabled"/>
										<form:errors path="serviceAddress.state" class="small" />
									</div>
									</spring:bind>
			
									<spring:bind path="serviceAddress.zipCode">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="serviceAddress.zipCode">ZIP</label>
										<form:input path="serviceAddress.zipCode" id="serviceAddress.zipCode" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 90012" maxlength="9" />
										<form:errors path="serviceAddress.zipCode" class="small" />
									</div>
									</spring:bind>
								</div> <!-- End of form-row -->
			
								<div class="form-row">
								
									<spring:bind path="serviceAddress.residenceType">
									<div class="form-group col-sm-4 ${status.error ? 'text-danger' : ''}">
										<label class="required" for="serviceAddress.residenceType">Address Type</label>
										<form:select path="serviceAddress.residenceType" id="serviceAddress.residenceType" 
											class="form-control ${status.error ? 'is-invalid' : ''}">
											<form:option value="S" label="Single Residence" />
											<form:option value="M" label="Mobile Home" />
										</form:select>
										<form:errors path="serviceAddress.residenceType" class="col-form-label" />
									</div>
									</spring:bind>
									
									<spring:bind path="serviceAddress.mobileParkName">
									<div class="form-group col-sm-8 ${status.error ? 'text-danger' : ''}">
										<label for="serviceAddress.mobileParkName">Mobile Park</label>
										<form:input path="serviceAddress.mobileParkName" id="serviceAddress.mobileParkName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MOBILE PARK NAME"/>
										<form:errors path="serviceAddress.mobileParkName" class="small" />
									</div>
									</spring:bind>
								</div>
			
								<form:errors path="serviceAddress" class="col-form-label ${status.error ? 'text-danger' : ''}" />
							</fieldset> <!-- End of service address -->
						</div> <!-- End of card-body for service address -->

					</div> <!-- End of form-horizontal -->
					
					<!-- Mailing address -->
					<div class="form-horizontal mb-2">
						<div class="collapse-toggle-txt" id="headingMailing">
							<legend class="px-3 heading">Mailing Address</legend>
							<input checked id="sameAsServiceChecked" name="sameAsServiceChecked" type="checkbox" data-toggle="collapse" data-target="#bodyMailing">
							<span class="font-weight-light h6">Check if same as service address</span>
						</div>
						
						<div class="card-body collapse" id="bodyMailing">
							<fieldset id="section-mailing">
								<h6 class="card-subtitle mb-2">Address Line 1</h6>
									
								<div class="form-row">
									<spring:bind path="mailingAddress.streetNum">
									<div class="form-group col-sm-2 ${status.error ? 'text-danger' : ''}">
										<label class="required" for="mailingAddress.streetNum" class="text-muted small">Street Number</label>
										<form:input path="mailingAddress.streetNum" id="mailingAddress.streetNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 12345" maxlength="5" />
										<form:errors path="mailingAddress.streetNum" class="small" />
									</div>
									</spring:bind>
									
									<spring:bind path="mailingAddress.fracNum">
									<div class="form-group col-sm-1 ${status.error ? 'text-danger' : ''}">
										<label for="mailingAddress.fracNum" class="text-muted small">Fraction</label>
										<form:input path="mailingAddress.fracNum" id="mailingAddress.fracNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 1/2" maxlength="4" />
										<form:errors path="mailingAddress.fracNum" class="small" />
									</div>
									
									</spring:bind>
									<spring:bind path="mailingAddress.streetDir">
									<div class="form-group col-sm-1 ${status.error ? 'text-danger' : ''}">
										<label for="mailingAddress.streetDir" class="text-muted small">Direction</label>
										<form:select path="mailingAddress.streetDir" name="mailingAddress.streetDir"
											class="form-control ${status.error ? 'is-invalid' : ''}">
											<form:option value="" label="---" />
											<form:options items="${streetDirections}"/>
										</form:select>
									</div>
									</spring:bind>
								</div> <!-- End of form-row -->
								
								<div class="form-row">
									<spring:bind path="mailingAddress.streetName">
									<div class="form-group col-sm-8 ${status.error ? 'text-danger' : ''}">
										<label class="required" for="mailingAddress.streetName" class="text-muted small">Street Name</label>
										<form:input path="mailingAddress.streetName" id="mailingAddress.streetName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. Broadway" maxlength="28" />
										<form:errors path="mailingAddress.streetName" class="small" />
									</div>
									</spring:bind>
									
									<spring:bind path="mailingAddress.streetType">
									<div class="form-group col-sm-4 ${status.error ? 'text-danger' : ''}">
										<label class="required" for="mailingAddress.streetType" class="text-muted small">Street Type</label>
										<form:input path="mailingAddress.streetType" id="mailingAddress.streetType" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. St" maxlength="2" />
										<form:errors path="mailingAddress.streetType" class="small" />
									</div>
									</spring:bind>
								</div> <!-- End of form-row -->
								
								<h6 class="card-subtitle mb-2">Address Line 2</h6>
								
								<div class="form-row">
									<spring:bind path="mailingAddress.unitNum">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label for="mailingAddress.unitNum" class="text-muted small">Unit Number</label>
										<form:input path="mailingAddress.unitNum" id="mailingAddress.unitNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 405" maxlength="7" />
										<form:errors path="mailingAddress.unitNum" class="small" />
									</div>
									</spring:bind>
								</div> <!-- End of form-row -->	
								
								<div class="form-row">
									<spring:bind path="mailingAddress.city">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="mailingAddress.city">City</label>
										<form:input path="mailingAddress.city" id="mailingAddress.city" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. Los Angeles" maxlength="25" />
										<form:errors path="mailingAddress.city" class="small" />
									</div>
									</spring:bind>
									
									<spring:bind path="mailingAddress.state">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="mailingAddress.state">State</label>
										<form:input path="mailingAddress.state" id="mailingAddress.state" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" value="CA" placeholder="CA" disabled="disabled"/>
										<form:errors path="mailingAddress.state" class="small" />
									</div>
									</spring:bind>
			
									<spring:bind path="mailingAddress.zipCode">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="mailingAddress.zipCode">ZIP</label>
										<form:input path="mailingAddress.zipCode" id="mailingAddress.zipCode" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 90012" maxlength="9" />
										<form:errors path="mailingAddress.zipCode" class="small" />
									</div>
									</spring:bind>
								</div> <!-- End of form-row -->
			
								<div class="form-row">
								
									<spring:bind path="mailingAddress.residenceType">
									<div class="form-group col-sm-4 ${status.error ? 'text-danger' : ''}">
										<label class="required" for="mailingAddress.residenceType">Address Type</label>
										<form:select path="mailingAddress.residenceType" id="mailingAddress.residenceType" 
											class="form-control ${status.error ? 'is-invalid' : ''}">
											<form:option value="S" label="Single Residence" />
											<form:option value="M" label="Mobile Home" />
										</form:select>
										<form:errors path="mailingAddress.residenceType" class="col-form-label" />
									</div>
									</spring:bind>
									
									<spring:bind path="mailingAddress.mobileParkName">
									<div class="form-group col-sm-8 ${status.error ? 'text-danger' : ''}">
										<label for="mailingAddress.mobileParkName">Mobile Park</label>
										<form:input path="mailingAddress.mobileParkName" id="mailingAddress.mobileParkName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MOBILE PARK NAME"/>
										<form:errors path="mailingAddress.mobileParkName" class="small" />
									</div>
									</spring:bind>
								</div>
			
								<form:errors path="mailingAddress" class="col-form-label ${status.error ? 'text-danger' : ''}" />
							</fieldset> <!-- End of mailing address -->
						</div> <!-- End of card-body for mailing address -->

					</div> <!-- End of form-horizontal -->
					
					<hr/>
					
					<h5 class="card-title">
						<legend class="px-3 heading">Utilities</legend>
						<c:set var="utilityErrors"><form:errors path="utility"/></c:set>
						<c:if test="${not empty utilityErrors}">
							&#9888; <form:errors path="utility" class="small col-form-label text-danger"/>
						</c:if>
					</h5>
					<p class="card-text">
						For each utility section filled out, you will be prompted on the next page to submit documentation. Please prepare to enclose a 
						photocopy of your most recent utility bill for each utility for which you are requesting an exemption.
					<br>
					<strong>&bull; The exemption cannot be granted if the name that appears on the utility bill is not the same as the applicant's name.</strong>
					<br>
					<strong>&bull; You are required to fill out the DWP section in order to submit this form. The other utilities are optional.</strong>
					</p>
					
					<!-- DWP -->
					<div class="card mb-2">
						<div class="card-header" id="headingDWP">
							<span class="collapse-toggle-txt required">Department of Water and Power (Required)</span>
							<button type="button" class="btn btn-outline-info btn-sm pull-right" id="btn-add-remove-dwp"
								data-toggle="collapse" data-target="#bodyDWP" aria-expanded="true">
							Add/Remove
							</button>					
						</div>
						
						<div class="card-body collapse" id="bodyDWP">
							<div class="form-horizontal" id="dwpWrapper">
							
								<fieldset id="section-dwp" disabled>
								<input type="hidden" id="utility[0].supplierCode" name="utility[0].supplier.supplierCode" value="DWP"/>
							
								<h6 class="card-subtitle mb-2">Name shown on bill</h6>
								<div class="form-row">
								
									<spring:bind path="utility[0].firstName">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required"  for="utility[0].firstName">First Name</label>
										<form:input path="utility[0].firstName" name="utility[0].firstName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
										<form:errors path="utility[0].firstName" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[0].lastName">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[0].lastName">Last Name</label>
										<form:input path="utility[0].lastName" name="utility[0].lastName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
										<form:errors path="utility[0].lastName" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[0].middleInitial">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label for="utility[0].middleInitial">Middle Initial</label>
										<form:input path="utility[0].middleInitial" name="utility[0].middleInitial" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
										<form:errors path="utility[0].middleInitial" class="small" />
									</div>
									</spring:bind>
								</div>
		
								<div class="form-row">
									<spring:bind path="utility[0].accountNum">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[0].accountNum">Account Number</label>
										<form:input path="utility[0].accountNum" name="utility[0].accountNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="ACCOUNT NUM" maxlength="10"/>
										<form:errors path="utility[0].accountNum" class="small" />
									</div>
									</spring:bind>
								</div>
								
								<div class="form-row">
									<spring:bind path="utility[0].includedInRent">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<div>
											<label class="required" for="utility[0].includedInRent">Utility Included in Rent?</label>
											<form:radiobutton path="utility[0].includedInRent" value="N" label="No" checked="checked"/>
											<form:radiobutton path="utility[0].includedInRent" value="Y" label="Yes" />
										</div>
										<form:errors path="utility[0].includedInRent" class="small" />
									</div>
										
									</spring:bind>
									<spring:bind path="utility[0].householdCount">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[0].householdCount">Number of People in Household</label>
										<form:input path="utility[0].householdCount" name="utility[0].householdCount" type="number"
											value="${applicant.utility[0].householdCount}"
											class="form-control ${status.error ? 'is-invalid' : ''}" maxlength="38" style="width: 80px" min="1" required="required"/>
										<form:errors path="utility[0].householdCount" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[0].serviceRequested">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[0]serviceRequested">Lifeline Services Requested</label>
										<form:select path="utility[0].serviceRequested" name="applicant.utility[0].serviceRequested" 
											class="form-control ${status.error ? 'is-invalid' : ''}">
											<form:option value="E">Electric Only</form:option>
											<form:option value="W">Water Only</form:option>
											<form:option value="B">Water and Electric</form:option>
											<form:option value="L">Lifeline Only</form:option>
										</form:select>
										<form:errors path="utility[0].serviceRequested" class="small" />
									</div>
									</spring:bind>
								</div>
								
								</fieldset> <!-- End of section-dwp -->
							</div>
						</div> <!-- End of card-body for DWP -->
					</div> <!-- End of card for DWP -->
	
					<!-- SCG -->
					<div class="card mb-2">
						<div class="card-header" id="headingSCG">
							<span class="collapse-toggle-txt">Southern California Gas (Optional)</span>
							<button type="button" class="btn btn-outline-info btn-sm pull-right" id="btn-add-remove-scg"
								data-toggle="collapse" data-target="#bodySCG" aria-expanded="true">
							Add/Remove
							</button>					
						</div>
						
						<div class="card-body collapse" id="bodySCG">
							<div class="form-horizontal" id="scgWrapper">
							
							<fieldset id="section-scg" disabled>
								<input type="hidden" id="utility[1].supplierCode" name="utility[1].supplier.supplierCode" value="SCG"/>
								
								<h6 class="card-subtitle mb-2">Name shown on bill</h6>
								<div class="form-row">
									<spring:bind path="utility[1].firstName">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[1].firstName">First Name</label>
										<form:input path="utility[1].firstName" name="utility[1].firstName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
										<form:errors path="utility[1].firstName" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[1].lastName">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[1].lastName">Last Name</label>
										<form:input path="utility[1].lastName" name="utility[1].lastName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
										<form:errors path="utility[1].lastName" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[1].middleInitial">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label for="utility[1].middleInitial">Middle Initial</label>
										<form:input path="utility[1].middleInitial" name="utility[1].middleInitial" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
										<form:errors path="utility[1].middleInitial" class="small" />
									</div>
									</spring:bind>
								</div>
		
								<div class="form-row">
									<spring:bind path="utility[1].accountNum">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
									<label class="required" for="utility[1].accountNum">Account Number</label>
										<form:input path="utility[1].accountNum" name="utility[1].accountNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" maxlength="11" placeholder="ACCOUNT NUMBER" />
										<form:errors path="utility[1].accountNum" class="small" />
									</div>
									</spring:bind>
								</div>
								
								<div class="form-row">
									<spring:bind path="utility[1].includedInRent">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<div>
											<label class="required" for="utility[1].includedInRent">Utility Included in Rent?</label>
											<form:radiobutton path="utility[1].includedInRent" value="N" label="No" checked="checked"/>
											<form:radiobutton path="utility[1].includedInRent" value="Y" label="Yes" />
										</div>
									</div>
									<form:errors path="utility[1].includedInRent" class="small" />
									</spring:bind>
								</div>
								
							</fieldset> <!-- End of section-scg -->
							
							</div> <!-- End of scgWrapper -->
						</div> <!-- End of card-body -->
					</div> <!-- End of card -->
					
					<!-- Landline -->
					<div class="card mb-2">
						<div class="card-header" id="headingLandline">
							<span class="collapse-toggle-txt">Landline Telephone Service Provider (Optional)</span>
							<button type="button" class="btn btn-outline-info btn-sm pull-right" id="btn-add-remove-landline"
								data-toggle="collapse" data-target="#bodyLandline" aria-expanded="true">
							Add/Remove
							</button>					
						</div>
						
						<div class="card-body collapse" id="bodyLandline">
							
							<div class="form-horizontal" id="landlineWrapper">	
							
							<fieldset id="section-landline" disabled>				
								<h6 class="card-subtitle mb-2">Name shown on bill</h6>
								<div class="form-row">
									<spring:bind path="utility[2].firstName">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[2].firstName">First Name</label>
										<form:input path="utility[2].firstName" name="utility[2].firstName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
										<form:errors path="utility[2].firstName" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[2].lastName">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[2].lastName">Last Name</label>
										<form:input path="utility[2].lastName" name="utility[2].lastName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
										<form:errors path="utility[2].lastName" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[2].middleInitial">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label for="utility[2].middleInitial">Middle Initial</label>
										<form:input path="utility[2].middleInitial" name="utility[2].middleInitial" type="text" 
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
										<form:errors path="utility[2].middleInitial" class="small" />
									</div>
									</spring:bind>
								</div>
								
								<div class="form-row">
									<spring:bind path="utility[2].supplier.supplierCode">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[2].supplier.supplierCode">Service Company Name</label>
										<form:select path="utility[2].supplier.supplierCode" name="utility[2].supplier.supplierCode"
											class="form-control ${status.error ? 'is-invalid' : ''}">
											<form:option value="${null}" label="--- Select ---" />
											<form:options items="${landlineSuppliers}" itemValue="supplierCode" itemLabel="supplierName" />
										</form:select>
										<form:errors path="utility[2].supplier.supplierCode" class="small" />
									</div>
									</spring:bind>
									
									<spring:bind path="utility[2].phoneNum">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[2].phoneNum">Residence Telephone</label>
										<form:input path="utility[2].phoneNum" name="utility[2].phoneNum" type="text" 
											class="form-control ${status.error ? 'is-invalid' : ''}" maxlength="10" placeholder="i.e. 9991234567" />
										<form:errors path="utility[2].phoneNum" class="small" />
									</div>
									</spring:bind>
								</div>
							</fieldset>	
							</div> <!-- End of landlineWrapper -->
						</div> <!-- End of card-body -->
					</div> <!-- End of well -->				
										
					<!-- Cellular -->			
					<div class="card mb-2">
						<div class="card-header" id="headingCellular">
							<span class="collapse-toggle-txt">Cellular Telephone Service Provider (Optional)</span>
							<button type="button" class="btn btn-outline-info btn-sm pull-right" id="btn-add-remove-cellular"
								data-toggle="collapse" data-target="#bodyCellular" aria-expanded="true">
							Add/Remove
							</button>					
						</div>
						
						<div class="card-body collapse" id="bodyCellular">								
							<div class="form-horizontal" id="cellularWrapper">
							
							<fieldset id="section-cellular" disabled>
								<h6 class="card-subtitle mb-2">Name shown on bill</h6>
								<div class="form-row">
									<spring:bind path="utility[3].firstName">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[3].firstName">First Name</label>
										<form:input path="utility[3].firstName" name="utility[3].firstName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
										<form:errors path="utility[3].firstName" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[3].lastName">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[3].lastName">Last Name</label>
										<form:input path="utility[3].lastName" name="utility[3].lastName" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
										<form:errors path="utility[3].lastName" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[3].middleInitial">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label for="utility[3].middleInitial">Middle Initial</label>
										<form:input path="utility[3].middleInitial" name="utility[3].middleInitial" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
										<form:errors path="utility[3].middleInitial" class="small" />
									</div>
									</spring:bind>
								</div>
								
								<div class="form-row">
									<spring:bind path="utility[3].supplier.supplierCode">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[3].supplier.supplierCode">Service Company Name</label>
										<form:select path="utility[3].supplier.supplierCode" name="utility[3].supplier.supplierCode"
											class="form-control ${status.error ? 'is-invalid' : ''}">
											<form:option value="" label="--- Select ---" />
											<form:options items="${phoneSuppliers}" itemValue="supplierCode" itemLabel="supplierName" />
										</form:select>
										<form:errors path="utility[3].supplier.supplierCode" class="small" />
									</div>
									</spring:bind>
									<spring:bind path="utility[3].phoneNum">
									<div class="form-group col ${status.error ? 'text-danger' : ''}">
										<label class="required" for="utility[3].phoneNum">Cell Phone Number</label>
										<form:input path="utility[3].phoneNum" name="utility[3].phoneNum" type="text"
											class="form-control ${status.error ? 'is-invalid' : ''}" maxlength="10" placeholder="i.e. 9991234567" />
										<form:errors path="utility[3].phoneNum" class="small" />
									</div>
									</spring:bind>
								</div>
								
							</fieldset>
							
							</div> <!-- End of cellularWrapper -->
						</div> <!-- End of card-body -->
					</div> <!-- End of card -->

				</div> <!-- End of card-body for whole form -->
				<div class="card-footer">
					<input class="btn btn-primary" type="submit" value="Submit Application"/>
				</div>											
			</div> <!-- End of main card -->
				
			</form:form>								
		</div> <!-- End of main container -->
	</div> <!-- End of content -->   

<script>
/*
$('#statusDocument').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileDWP').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileSCG').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileLandline').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileCellular').on('change', ChangeFileBrowserText).change();
*/

$('#btn-add-remove-dwp').on('click', function(){
		toggleFieldset('section-dwp');
});
$('#btn-add-remove-scg').on('click', function(){
	toggleFieldset('section-scg');
});
$('#btn-add-remove-landline').on('click', function(){
	toggleFieldset('section-landline');
});
$('#btn-add-remove-cellular').on('click', function(){
	toggleFieldset('section-cellular');
});
$('#sameAsServiceChecked').on('change', function(){
	toggleFieldset('section-mailing');
});


// Removes "fakepath" from filename on document selection inputs
function ChangeFileBrowserText() {
	var fileName = $(this).val();
	if(fileName === "") {
		$(this).next('.custom-file-label').html("Choose file...");
	}
	else {
		var cleanFileName = fileName.replace('C:\\fakepath\\', "");
		$(this).next('.custom-file-label').html(cleanFileName);
	}	
}

// disable fieldset if containing card-body is hidden
function toggleFieldset(fieldset_id) {
	var fieldset = document.getElementById(fieldset_id);
	var cardbody = fieldset.closest(".card-body");
	if(cardbody.classList.contains("show")) {
		fieldset.setAttribute("disabled", true);
	} else {
		fieldset.removeAttribute("disabled");
	}
}
</script>

<script>

$(document).ready(function(){
    $('[data-toggle="popover"]').popover();
});

$(document).ready(function() {
	var utilityAdded0 = '${utilityAdded0}';
	var utilityAdded1 = '${utilityAdded1}';
	var utilityAdded2 = '${utilityAdded2}';
	var utilityAdded3 = '${utilityAdded3}';
	var mailingSameAsService = '${mailingSameAsService}';
	if(utilityAdded0) {
		console.log("DWP previously added: " + utilityAdded0);
		$("#btn-add-remove-dwp").trigger("click");
	}
	if(utilityAdded1) {
		console.log("SCG previously added: " + utilityAdded0);
		$("#btn-add-remove-scg").trigger("click");
	}
	if(utilityAdded2) {
		console.log("Landline previously added: " + utilityAdded0);
		$("#btn-add-remove-landline").trigger("click");
	}
	if(utilityAdded3) {
		console.log("Cellular previously added: " + utilityAdded0);
		$("#btn-add-remove-cellular").trigger("click");
	}
	if(typeof mailingSameAsService !== 'undefined') {
		if(mailingSameAsService === "false") {
			console.log("sameAsServiceChecked");
			$("#sameAsServiceChecked").trigger("click");	// only triggers this if form was previously submitted
		}
	}
});
</script>

<script>
// defunct
$(function(){
    $(":checkbox").change(function(){
        if($("#lifeline").is(':checked')){
            $("#water").attr("disabled", true);
            $("#water").removeAttr('checked');
            $("#electric").attr("disabled", true);
            $("#electric").removeAttr('checked');
        }
        if(!($("#lifeline").is(':checked'))){
            $("#water").attr("disabled", false);
            $("#electric").attr("disabled", false);
        }
        if($("#electric").is(':checked') || $("#water").is(':checked')){
            $("#lifeline").attr("disabled", true);
            $("#lifeline").removeAttr('checked');
        }
        if(!($("#water").is(':checked')) && !($("#electric").is(':checked'))){
            $("#lifeline").attr("disabled", false);
        }
    });
});
</script>

<script type="text/javascript">
function SetDate()
{
	var today = GetToday();		
	
	// if utility has not been populated (i.e. no date Approved) then set default date to today
	var dates = document.getElementsByClassName("approveDate");
	for (var i = 0; i < dates.length; i++) {
		if(!dates[i].value || dates[i].value.length === 0) {
			dates[i].defaultValue = today;
		}
	}
	
	var mailingDate = document.getElementsByName("mailingDate")[0].setAttribute('max', today);
}

function GetToday() {
	var date = new Date();
	
	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	
	if (month < 10) month = "0" + month;
	if (day < 10) day = "0" + day;
	
	var today = year + "-" + month + "-" + day;	
	
	return today;
}

</script>	

</div> <!-- End of page-container -->
<footer class="footer">
	<jsp:include page="footer.jsp" />
</footer>
</body>
</html>