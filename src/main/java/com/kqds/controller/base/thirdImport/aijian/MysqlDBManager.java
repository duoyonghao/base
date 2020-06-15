package com.kqds.controller.base.thirdImport.aijian;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDBManager
{
  private static String user = "root";
  private static String password = "1qaz2wsx,,,";
  private static String host = "localhost:33006";
  private static String database = "h2";
  private static String url = "";
  private Connection con = null;
  private static MysqlDBManager dbm = null;
  
  private MysqlDBManager(String host, String database, String user, String password)
  {
    url = "jdbc:mysql://" + host + "/" + database + "?useUnicode=true&characterEncoding=latin1&useOldAliasMetadataBehavior=true";
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
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
  
  public static MysqlDBManager getInstance()
  {
    if (dbm == null) {
      dbm = new MysqlDBManager(host, database, user, password);
    }
    return dbm;
  }
  
  public Connection getCon()
  {
    return this.con;
  }
}
