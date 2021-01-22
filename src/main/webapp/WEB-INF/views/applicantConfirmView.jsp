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
<script 
	src="https://www.google.com/recaptcha/api.js"></script>
	
<script 
	src="<c:url value="/resources/js/confirmation.js"/>"></script>
	
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
				<li class="breadcrumb-item active">Confirm Application</li>
			</ol>
		</nav>

		<!-- Main container -->
		<div class="container">						

			<div class="card">
				<div class="card-body">
					<div class="heading-container center mb-3">
						<h3 class="card-title">Please Review Your Information And Upload Documentation</h3>
						<h4 class="card-subtitle mb-2 text-muted">Does everything look correct?</h4>
					</div>
					
					<form:form class="form-horizontal" id="applicantForm" method="POST" action="confirm" modelAttribute="applicant"
						enctype="multipart/form-data">
							<fieldset id="section-applicant">
								<div class="card-body">
									<div class="form-row">
										
										<spring:bind path="firstName">
										<div class="form-group col ${status.error ? 'text-danger' : ''}">
											<label for="firstName">First Name</label><br/>
											<span class="filled-out">${applicant.firstName}</span>
											<form:input path="firstName" id="firstName" type="hidden" 
												class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
											<form:errors path="firstName" class="small" />
										</div>
										</spring:bind>
										
										<spring:bind path="lastName">
										<div class="form-group col ${status.error ? 'text-danger' : ''}">
											<label for="lastName">Last Name</label><br/>
											<span class="filled-out">${applicant.lastName}</span>
											<form:input path="lastName" id="lastName" type="hidden"
												class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
											<form:errors path="lastName" class="small" />
										</div>
										</spring:bind>
										
										<spring:bind path="middleName">
										<div class="form-group col ${status.error ? 'text-danger' : ''}">
											<label for="middleName">Middle Initial</label><br/>
											<span class="filled-out">${applicant.middleName}</span>
											<form:input path="middleName" id="middleName" type="hidden"
												class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
											<form:errors path="middleName" class="small" />
										</div>
										</spring:bind>
									</div>
									
									<div class="form-row">
										<spring:bind path="emailAddress">
										<div class="form-group col ${status.error ? 'text-danger' : ''}">
											<label for="emailAddress">Email Address</label><br/>
											<span class="filled-out">${applicant.emailAddress}</span>
											<form:input path="emailAddress" id="emailAddress" type="hidden"
												class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="name@domain.com" />
											<form:errors path="emailAddress" class="small" />
										</div>
										</spring:bind>
									</div>
				
									<div class="form-row">					
										<spring:bind path="classCode">
										<div class="form-group col ${status.error ? 'text-danger' : ''}">
											<label for="classCode">Class</label><br/>
											<span class="filled-out">
												<c:if test="${applicant.classCode == 'S' }">Senior</c:if>
												<c:if test="${applicant.classCode == 'D' }">Disabled</c:if>
											</span>
											<form:input path="classCode" id="classCode" type="hidden" class="form-control ${status.error ? 'is-invalid' : ''}" />
											<form:errors path="classCode" class="small" />
										</div>
										</spring:bind>
										
										<spring:bind path="dateOfBirth">
										<div class="form-group col ${status.error ? 'text-danger' : ''}">
											<label for="dateOfBirth">Date of Birth</label><br/>
											<span class="filled-out">${applicant.dateOfBirth}</span>
											<form:input path="dateOfBirth" id="dateOfBirth" type="hidden" placeholder="YYYY-MM-DD"
												class="form-control ${status.error ? 'is-invalid' : ''}" required="required"/>
											<form:errors path="dateOfBirth" class="small" />
										</div>
										</spring:bind>
					
										<spring:bind path="dayPhone">
										<div class="form-group col ${status.error ? 'text-danger' : ''}">
											<label for="dayPhone">Day Phone</label><br/>
											<span class="filled-out">${applicant.dayPhone}</span>
											<form:input path="dayPhone" id="dayPhone" type="hidden"
												class="form-control ${status.error ? 'is-invalid' : ''}" maxlength="10" placeholder="1234567890" />
											<form:errors path="dayPhone" class="small" />
										</div>
										</spring:bind>
									</div>
									
									<div class="form-row">
										<spring:bind path="statusDocument">	
										<div class="form-group col bg-warning ${status.error ? 'text-danger' : ''}">
											<label class="required">
												Status Documentation <span class="small"><strong>For senior citizens:</strong> Proof of age. <strong>For disabled citizens:</strong> Proof of disability.
												(File types accepted: .png, .jpg, or .pdf. Maximum upload size 1MB.)</span>
											</label>
											<a tabindex="-1" href="javascript://" data-toggle="popover"  data-trigger="focus" data-html="true"
												data-content="<b>Accepted documents for proof of age:</b><br/>
													- CA State driver's license<br/>
													- CA State identification card<br/><br/>
													<b>Accepted documents for proof of disability:</b><br/>
													- recent certification (within the last 2 years) signed by a licensed physician attesting
													that you are physically and/or mentally disabled which can be expected to result in death 
													or to be of long/continued and indefinite duration.">
												<i class="fa fa-question-circle"></i>
											</a>
											<div class="input-group mb-1">
										  		<div class="input-group-prepend">
													<span class="input-group-text" id="statusDocumentAddOn">Upload document</span>
												</div>
											 	<div class="custom-file">
													<form:input type="file" path="statusDocument"
														class="custom-file-input ${status.error ? 'is-invalid' : ''}" id="statusDocument"
														accept="image/png, image/jpeg, application/pdf"
														name="document" aria-describedby="statusDocumentAddOn" />
													<label class="custom-file-label" for="statusDocument">Choose file</label>
												</div>
											</div>
											<form:errors path="statusDocument" class="small" />
										</div>
										</spring:bind>
									</div>
									
									<div class="form-row">
										<spring:bind path="incomeDocument">	
										<div class="form-group col bg-warning ${status.error ? 'text-danger' : ''}">
											<label class="required">
												Income Documentation <span class="small"><strong>Proof of income for applicant and each household member.</strong>
												(File types accepted: .png, .jpg, or .pdf. Maximum upload size 1MB.)</span>
												<a class="small" href="faq#income" target="_blank">FAQ on accepted income documents.</a>
												<a tabindex="-1" href="javascript://" data-toggle="popover"  data-trigger="focus" data-html="true"
													data-content="<b>Accepted documents for proof of income:</b><br/>
														- Californa Resident Income Tax Return Form 540<br/>
														- Social Security Benefits Statement<br/>
														- Award letter of the amount of SSI benefits received<br/>
														- Award letter from General Relief<br/>
														- Cal Works/AFCD (entire copy)<br/>
														- <b>Notarized Letter</b> stating income<br/>
														<b>Note: We will not accept
														copies of checks from any County, W-2, Statement of Earnings and Deductions (paystub), 
														or the Federal Income Tax Return Form 1040</b></br/><br/>
														<b>Accepted documents for proof of disability:</b><br/>
														- recent certification (within the last 2 years) signed by a licensed physician attesting
														that you are physically and/or mentally disabled which can be expected to result in death 
														or to be of long/continued and indefinite duration.">
													<i class="fa fa-question-circle"></i>
												</a>
											</label>
											<div class="input-group mb-1">
										  		<div class="input-group-prepend">
													<span class="input-group-text" id="incomeDocumentAddOn">Upload document</span>
												</div>
											 	<div class="custom-file">
													<form:input type="file" path="incomeDocument"
														class="custom-file-input ${status.error ? 'is-invalid' : ''}" id="incomeDocument"
														accept="image/png, image/jpeg, application/pdf"
														name="document" aria-describedby="incomeDocumentAddOn" />
													<label class="custom-file-label" for="incomeDocument">Choose file</label>
												</div>
											</div>
											<form:errors path="incomeDocument" class="small" />
										</div>
										</spring:bind>
									</div>
								</div> <!-- End of card-body for applicant information -->
							</fieldset> <!-- End of applicant information -->
			
							<!-- Service address -->
							<div class="form-horizontal">
								<div class="collapse-toggle-txt" id="headingService">
									Service Address
								</div>
								<div class="card-body" id="bodyService-filled">
									<div class="filled-out">
										<span>${applicant.serviceAddress.streetNum} </span>
										<span>${applicant.serviceAddress.fracNum} </span>
										<span>${applicant.serviceAddress.streetDir} </span>
										<span>${applicant.serviceAddress.streetName} </span>
										<span>${applicant.serviceAddress.streetType}</span>
									</div>
									<div class="filled-out">
										<span>${applicant.serviceAddress.unitNum}</span>
									</div>
									<div class="filled-out">
										<span>${applicant.serviceAddress.city}, </span>
										<span>${applicant.serviceAddress.state} </span>
										<span>${applicant.serviceAddress.zipCode}</span>
									</div>
									<div class="filled-out">
										<span>
										<c:if test="${applicant.serviceAddress.residenceType eq 'S'}">Single Residence</c:if>
										<c:if test="${applicant.serviceAddress.residenceType eq 'M'}">Mobile Park: </c:if>
										</span>
										<span>${applicant.serviceAddress.mobileParkName}</span>	
									</div>
								</div>
								
								<div class="card-body collapse" id="bodyService">
									<fieldset id="section-service">
										<h6 class="card-subtitle mb-2">Address Line 1</h6>

										<div class="form-row">
											<spring:bind path="serviceAddress.streetNum">
											<div class="form-group col-sm-2 ${status.error ? 'text-danger' : ''}">
											<label for="serviceAddress.streetNum" class="text-muted small">Street Number</label><br/>
											<span class="filled-out">${applicant.serviceAddress.streetNum}</span>
												<form:input path="serviceAddress.streetNum" id="serviceAddress.streetNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 12345" maxlength="5" />
												<form:errors path="serviceAddress.streetNum" class="small" />
											</div>
											</spring:bind>
											
											<spring:bind path="serviceAddress.fracNum">
											<div class="form-group col-sm-1 ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.fracNum" class="text-muted small">Fraction</label><br/>
												<span class="filled-out">${applicant.serviceAddress.fracNum}</span>
												<form:input path="serviceAddress.fracNum" id="serviceAddress.fracNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 1/2" maxlength="4" />
												<form:errors path="serviceAddress.fracNum" class="small" />
											</div>
											
											</spring:bind>
											<spring:bind path="serviceAddress.streetDir">
											<div class="form-group col-sm-1 ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.streetDir" class="text-muted small">Direction</label><br/>
												<span class="filled-out">${applicant.serviceAddress.streetDir}</span>
												<form:input path="serviceAddress.streetDir" id="serviceAddress.streetDir" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. NE" maxlength="2" />
												<form:errors path="serviceAddress.streetDir" class="small" />
											</div>
											</spring:bind>
										</div> <!-- End of form-row -->
										
										<div class="form-row">
											<spring:bind path="serviceAddress.streetName">
											<div class="form-group col-sm-8 ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.streetName" class="text-muted small">Street Name</label><br/>
												<span class="filled-out">${applicant.serviceAddress.streetName}</span>
												<form:input path="serviceAddress.streetName" id="serviceAddress.streetName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. Broadway" maxlength="28" />
												<form:errors path="serviceAddress.streetName" class="small" />
											</div>
											</spring:bind>
											
											<spring:bind path="serviceAddress.streetType">
											<div class="form-group col-sm-4 ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.streetType" class="text-muted small">Street Type</label><br/>
												<span class="filled-out">${applicant.serviceAddress.streetType}</span>
												<form:input path="serviceAddress.streetType" id="serviceAddress.streetType" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. St" maxlength="2" />
												<form:errors path="serviceAddress.streetType" class="small" />
											</div>
											</spring:bind>
										</div> <!-- End of form-row -->
										
										<h6 class="card-subtitle mb-2">Address Line 2</h6>
										
										<div class="form-row collapse">
											<spring:bind path="serviceAddress.unitNum">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.unitNum" class="text-muted small">Unit Number</label><br/>
												<span class="filled-out">${applicant.serviceAddress.unitNum}</span>
												<form:input path="serviceAddress.unitNum" id="serviceAddress.unitNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 405" maxlength="7" />
												<form:errors path="serviceAddress.unitNum" class="small" />
											</div>
											</spring:bind>
										</div> <!-- End of form-row -->	
										
										<div class="form-row">
											<spring:bind path="serviceAddress.city">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.city">City</label><br/>
												<span class="filled-out">${applicant.serviceAddress.city}</span>
												<form:input path="serviceAddress.city" id="serviceAddress.city" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. Los Angeles" maxlength="25" />
												<form:errors path="serviceAddress.city" class="small" />
											</div>
											</spring:bind>
											
											<spring:bind path="serviceAddress.state">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.state">State</label><br/>
												<span class="filled-out">${applicant.serviceAddress.state}</span>
												<form:input path="serviceAddress.state" id="serviceAddress.state" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" value="CA" placeholder="CA" disabled="disabled"/>
												<form:errors path="serviceAddress.state" class="small" />
											</div>
											</spring:bind>
					
											<spring:bind path="serviceAddress.zipCode">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.zipCode">ZIP</label><br/>
												<span class="filled-out">${applicant.serviceAddress.zipCode}</span>
												<form:input path="serviceAddress.zipCode" id="serviceAddress.zipCode" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 90012" maxlength="9" />
												<form:errors path="serviceAddress.zipCode" class="small" />
											</div>
											</spring:bind>
										</div> <!-- End of form-row -->
					
										<div class="form-row">
										
											<spring:bind path="serviceAddress.residenceType">
											<div class="form-group col-sm-4 ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.residenceType">Address Type</label><br/>
												<span class="filled-out">${applicant.serviceAddress.residenceType}
													<c:if test="${applicant.serviceAddress.residenceType == 'S' }">Single</c:if>
													<c:if test="${applicant.serviceAddress.residenceType == 'M' }">Mobile</c:if>
												</span>
												<form:input path="serviceAddress.residenceType" type="hidden" />
												<form:errors path="serviceAddress.residenceType" class="col-form-label" />
											</div>
											</spring:bind>
											
											<spring:bind path="serviceAddress.mobileParkName">
											<div class="form-group col-sm-8 ${status.error ? 'text-danger' : ''}">
												<label for="serviceAddress.mobileParkName">Mobile Park</label><br/>
												<span class="filled-out">${applicant.serviceAddress.mobileParkName}</span>
												<form:input path="serviceAddress.mobileParkName" id="serviceAddress.mobileParkName" type="hidden"
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
									Mailing Address 
								</div>
								
								<div class="card-body" id="bodyMailing-filled">
									<div class="filled-out">
										<span>${applicant.mailingAddress.streetNum} </span>
										<span>${applicant.mailingAddress.fracNum} </span>
										<span>${applicant.mailingAddress.streetDir} </span>
										<span>${applicant.mailingAddress.streetName} </span>
										<span>${applicant.mailingAddress.streetType}</span>
									</div>
									<div class="filled-out">
										<span>${applicant.mailingAddress.unitNum}</span>
									</div>
									<div class="filled-out">
										<span>${applicant.mailingAddress.city}, </span>
										<span>${applicant.mailingAddress.state} </span>
										<span>${applicant.mailingAddress.zipCode}</span>
									</div>
									<div class="filled-out">
										<span>
										<c:if test="${applicant.mailingAddress.residenceType eq 'S'}">Single Residence</c:if>
										<c:if test="${applicant.mailingAddress.residenceType eq 'M'}">Mobile Park: </c:if>
										</span>
										<span>${applicant.mailingAddress.mobileParkName}</span>	
									</div>
								</div>
								
								<div class="card-body collapse" id="bodyMailing">
									<fieldset id="section-mailing">
										<h6 class="card-subtitle mb-2">Address Line 1</h6>
										
										<div class="form-row">
											<spring:bind path="mailingAddress.streetNum">
											<div class="form-group col-sm-2 ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.streetNum" class="text-muted small">Street Number</label><br/>
												<span class="filled-out">${applicant.mailingAddress.streetNum}</span>
												<form:input path="mailingAddress.streetNum" id="mailingAddress.streetNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 12345" maxlength="5" />
												<form:errors path="mailingAddress.streetNum" class="small" />
											</div>
											</spring:bind>
											
											<spring:bind path="mailingAddress.fracNum">
											<div class="form-group col-sm-1 ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.fracNum" class="text-muted small">Fraction</label><br/>
												<span class="filled-out">${applicant.mailingAddress.fracNum}</span>
												<form:input path="mailingAddress.fracNum" id="mailingAddress.fracNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 1/2" maxlength="4" />
												<form:errors path="mailingAddress.fracNum" class="small" />
											</div>
											
											</spring:bind>
											<spring:bind path="mailingAddress.streetDir">
											<div class="form-group col-sm-1 ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.streetDir" class="text-muted small">Direction</label><br/>
												<span class="filled-out">${applicant.mailingAddress.streetDir}</span>
												<form:input path="mailingAddress.streetDir" id="mailingAddress.streetDir" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. NE" maxlength="2" />
												<form:errors path="mailingAddress.streetDir" class="small" />
											</div>
											</spring:bind>
										</div> <!-- End of form-row -->
										
										<div class="form-row">
											<spring:bind path="mailingAddress.streetName">
											<div class="form-group col-sm-8 ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.streetName" class="text-muted small">Street Name</label><br/>
												<span class="filled-out">${applicant.mailingAddress.streetName}</span>
												<form:input path="mailingAddress.streetName" id="mailingAddress.streetName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. Broadway" maxlength="28" />
												<form:errors path="mailingAddress.streetName" class="small" />
											</div>
											</spring:bind>
											
											<spring:bind path="mailingAddress.streetType">
											<div class="form-group col-sm-4 ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.streetType" class="text-muted small">Street Type</label><br/>
												<span class="filled-out">${applicant.mailingAddress.streetType}</span>
												<form:input path="mailingAddress.streetType" id="mailingAddress.streetType" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. St" maxlength="2" />
												<form:errors path="mailingAddress.streetType" class="small" />
											</div>
											</spring:bind>
										</div> <!-- End of form-row -->
										
										<h6 class="card-subtitle mb-2">Address Line 2</h6>
										<span class="filled-out">${applicant.mailingAddress.unitNum}</span>
										
										<div class="form-row">
											<spring:bind path="mailingAddress.unitNum">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.unitNum" class="text-muted small">Unit Number</label><br/>
												<span class="filled-out">${applicant.mailingAddress.unitNum}</span>
												<form:input path="mailingAddress.unitNum" id="mailingAddress.unitNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 405" maxlength="7" />
												<form:errors path="mailingAddress.unitNum" class="small" />
											</div>
											</spring:bind>
										</div> <!-- End of form-row -->	
										
										<div class="form-row">
											<spring:bind path="mailingAddress.city">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.city">City</label><br/>
												<span class="filled-out">${applicant.mailingAddress.city}</span>
												<form:input path="mailingAddress.city" id="mailingAddress.city" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. Los Angeles" maxlength="25" />
												<form:errors path="mailingAddress.city" class="small" />
											</div>
											</spring:bind>
											
											<spring:bind path="mailingAddress.state">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.state">State</label><br/>
												<span class="filled-out">${applicant.mailingAddress.state}</span>
												<form:input path="mailingAddress.state" id="mailingAddress.state" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" value="CA" placeholder="CA" disabled="disabled"/>
												<form:errors path="mailingAddress.state" class="small" />
											</div>
											</spring:bind>
					
											<spring:bind path="mailingAddress.zipCode">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.zipCode">ZIP</label><br/>
												<span class="filled-out">${applicant.mailingAddress.zipCode}</span>
												<form:input path="mailingAddress.zipCode" id="mailingAddress.zipCode" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. 90012" maxlength="9" />
												<form:errors path="mailingAddress.zipCode" class="small" />
											</div>
											</spring:bind>
										</div> <!-- End of form-row -->
					
										<div class="form-row">
										
											<spring:bind path="mailingAddress.residenceType">
											<div class="form-group col-sm-4 ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.residenceType">Address Type</label><br/>
												<span class="filled-out">${applicant.mailingAddress.residenceType}
													<c:if test="${applicant.mailingAddress.residenceType == 'S' }">Single</c:if>
													<c:if test="${applicant.mailingAddress.residenceType == 'M' }">Mobile</c:if>
												</span>
												<form:input path="mailingAddress.residenceType" type="hidden" />
												<form:errors path="mailingAddress.residenceType" class="col-form-label" />
											</div>
											</spring:bind>
											
											<spring:bind path="mailingAddress.mobileParkName">
											<div class="form-group col-sm-8 ${status.error ? 'text-danger' : ''}">
												<label for="mailingAddress.mobileParkName">Mobile Park</label><br/>
												<span class="filled-out">${applicant.mailingAddress.mobileParkName}</span>
												<form:input path="mailingAddress.mobileParkName" id="mailingAddress.mobileParkName" type="hidden"
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
								Utilities
							</h5>
							<p class="card-text">
								Please enclose a photocopy of your most recent utility bill for each utility for which you are requesting an exemption.
							<strong>The exemption cannot be granted if the name that appears on the utility bill is not the same as the applicant's name.</strong>
							</p>
							
							<!-- DWP -->
							<div class="card mb-2" id="cardDWP">
								<div class="card-header" id="headingDWP">
									<span class="collapse-toggle-txt">Department of Water and Power</span>
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
												<label for="utility[0].firstName">First Name</label>
												<span class="filled-out">${applicant.utility[0].firstName}</span>
												<form:input path="utility[0].firstName" name="utility[0].firstName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
												<form:errors path="utility[0].firstName" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[0].lastName">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[0].lastName">Last Name</label>
												<span class="filled-out">${applicant.utility[0].lastName}</span>
												<form:input path="utility[0].lastName" name="utility[0].lastName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
												<form:errors path="utility[0].lastName" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[0].middleInitial">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[0].middleInitial">Middle Initial</label>
												<span class="filled-out">${applicant.utility[0].middleInitial}</span>
												<form:input path="utility[0].middleInitial" name="utility[0].middleInitial" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
												<form:errors path="utility[0].middleInitial" class="small" />
											</div>
											</spring:bind>
										</div>
				
										<div class="form-row">
											<spring:bind path="utility[0].accountNum">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[0].accountNum">Account Number</label>
												<span class="filled-out">${applicant.utility[0].accountNum}</span>
												<form:input path="utility[0].accountNum" name="utility[0].accountNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="ACCOUNT NUM" maxlength="10"/>
												<form:errors path="utility[0].accountNum" class="small" />
											</div>
											</spring:bind>
										</div>
										
										<div class="form-row">
											<spring:bind path="utility[0].includedInRent">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<div>
													<label for="utility[0].includedInRent">Utility Included in Rent?</label>
													<span class="filled-out">${applicant.utility[0].includedInRent}</span>
													<form:input path="utility[0].includedInRent" name="utility[0].includedInRent" type="hidden" />
												</div>
												<form:errors path="utility[0].includedInRent" class="small" />
											</div>
												
											</spring:bind>
											<spring:bind path="utility[0].householdCount">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[0].householdCount">Number of People in Household</label>
												<span class="filled-out">${applicant.utility[0].householdCount}</span>
												<form:input path="utility[0].householdCount" name="utility[0].householdCount" type="hidden"
													value="${applicant.utility[0].householdCount}"
													class="form-control ${status.error ? 'is-invalid' : ''}" maxlength="38" style="width: 80px" min="1" required="required"/>
												<form:errors path="utility[0].householdCount" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[0].serviceRequested">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[0].serviceRequested">Lifeline Services Requested</label>
												<div class="filled-out">
													<span>
													<c:if test="${applicant.utility[0].serviceRequested eq 'E'}">Electric Only</c:if>
													<c:if test="${applicant.utility[0].serviceRequested eq 'W'}">Water Only</c:if>
													<c:if test="${applicant.utility[0].serviceRequested eq 'B'}">Both water and electric</c:if>
													<c:if test="${applicant.utility[0].serviceRequested eq 'L'}">Lifeline Only</c:if>
													</span>
												</div>
												<form:input path="utility[0].serviceRequested" name="utility[0].serviceRequested" type="hidden" />
												<form:errors path="utility[0].serviceRequested" class="small" />
											</div>
											</spring:bind>
										</div>
										
										<h6 class="required" class="card-subtitle mb-2">Document</h6>
										<div class="form-row">
											<spring:bind path="utility[0].document">
											<div class="form-group col bg-warning ${status.error ? 'text-danger' : ''}">
												<label>Image of a recent DWP bill (as a .png, .jpg, or .pdf. Maximum upload size 1MB.) 
													<a class="small" href="faq#dwp" target="_blank">FAQ on accepted DWP documents.</a>
												</label>
												<a tabindex="-1" href="javascript://" data-toggle="popover"  data-trigger="focus" data-html="true"
													data-content="<b>Please provide a copy of the entire DWP bill. Must contain the following:</b><br/>
														- applicant's name<br/>
														- current service address<br/>
														<b>Please do not only send the payment portion.</b><br/>">
													<i class="fa fa-question-circle"></i>
												</a>
												<div class="input-group mb-1">
											  		<div class="input-group-prepend">
														<span class="input-group-text" id="inputGroupFileDWPAddOn">Upload document</span>
													</div>
												 	<div class="custom-file">
														<form:input type="file" path="utility[0].document"
															class="custom-file-input ${status.error ? 'is-invalid' : ''}" id="inputGroupFileDWP"
															accept="image/png, image/jpeg, application/pdf"
															name="document" aria-describedby="inputGroupFileDWPAddOn" />
														<label class="custom-file-label" for="inputGroupFileDWP">Choose file</label>
													</div>
												</div>
												<form:errors path="utility[0].document" class="small" />
											</div>
											</spring:bind>
										</div>
										
										</fieldset> <!-- End of section-dwp -->
									</div>
								</div> <!-- End of card-body for DWP -->
							</div> <!-- End of card for DWP -->
			
							<!-- SCG -->
							<div class="card mb-2" id="cardSCG">
								<div class="card-header" id="headingSCG">
									<span class="collapse-toggle-txt">Southern California Gas</span>
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
												<label for="utility[1].firstName">First Name</label>
												<span class="filled-out">${applicant.utility[1].firstName}</span>
												<form:input path="utility[1].firstName" name="utility[1].firstName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
												<form:errors path="utility[1].firstName" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[1].lastName">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[1].lastName">Last Name</label>
												<span class="filled-out">${applicant.utility[1].lastName}</span>
												<form:input path="utility[1].lastName" name="utility[1].lastName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
												<form:errors path="utility[1].lastName" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[1].middleInitial">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[1].middleInitial">Middle Initial</label>
												<span class="filled-out">${applicant.utility[1].middleInitial}</span>
												<form:input path="utility[1].middleInitial" name="utility[1].middleInitial" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
												<form:errors path="utility[1].middleInitial" class="small" />
											</div>
											</spring:bind>
										</div>
				
										<div class="form-row">
											<spring:bind path="utility[1].accountNum">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[1].accountNum">Account Number</label>
												<span class="filled-out">${applicant.utility[1].accountNum}</span>
												<form:input path="utility[1].accountNum" name="utility[1].accountNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="ACCOUNT NUMBER" />
												<form:errors path="utility[1].accountNum" class="small" />
											</div>
											</spring:bind>
										</div>
										
										<div class="form-row">
											<spring:bind path="utility[1].includedInRent">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<div>
													<label for="utility[1].includedInRent">Utility Included in Rent?</label>
													<span class="filled-out">${applicant.utility[1].includedInRent}</span>
													<form:input path="utility[1].includedInRent" name="utility[1].includedInRent" type="hidden" />
												</div>
											</div>
											<form:errors path="utility[1].includedInRent" class="small" />
											</spring:bind>
										</div>
										
										<h6 class="required" class="card-subtitle mb-2">Document</h6>
										<div class="form-row">
											<spring:bind path="utility[1].document">
											<div class="form-group col bg-warning ${status.error ? 'text-danger' : ''}">
												<label>Image of a recent SCG bill (as a .png, .jpg, or .pdf. Maximum upload size 1MB.)
													<a class="small" href="faq#scg" target="_blank">FAQ on accepted SCG documents.</a>
												</label>
												<a tabindex="-1" href="javascript://" data-toggle="popover"  data-trigger="focus" data-html="true"
													data-content="<b>Please provide a copy of the entire Gas bill. Must contain the following:</b><br/>
														- applicant's name<br/>
														- current service address<br/>
														<b>Please do not only send the payment portion.</b><br/>">
													<i class="fa fa-question-circle"></i>
												</a>
												<div class="input-group mb-1">
											  		<div class="input-group-prepend">
														<span class="input-group-text" id="inputGroupFileSCGAddOn">Upload document</span>
													</div>
												 	<div class="custom-file">
														<form:input type="file" path="utility[1].document"
															accept="image/png, image/jpeg, application/pdf"
															class="custom-file-input ${status.error ? 'is-invalid' : ''}" id="inputGroupFileSCG" 
															name="document" aria-describedby="inputGroupFileSCGAddOn" />
														<label class="custom-file-label" for="inputGroupFileSCG">Choose file</label>
													</div>
												</div>
												<form:errors path="utility[1].document" class="small" />
											</div>
											</spring:bind>
										</div>	
									</fieldset> <!-- End of section-scg -->
									
									</div> <!-- End of scgWrapper -->
								</div> <!-- End of card-body -->
							</div> <!-- End of card -->
							
							<!-- Landline -->
							<div class="card mb-2" id="cardLandline">
								<div class="card-header" id="headingLandline">
									<span class="collapse-toggle-txt">Landline Telephone Service Provider</span>
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
												<label for="utility[2].firstName">First Name</label>
												<span class="filled-out">${applicant.utility[2].firstName}</span>
												<form:input path="utility[2].firstName" name="utility[2].firstName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
												<form:errors path="utility[2].firstName" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[2].lastName">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[2].lastName">Last Name</label>
												<span class="filled-out">${applicant.utility[2].lastName}</span>
												<form:input path="utility[2].lastName" name="utility[2].lastName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
												<form:errors path="utility[2].lastName" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[2].middleInitial">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[2].middleInitial">Middle Initial</label>
												<span class="filled-out">${applicant.utility[2].middleInitial}</span>
												<form:input path="utility[2].middleInitial" name="utility[2].middleInitial" type="hidden" 
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
												<form:errors path="utility[2].middleInitial" class="small" />
											</div>
											</spring:bind>
										</div>
										
										<div class="form-row">
											<spring:bind path="utility[2].supplier.supplierCode">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[2].supplier.supplierCode">Service Company Name</label>
												<span class="filled-out">${applicant.utility[2].supplier.supplierName}</span>
												<form:input path="utility[2].supplier.supplierCode" type="hidden" />
												<form:errors path="utility[2].supplier.supplierCode" class="small" />
											</div>
											</spring:bind>
											
											<spring:bind path="utility[2].phoneNum">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[2].phoneNum">Residence Telephone</label>
												<span class="filled-out">${applicant.utility[2].phoneNum}</span>
												<form:input path="utility[2].phoneNum" name="utility[2].phoneNum" type="hidden" 
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. (123)456-7890" />
												<form:errors path="utility[2].phoneNum" class="small" />
											</div>
											</spring:bind>
										</div>
										
										<h6 class="required" class="card-subtitle mb-2">Document</h6>
										<div class="form-row">
											<spring:bind path="utility[2].document">
											<div class="form-group col bg-warning ${status.error ? 'text-danger' : ''}">
												<label>Image of a recent landline telephone bill (as a .png, .jpg, or .pdf. Maximum upload size 1MB.)
													<a class="small" href="faq#landline" target="_blank">FAQ on accepted landline bill documents.</a>
												</label>
												<a tabindex="-1" href="javascript://" data-toggle="popover"  data-trigger="focus" data-html="true"
													data-content="<b>Please provide a copy of the entire landline bill. Must contain the following:</b><br/>
														- applicant's name<br/>
														- current service address<br/>
														- phone number as filled out<br/>
														<b>Please do not only send the payment portion.</b><br/>">
													<i class="fa fa-question-circle"></i>
												</a>
												<div class="input-group mb-1">
											  		<div class="input-group-prepend">
														<span class="input-group-text" id="inputGroupFileLandlineAddOn">Upload document</span>
													</div>
												 	<div class="custom-file">
														<form:input type="file" path="utility[2].document"
															accept="image/png, image/jpeg, application/pdf"
															class="custom-file-input ${status.error ? 'is-invalid' : ''}" id="inputGroupFileLandline" 
															name="document" aria-describedby="inputGroupFileLandlineAddOn"/>
														<label class="custom-file-label" for="inputGroupFileLandline">Choose file</label>
													</div>
												</div>
												<form:errors path="utility[2].document" class="small" />
											</div>
											</spring:bind>
										</div>
									</fieldset>	<!-- End of section-landline -->
										
									</div> <!-- End of landlineWrapper -->
								</div> <!-- End of card-body -->
							</div> <!-- End of well -->				
												
							<!-- Cellular -->			
							<div class="card mb-2" id="cardCellular">
								<div class="card-header" id="headingCellular">
									<span class="collapse-toggle-txt">Cellular Telephone Service Provider</span>
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
												<label for="utility[3].firstName">First Name</label>
												<span class="filled-out">${applicant.utility[3].firstName}</span>
												<form:input path="utility[3].firstName" name="utility[3].firstName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="FIRST NAME" />
												<form:errors path="utility[3].firstName" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[3].lastName">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[3].lastName">Last Name</label>
												<span class="filled-out">${applicant.utility[3].lastName}</span>
												<form:input path="utility[3].lastName" name="utility[3].lastName" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="LAST NAME" />
												<form:errors path="utility[3].lastName" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[3].middleInitial">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[3].middleInitial">Middle Initial</label>
												<span class="filled-out">${applicant.utility[3].middleInitial}</span>
												<form:input path="utility[3].middleInitial" name="utility[3].middleInitial" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="MI" maxlength="1" style="width: 50px" />
												<form:errors path="utility[3].middleInitial" class="small" />
											</div>
											</spring:bind>
										</div>
										
										<div class="form-row">
											<spring:bind path="utility[3].supplier.supplierCode">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[3].supplier.supplierCode">Service Company Name</label>
												<span class="filled-out">${applicant.utility[3].supplier.supplierName}</span>
												<form:input path="utility[3].supplier.supplierCode" type="hidden"/>
												<form:errors path="utility[3].supplier.supplierCode" class="small" />
											</div>
											</spring:bind>
											<spring:bind path="utility[3].phoneNum">
											<div class="form-group col ${status.error ? 'text-danger' : ''}">
												<label for="utility[3].phoneNum">Cell Phone Number</label>
												<span class="filled-out">${applicant.utility[3].phoneNum}</span>
												<form:input path="utility[3].phoneNum" name="utility[3].phoneNum" type="hidden"
													class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="i.e. (123)456-7890" />
												<form:errors path="utility[3].phoneNum" class="small" />
											</div>
											</spring:bind>
										</div>
										
										<h6 class="required"  class="card-subtitle mb-2">Document</h6>
										<div class="form-row">
											<spring:bind path="utility[3].document">
											<div class="form-group col bg-warning ${status.error ? 'text-danger' : ''}">
												<label>Image of a recent cell phone bill (as a .png, .jpg, or .pdf. Maximum upload size 1MB.)
													<a class="small" href="faq#cellular" target="_blank">FAQ on accepted cell phone bill documents.</a>
												</label>
												<a tabindex="-1" href="javascript://" data-toggle="popover"  data-trigger="focus" data-html="true"
													data-content="<b>Please provide a copy of the entire cell phone bill. Must contain the following:</b><br/>
														- applicant's name<br/>
														- phone number as filled out<br/>
														<b>Please do not only send the payment portion.</b><br/>">
													<i class="fa fa-question-circle"></i>
												</a>
												<div class="input-group mb-1">
											  		<div class="input-group-prepend">
														<span class="input-group-text" id="inputGroupFileCellularAddOn">Upload document</span>
													</div>
												 	<div class="custom-file">
														<form:input type="file" path="utility[3].document"
															accept="image/png, image/jpeg, application/pdf"
															class="custom-file-input ${status.error ? 'is-invalid' : ''}" id="inputGroupFileCellular" 
															name="document" aria-describedby="inputGroupFileCellularAddOn" />
														<label class="custom-file-label" for="inputGroupFileCellular">Choose file</label>
													</div>
												</div>
												<form:errors path="utility[3].document" class="small" />
											</div>
											</spring:bind>
										</div>
									</fieldset>
									
									</div> <!-- End of cellularWrapper -->
								</div> <!-- End of card-body -->
							</div> <!-- End of card -->	
							<div class="card mb-2">
								<div class="card-body form-horizontal">
									<div class="form-group col">
										<input type="checkbox" class="form-check-input" id="certify-statement-check" data-toggle="collapse" data-target="#confirmation">
										<label class="form-check-label" for="certify-statement-check">
										I certify, under penalty of perjury under the laws of the state of California, that the information I have provided in this 
										application is true and correct. By completing this form and submitting it to the Office of Finance in an electronic format, 
										such as email, I agree that the form has the same legal effect as a form submitted by U.S. Mail or in person. I agree that 
										the Office of Finance and the Los Angeles Department of Water and Power can share my information with other utilities or 
										agencies to enroll me in their assistance programs. I understand that my information will be shared only with agencies that 
										offer discount programs that have agreed to keep the information confidential. I also agree that the aforementioned form 
										legally represents a document sent by me or my legal representative. 
										</label>
									</div>
									<div id="confirmation" class="collapse">
										<div class="form-group row">
											<div class="g-recaptcha m-auto" data-sitekey="${siteKey}" data-callback="enableSubmit" data-expired-callback="disableSubmit"></div>
										</div>
										<input id="submit-button" class="btn btn-primary" type="submit" value="Confirm Application"/>
									</div>
								</div>
							</div>										
					</form:form>
			</div> <!-- End of main card-body -->							
		</div> <!-- End of main card container -->
	</div> <!-- End of content --> 
