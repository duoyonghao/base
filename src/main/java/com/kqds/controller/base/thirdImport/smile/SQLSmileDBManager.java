package com.kqds.controller.base.thirdImport.smile;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLSmileDBManager
{
  private static String user = "sa";
  private static String password = "1qaz2wsx,,,";
  private static String url = "jdbc:jtds:sqlserver://localhost:1433/Smile_dantel";
  private Connection con = null;
  private static SQLSmileDBManager dbm = null;
  
  private SQLSmileDBManager(String user, String password)
  {
    try
    {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
    }
    catch (ClassNotFoundException e)
    {
      System.err.println("class not found:" + e.getMessage());
    }
    try
    {
      this.con = DriverManager.getConnection(url, user, password);
    }
    catch (SQLException a)
    {
      System.err.println("sql exception:" + a.getMessage());
    }
  }
  
  public static SQLSmileDBManager getInstance()
  {
    if (dbm == null) {
      dbm = new SQLSmileDBManager(user, password);
    }
    return dbm;
  }
  
  public Connection getCon()
  {
    return this.con;
  }
}
