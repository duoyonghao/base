package com.kqds.core.util.db.generics;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.db.DBUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

public class YZORMSelect {
  public static DataTableEntity getDataTableEntity(ResultSet rs, boolean isLowerCase) throws Exception {
    DataTableEntity dataTable = null;
    if (rs != null) {
      ResultSetMetaData rsMetaData = rs.getMetaData();
      int columnCount = rsMetaData.getColumnCount();
      dataTable = new DataTableEntity(columnCount);
      for (int i = 0; i < columnCount; i++) {
        String columnName = null;
        if (DBUtil.isMysql()) {
          columnName = rsMetaData.getColumnLabel(i + 1);
        } else {
          columnName = rsMetaData.getColumnName(i + 1);
        } 
        if (isLowerCase)
          columnName = columnName.toLowerCase(); 
        int columnType = rsMetaData.getColumnType(i + 1);
        dataTable.setColumnName(columnName, i);
        dataTable.setColumnType(columnType, i);
      } 
    } 
    return dataTable;
  }
  
  public static Object getColumnValByIndex(ResultSet rs, int dbType, int i) throws Exception {
    Object value = null;
    if (rs.getObject(i) == null)
      return ""; 
    String dbms = YZSysProps.getProp("db.jdbc.dbms");
    if (dbms.equals("sqlserver") && dbType == 8)
      dbType = 3; 
    if (dbType == -6) {
      value = Byte.valueOf(rs.getByte(i));
    } else if (dbType == 5) {
      value = Short.valueOf(rs.getShort(i));
    } else if (dbType == 4 || dbType == 2) {
      value = Integer.valueOf(rs.getInt(i));
    } else if (dbType == -5) {
      value = Long.valueOf(rs.getLong(i));
    } else if (dbType == 6 || dbType == 7) {
      value = Float.valueOf(rs.getFloat(i));
    } else if (dbType == 8) {
      value = Double.valueOf(rs.getDouble(i));
    } else if (dbType == 3) {
      value = KqdsBigDecimal.round(rs.getBigDecimal(i), 2);
    } else if (dbType == -7) {
      value = Boolean.valueOf(rs.getBoolean(i));
    } else if (dbType == 1 || dbType == 12 || dbType == -1) {
      value = rs.getString(i);
    } else if (dbType == 2005) {
      Clob cl = rs.getClob(i);
      value = YZSQLParamHepler.clobToString(cl);
    } else if (dbType == 91) {
      Date date = rs.getDate(i);
      value = date;
    } else if (dbType == 92) {
      value = rs.getTime(i);
    } else if (dbType == 93) {
      if (rs.getTimestamp(i) != null)
        value = rs.getTimestamp(i); 
    } else if (dbType == -2 || dbType == -3 || dbType == -4 || dbType == 2004) {
      value = rs.getBytes(i);
    } else {
      throw new SQLException("数据库中包含不支持的自动映射数据类型：" + dbType);
    } 
    return value;
  }
}
