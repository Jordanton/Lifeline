package com.itafin.lifeline.utils;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itafin.lifeline.model.UserAccount;

public class LifelineUtils {

	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

	// Store Connection in request attribute.
	// (Information stored only exist during requests)
	public static void storeConnection(ServletRequest request, Connection conn) {
		request.setAttribute(ATT_NAME_CONNECTION, conn);
	}

	// Get the Connection object has been stored in one attribute of the request.
	public static Connection getStoredConnection(ServletRequest request) {
		Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
		return conn;
	}

	// Store user info in Session.
	public static void storeUserSession(HttpSession session, UserAccount user) {
		// On the JSP can access ${currentUser}
		session.setAttribute("currentUser", user);
	}

	// Get the user information stored in the session.
	public static UserAccount getUserSession(HttpSession session) {
		UserAccount user = (UserAccount)session.getAttribute("currentUser");
		return user;
	}

	// Store info in Cookie
	public static void storeUserCookie(HttpServletResponse response,
		UserAccount user) {
		System.out.println("Store user cookie");
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUsername());

		// 1 day (Convert to seconds)
		cookieUserName.setMaxAge(24 * 60 * 60);
		response.addCookie(cookieUserName);
	}

	public static String getUserNameInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// Delete cookie.
	public static void deleteUserCookie(HttpServletResponse response) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);

		// 0 seconds (Expires immediately)
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}
	
	public static boolean stringContains(String s, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		if(m.find()) {
			return true;
		}
		return false;
		
	}
	
	//listNullsOnly() checks if a list is all null or contains a combination of only nulls and empty strings
	public static <T> boolean listNullsOnly(List<T> list) {
		if(list == null
				|| list.stream().noneMatch(Objects::nonNull)
				|| list.stream().allMatch(s -> (s == null || s.toString().isEmpty()) ))
			return true;
		else
			return false;
	}

}