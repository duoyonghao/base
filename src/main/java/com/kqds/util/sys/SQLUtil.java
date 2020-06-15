package com.kqds.util.sys;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.db.DBUtil;
import java.sql.SQLException;

public class SQLUtil
{
  public static String dateadd(String columnName, int daycount)
  {
    if (DBUtil.isMysql()) {
      return " DATE_ADD(" + columnName + ",INTERVAL " + daycount + " DAY) ";
    }
    return " dateadd(day, " + daycount + ", " + columnName + ") ";
  }
  
  public static String NULLIF()
  {
    if (DBUtil.isMysql()) {
      return "IFNULL";
    }
    return "NULLIF";
  }
  
  public static String convertFloat(String fieldName)
  {
    if (DBUtil.isMysql()) {
      return " " + fieldName + " ";
    }
    return " convert(float," + fieldName + ") ";
  }
  
  public static String convertInt(String fieldName)
  {
    if (DBUtil.isMysql()) {
      return " " + fieldName + " ";
    }
    return " convert(int," + fieldName + ") ";
  }
  
  public static String convertDecimal(String fieldName, int num1, int num2)
  {
    if (DBUtil.isMysql()) {
      return " convert(" + fieldName + ",decimal(" + num1 + "," + num2 + ")) ";
    }
    return " convert(varchar,convert(decimal(" + num1 + "," + num2 + ")," + fieldName + ") )";
  }
  
  public static String selectTop(int topNumer, String sqlstr)
  {
    StringBuffer topsql = new StringBuffer();
    if (DBUtil.isMysql()) {
      topsql.append("select ").append(sqlstr).append(" limit 0,").append(topNumer);
    } else {
      topsql.append("select top ").append(topNumer).append(" ").append(sqlstr);
    }
    return topsql.toString();
  }
  
  public static String dateDiffDay(String columnName)
  {
    String diffStr = "";
    if (DBUtil.isMysql()) {
      diffStr = " to_days(" + columnName + ") = to_days(now()) ";
    } else {
      diffStr = " datediff(day, " + columnName + ",getdate())=0 ";
    }
    return diffStr;
  }
  
  public static String dateDiffDay(String columnName, int daynum)
  {
    String diffStr = "";
    if (DBUtil.isMysql()) {
      diffStr = " DATE_SUB(CURDATE(), INTERVAL " + daynum + " DAY) <= date(" + columnName + ") ";
    } else {
      diffStr = " datediff(day, " + columnName + ",getdate())=" + daynum + " ";
    }
    return diffStr;
  }
  
  public static String dateDiffMonth(String columnName)
  {
    String diffStr = "";
    if (DBUtil.isMysql()) {
      diffStr = " DATE_FORMAT(" + columnName + ", '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) ";
    } else {
      diffStr = " datediff(month, " + columnName + ",getdate())=0 ";
    }
    return diffStr;
  }
  
  public static String dateDiffYear(String columnName)
  {
    String diffStr = "";
    if (DBUtil.isMysql()) {
      diffStr = " YEAR(" + columnName + ")=YEAR(NOW()) ";
    } else {
      diffStr = " datediff(year, " + columnName + ",getdate())=0 ";
    }
    return diffStr;
  }
  
  public static String length(String columnName)
  {
    String str = "";
    if (DBUtil.isMysql()) {
      str = " length(" + columnName + ") ";
    } else {
      str = " len(" + columnName + ") ";
    }
    return str;
  }
  
  public static String getCurrDate()
  {
    if (DBUtil.isMysql()) {
      return " curdate() ";
    }
    return " convert(varchar(10),getdate(),23) ";
  }
  
  public static String castAsInt(String columnName)
  {
    if (DBUtil.isMysql()) {
      return " " + columnName + " ";
    }
    return " cast(" + columnName + " as int) ";
  }
  
  public static String castAsFloat(String columnName)
  {
    if (DBUtil.isMysql()) {
      return " " + columnName + " ";
    }
    return " cast(" + columnName + " as float) ";
  }
  
  public static String findInSet(String str, String dbFieldName)
    throws SQLException
  {
    String dbms = YZSysProps.getProp("db.jdbc.dbms");
    String result = "";
    if (dbms.startsWith("sqlserver")) {
      result = "charindex('" + str + "'," + dbFieldName + ")>0";
    } else if (dbms.startsWith("mysql")) {
      result = " find_in_set('" + str + "'," + dbFieldName + ")>0";
    } else if (dbms.startsWith("oracle")) {
      result = "instr(','||" + dbFieldName + "||',','," + str + ",') > 0";
    } else {
      throw new SQLException("not accepted dbms");
    }
    return result;
  }
}
