package com.kqds.core.util.db;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.core.util.db.generics.DataTableEntity;
import com.kqds.core.util.db.generics.YZORMSelect;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.YZStringFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {
  private static Logger logger = LoggerFactory.getLogger(DBUtil.class);
  
  public static boolean isMysql() {
    String dbms = YZSysProps.getProp("db.jdbc.dbms");
    if (YZUtility.isNullorEmpty(dbms))
      logger.error("isMysql Error：系统未配置数据库类型是mysql还是sqlserver"); 
    dbms = dbms.trim();
    dbms = dbms.toLowerCase();
    if ("mysql".equals(dbms))
      return true; 
    return false;
  }
  
  public static void getJsonListByResultSet(ResultSet rs, List<JSONObject> list, int count, boolean isMysql) throws SQLException, Exception {
    DataTableEntity dataTable = YZORMSelect.getDataTableEntity(rs, true);
    int nColumnCount = dataTable.getColumnCount();
    int[] strColumnTypes = dataTable.getColumnTypes();
    String[] strColumnNames = dataTable.getColumnNames();
    while (rs.next()) {
      JSONObject obj = new JSONObject();
      for (int i = 0; i < nColumnCount; i++) {
        String colName = strColumnNames[i];
        if (!"barcode".equals(colName)) {
          Object objColumnValue = YZORMSelect.getColumnValByIndex(rs, strColumnTypes[i], i + 1);
          Object object1 = null;
          if (objColumnValue == null) {
            object1 = "";
          } else {
            object1 = objColumnValue;
            object1 = YZAuthenticator.decryKqdsPhonenumber(colName, (String)object1);
          } 
          obj.put(colName, object1);
          if (colName.contains("_")) {
            String field2 = YZStringFormat.unformat(colName);
            obj.put(field2, object1);
          } 
        } 
      } 
      count++;
      if (isMysql)
        obj.put("rownumber", Integer.valueOf(count)); 
      list.add(obj);
    } 
  }
  
  public static void close(Statement stmt, ResultSet rs, Logger log) {
    try {
      if (rs != null)
        rs.close(); 
    } catch (Exception ex) {
      if (log != null && log.isDebugEnabled())
        log.error(ex.getMessage(), ex); 
    } 
    try {
      if (stmt != null)
        stmt.close(); 
    } catch (Exception ex) {
      if (log != null && log.isDebugEnabled())
        log.error("close Error：" + ex.getMessage(), ex); 
    } 
  }
  
  public static int EXECUTE_UPDATE(Connection dbConn, String sql, HttpServletRequest request) throws Exception {
    int count = 0;
    PreparedStatement ps = null;
    try {
      ps = dbConn.prepareStatement(sql);
      count = ps.executeUpdate();
    } catch (Exception e) {
      logger.error("EXECUTE_UPDATE Error：" + e.getMessage());
      throw e;
    } finally {
      close(ps, null, logger);
    } 
    return count;
  }
}