</div> 

<script>

$(document).ready(function() {
	disableSubmit();
	$('[data-toggle="popover"]').popover();
	
	var utilityAdded0 = '${utilityAdded0}';
	var utilityAdded1 = '${utilityAdded1}';
	var utilityAdded2 = '${utilityAdded2}';
	var utilityAdded3 = '${utilityAdded3}';
	var mailingSameAsService = '${mailingSameAsService}';
	if(utilityAdded0) {
		$("#btn-add-remove-dwp").trigger("click");
		$("#btn-add-remove-dwp").remove();
	} else {
		$("#cardDWP").remove();
	}
	if(utilityAdded1) {
		$("#btn-add-remove-scg").trigger("click");
		$("#btn-add-remove-scg").remove();
	} else {
		$("#cardSCG").remove();
	}
	if(utilityAdded2) {
		$("#btn-add-remove-landline").trigger("click");
		$("#btn-add-remove-landline").remove();
	} else {
		$("#cardLandline").remove();
	}
	if(utilityAdded3) {
		$("#btn-add-remove-cellular").trigger("click");
		$("#btn-add-remove-cellular").remove();
	} else {
		$("#cardCellular").remove();
	}
});

</script>

<script>

$('#statusDocument').on('change', ChangeFileBrowserText).change();
$('#incomeDocument').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileDWP').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileSCG').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileLandline').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileCellular').on('change', ChangeFileBrowserText).change();

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