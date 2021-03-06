//package com.itafin.lifeline.utils;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import javax.naming.NamingException;
// 
//public class ConnectionUtils {
// 
//   public static Connection getConnection()
//             throws ClassNotFoundException, SQLException, NamingException {
//      
//       // Oracle Connection to DB.
//       return OracleConnUtils.getOracleConnection();
//   }
//    
//   public static void closeQuietly(Connection conn) {
//       try {
//           conn.close();
//       } catch (Exception e) {
//       }
//   }
// 
//   public static void rollbackQuietly(Connection conn) {
//       try {
//           conn.rollback();
//       } catch (Exception e) {
//       }
//   }
//}