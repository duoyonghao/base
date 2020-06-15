package com.kqds.controller.base.thirdImport.smile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlserverDBManager {
  private static String user = "sa";
  
  private static String password = "1qaz2wsx,,,";
  
  private static String url = "jdbc:jtds:sqlserver://192.168.0.12:1433/kqds_smile";
  
  private Connection con = null;
  
  private static SqlserverDBManager dbm = null;
  
  private SqlserverDBManager(String user, String password) {
    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.err.println("class not found:" + e.getMessage());
    } 
    try {
      this.con = DriverManager.getConnection(url, user, password);
    } catch (SQLException a) {
      System.err.println("sql exception:" + a.getMessage());
    } 
  }
  
  public static SqlserverDBManager getInstance() {
    if (dbm == null)
      dbm = new SqlserverDBManager(user, password); 
    return dbm;
  }
  
  public Connection getCon() {
    return this.con;
  }
}
