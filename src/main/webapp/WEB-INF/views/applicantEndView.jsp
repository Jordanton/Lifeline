<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
  <title>Lifeline - Application Submitted</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
<link rel="stylesheet" type="text/css" href="./application/css/styles.css">
  
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
				<li class="breadcrumb-item active">Application Submitted</li>
			</ol>
		</nav>

		<!-- Main container -->
		<div class="container">						
			<div class="card">
				<div class="card-body">
					<div class="heading-container center">
						<h3 class="card-title">Application has been submitted.</h3>
						<!-- <h4 class="card-subtitle mb-2 text-muted">A confirmation email has been sent to ${emailAddress}</h4>-->
						<h4 class="text-info">
							Application number: ${applicationNumber}
						</h4>
					</div>
					
				</div> <!-- End of card-body -->									
			</div> <!-- End of main card -->							
		</div> <!-- End of main container -->
	</div> <!-- End of content -->   

<script>
$(document).ready(function() {

});
</script>

<script>
$('#inputGroupFileDWP').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileSCG').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileLandline').on('change', ChangeFileBrowserText).change();
$('#inputGroupFileCellular').on('change', ChangeFileBrowserText).change();

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

</script>

<script>
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