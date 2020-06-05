package com.kqds.core.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.auth.YZAuthenticator;
import com.kqds.core.util.db.generics.DataTableEntity;
import com.kqds.core.util.db.generics.YZORMSelect;
import com.kqds.util.sys.YZUtility;
import com.kqds.util.sys.other.YZStringFormat;

import net.sf.json.JSONObject;

public class DBUtil {

	private static Logger logger = LoggerFactory.getLogger(DBUtil.class);

	/**
	 * 判断是否是mysql数据库版本
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean isMysql() {
		String dbms = YZSysProps.getProp("db.jdbc.dbms");
		if (YZUtility.isNullorEmpty(dbms)) {
			logger.error("isMysql Error：系统未配置数据库类型是mysql还是sqlserver");
		}
		dbms = dbms.trim();
		dbms = dbms.toLowerCase();

		if ("mysql".equals(dbms)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据resultset 返回 Json list，目前仅在 MyResultSetInterceptor 拦截器中使用 yangsen 2018-4-10
	 * 
	 * @param rs
	 * @param list
	 * @param count
	 * @param isMysql
	 * @throws SQLException
	 * @throws Exception
	 */
	public static void getJsonListByResultSet(ResultSet rs, List<JSONObject> list, int count, boolean isMysql) throws SQLException, Exception {
		// 处理ResultSet结构体信息
		DataTableEntity dataTable = YZORMSelect.getDataTableEntity(rs, true); // 在这个方法就转成小写，比放在下面的循环里，效率高
		// 取出ResultSet结构体信息
		int nColumnCount = dataTable.getColumnCount();
		int[] strColumnTypes = dataTable.getColumnTypes();
		String[] strColumnNames = dataTable.getColumnNames();

		while (rs.next()) {
			JSONObject obj = new JSONObject();
			for (int i = 0; i < nColumnCount; i++) {
				String colName = strColumnNames[i];
				if ("barcode".equals(colName)) { // 大量查询二维码会导致java内存溢出
					continue;
				}
				// 获取字段值
				Object objColumnValue = YZORMSelect.getColumnValByIndex(rs, strColumnTypes[i], i + 1); // 根据数据库字段类型，获取对应的值，resulSet是从1开始的
				String value = null;
				if (objColumnValue == null) {
					value = "";
				} else {
					value = objColumnValue + "";
					value = YZAuthenticator.decryKqdsPhonenumber(colName, value);
				}
				obj.put(colName, value);
				if (colName.contains("_")) { // 保险起见，针对seq_id这种带下划线的字段，存两个值，分别为seq_id和seqId
					String field2 = YZStringFormat.unformat(colName);
					obj.put(field2, value);
				}
			}
			count++;
			if (isMysql) {
				obj.put("rownumber", count);
			}
			list.add(obj);
		}
	}

	/**
	 * 关闭数据库资源
	 * 
	 * @param dbConn
	 * @param stmt
	 * @param rs
	 */
	public static void close(Statement stmt, ResultSet rs, Logger log) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception ex) {
			if (log != null && log.isDebugEnabled()) {
				log.error(ex.getMessage(), ex);
			}
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception ex) {
			if (log != null && log.isDebugEnabled()) {
				log.error("close Error：" + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * 根据sql执行更新操作
	 * 
	 * @param conn
	 * @param sql
	 * @throws Exception
	 */
	public static int EXECUTE_UPDATE(Connection dbConn, String sql, HttpServletRequest request) throws Exception {
		int count = 0;
		PreparedStatement ps = null;
		try {
			ps = dbConn.prepareStatement(sql);
			count = ps.executeUpdate();

			if (request != null) {
				// 记录日志
				// KqdsLogUtil.insertUpdateSQL(sql, request);
			}

		} catch (Exception e) {
			// System.out.println(sql);
			logger.error("EXECUTE_UPDATE Error：" + e.getMessage());
			throw e;
		} finally {
			close(ps, null, logger);
		}
		return count;
	}

}
