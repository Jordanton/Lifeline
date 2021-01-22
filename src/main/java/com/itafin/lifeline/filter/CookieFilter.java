//package com.itafin.lifeline.filter;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
// 
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.itafin.lifeline.dao.LifelineDaoImpl;
//import com.itafin.lifeline.model.UserAccount;
//import com.itafin.lifeline.utils.LifelineProperties;
//import com.itafin.lifeline.utils.LifelineUtils;
// 
////@WebFilter(filterName = "cookieFilter", urlPatterns = { "/*" })
//public class CookieFilter {
//	
//	@Autowired LifelineDaoImpl lifelineDaoImpl;
//	@Autowired LifelineProperties lifelineProps;
// 
//   public CookieFilter() {
//   }
// 
//   public void init(FilterConfig fConfig) throws ServletException {
// 
//   }
// 
//   public void destroy() {
// 
//   }
// 
//   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//           throws IOException, ServletException {
//       HttpServletRequest req = (HttpServletRequest) request;
//       HttpSession session = req.getSession();
// 
//       UserAccount userInSession = LifelineUtils.getUserSession(session);
//    
//       if (userInSession != null) {
//           session.setAttribute("COOKIE_CHECKED", "CHECKED");
//           chain.doFilter(request, response);
//           return;
//       }
//    
//       // Connection was created in JDBCFilter.
//       Connection conn = LifelineUtils.getStoredConnection(request);
// 
//  
//       // Flag check cookie
//       String checked = (String) session.getAttribute("COOKIE_CHECKED");
//       if (checked == null && conn != null) {
//           String userName = LifelineUtils.getUserNameInCookie(req);
////           try {
////               UserAccount user = lifelineDaoImpl.findUser(userName);
////               LifelineUtils.storeUserSession(session, user);
////           } catch (SQLException e) {
////               e.printStackTrace();
////           }
//    
//           // Mark checked.
//           session.setAttribute("COOKIE_CHECKED", "CHECKED");
//       }
//       
//       chain.doFilter(request, response);
//   }
// 
//}