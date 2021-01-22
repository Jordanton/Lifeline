package com.itafin.lifeline.utils;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
/*
 * Copyright 2018 LATAX, Inc. All rights reserved.
 *
 */
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.itafin.lifeline.dao.LifelineDaoImpl;

/**
 * Login Interceptor
 */
public class AppAuthorizationInterceptor extends HandlerInterceptorAdapter {

	Logger AppLog = Logger.getLogger(AppAuthorizationInterceptor.class);
	
	@Autowired LifelineProperties lifelineProps;
	@Autowired LifelineDaoImpl lifelineDaoImpl;

	public static final String PNG_EXT = ".png";
	public static final String JPG_EXT = ".jpg";
	public static final String CSS_EXT = ".css";
	public static final String JS_EXT = ".js";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String strServletPath = request.getRequestURI().substring(request.getContextPath().length());
		String strRequestedPath = strServletPath.substring(1);	// remove /
		// Allow css, image, and js to pass through
		if (strServletPath.endsWith(PNG_EXT) || strServletPath.endsWith(JPG_EXT) || strServletPath.endsWith(CSS_EXT)
				|| strServletPath.endsWith(JS_EXT)) {
			return true;
		}
		
		// set version number
		request.setAttribute("versionNumber", lifelineProps.getVersionNumber());
		
		return true;
				
	}


}
