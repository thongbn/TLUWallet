package com.server.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class MySQLConnUtils {
 

  public static Connection getMySQLConnection() throws SQLException,
          ClassNotFoundException {
      String hostName = "localhost";
 
      String dbName = "quanlychitieu";
      String userName = "root";
      String password = "1234";
 
      return getMySQLConnection(hostName, dbName, userName, password);
  }
 
  public static Connection getMySQLConnection(String hostName, String dbName,
          String userName, String password) throws SQLException,
          ClassNotFoundException {

      Class.forName("com.mysql.jdbc.Driver");
 

      String connectionURL = "jdbc:mysql://localhost:3306/quanlychitieu";
 
      Connection conn = DriverManager.getConnection(connectionURL, userName,
              password);
      return conn;
  }
}
