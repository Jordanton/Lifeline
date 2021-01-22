<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!--
<link rel="stylesheet" type="text/css" href="finance_styles-sub.css">
<link rel="stylesheet" type="text/css" href="lastarter_styles.css">
-->
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/styles.css">
</head>
<body>

<div>
    <div id="background-opacity"></div>
    
    <div><img class="banner-1" src="https://www.lacity.org/sites/g/files/wph1221/f/styles/short_hero/public/hero_short.jpg?itok=Bg5jZSIR"></div>
    
    <div><a href="http://finance.lacity.org/" target="_blank" title="Home"><img class="logo-1" src="http://10.96.13.140:8036/laweb/images/finance_logo.png"></a></div>
</div>

<div id="google_translate_element"></div>

</body>
<script src="//navbar.lacity.org/global_nav.js"></script> 
<script type="text/javascript">
	function googleTranslateElementInit() {
	  new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
	}
</script>	
<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script> 	

</html>