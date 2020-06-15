package com.kqds.util.sys;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySqlTest {
  public Connection getCon() {
    String username = "root";
    String password = "*196BDEDE2AE4F84CA44C47D54D78478C7E2BD7B7";
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/h2?useUnicode=true&characterEncoding=latin1";
    Connection conn = null;
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, username, password);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return conn;
  }
  
  public List<String> getSelect() {
    String sql = "select * from parameter";
    Connection conn = getCon();
    PreparedStatement pst = null;
    List<String> list = new ArrayList<>();
    try {
      pst = conn.prepareStatement(sql);
      ResultSet rs = pst.executeQuery();
      while (rs.next())
        list.add(rs.getString("note")); 
    } catch (Exception exception) {}
    return list;
  }
  
  public static void main(String[] args) throws UnsupportedEncodingException {
    MySqlTest dao = new MySqlTest();
    List<String> list = dao.getSelect();
    for (int i = 0; i < list.size(); i++);
  }
}
