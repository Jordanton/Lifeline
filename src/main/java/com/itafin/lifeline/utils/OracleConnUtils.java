/* 
 * This class is for connecting to the database
 * Connects to the JBOSS DataSource using JNDI name
 * Credentials are configured server side
 */

//package com.itafin.lifeline.utils;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.servlet.http.HttpServlet;
//import javax.sql.DataSource;
//
//public class OracleConnUtils extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private static DataSource mDataSource = null;
//	
//  public static Connection getOracleConnection() throws ClassNotFoundException, SQLException, NamingException {   			  	
//  	Context initContext;
//    Connection conn = null;
//    try {
//    	initContext = new InitialContext();
//    	mDataSource = (DataSource)initContext.lookup("java:/SCERS");
//    	conn = mDataSource.getConnection();	      	
//    } catch (NamingException e) {
//    	e.printStackTrace();
//    }
//    
//    return conn;      
//  }
//}