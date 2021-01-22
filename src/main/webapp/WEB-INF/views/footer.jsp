<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>
<!--
<link rel="stylesheet" type="text/css" href="finance_styles-sub.css">
<link rel="stylesheet" type="text/css" href="lastarter_styles.css">
-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<script src="https://globalnav.lacity.org/global_nav_2.0.js"></script> 

<div class="container" style="background:#212121; color:#FFF; max-width:none">
	<div id="version-info">
		<small class="text-muted">Release Build ${versionNumber}</small>
	</div>
    <div class="row mb-2">
        <div class="col-sm-4">
            <h4 style="font-weight:bold; padding-top:10px; padding-bottom:10px">Connect With Us</h4>
            <p><a href="http://business.lacity.org/" target="_blank"><img alt="LA Business Portal" title="LA Business Portal" src="http://10.96.13.140:8036/laweb/images/LABusinessPortal_Banner.jpg" style="max-width:100%; height:auto"></a></p>
        </div>
    
        <div class="col-sm-4">
            <h4 style="font-weight:bold; padding-top:10px; padding-bottom:10px">Disclaimer</h4>
            <p>Non-financial information such as name, business address (including home-based businesses), mailing address, etc., contained in your City of Los Angeles tax and permit records, is subject to public disclosure under provisions of the California Public Records Act, Government Code Section 6250 et seq.&nbsp; Your residential information may also be subject to public disclosure if that location is utilized for business and/or mailing purposes.</p>
        </div>
        
        <div class="col-sm-4">
            <h4 style="font-weight:bold; padding-top:10px; padding-bottom:10px">Contact Us</h4>  
            <table class="table table-no-border" style="color:#FFF">
            <tr><td valign="top"><i class="fa fa-map-marker"></i></td><td valign="top">200 N. Spring Street, Los Angeles, CA 90012</td></tr>
            <tr><td valign="top"><i class="fa fa-phone"></i></td><td valign="top">Call 311</td></tr>
            <tr><td valign="top"><i class="fa fa-envelope"></i></td><td valign="top"><a href="http://finance.lacity.org/submit-feedback" target="_blank" style="color:#FFF; text-decoration:underline;">Submit Feedback</a></td></tr>
            </table>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-12">
            <p align="center">&#169; Copyright 2016 City of Los Angeles. All rights reserved.&nbsp;&nbsp;
            <a href="http://disclaimer.lacity.org/disclaimer.htm" target="_blank" style="color:#FFF; text-decoration:underline;">Disclaimer</a> &nbsp;| &nbsp;
            <a href="http://disclaimer.lacity.org/privacy.htm" target="_blank" style="color:#FFF; text-decoration:underline;">Privacy Policy</a>
            </p>
        </div>
    </div>
</div>
</html>