package com.kqds.core.util.db.generics;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import com.kqds.core.global.YZSysProps;
import com.kqds.core.util.db.DBUtil;
import com.kqds.util.sys.other.KqdsBigDecimal;

public class YZORMSelect {

	public static DataTableEntity getDataTableEntity(ResultSet rs, boolean isLowerCase) throws Exception {
		DataTableEntity dataTable = null;
		// 处理ResultSet结构体信息
		if (rs != null) {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			dataTable = new DataTableEntity(columnCount);
			// 获取字段名称，类型
			for (int i = 0; i < columnCount; i++) {
				String columnName = null;
				if (DBUtil.isMysql()) {
					columnName = rsMetaData.getColumnLabel(i + 1);
				} else {
					columnName = rsMetaData.getColumnName(i + 1);
				}
				if (isLowerCase) { // 判断是否需要转成小写，返回json需要，返回bean不需要
					columnName = columnName.toLowerCase();
				}
				int columnType = rsMetaData.getColumnType(i + 1);
				dataTable.setColumnName(columnName, i);
				dataTable.setColumnType(columnType, i);
			}
		}
		return dataTable;
	}

	/**
	 * 获取数据库字段值
	 * 
	 * @param rs
	 * @param dbType
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public static Object getColumnValByIndex(ResultSet rs, int dbType, int i) throws Exception {
		Object value = null;
		if (rs.getObject(i) == null) {
			return "";
		}

		// 取得第i列的数据类型
		String dbms = YZSysProps.getProp("db.jdbc.dbms");
		if (dbms.equals("sqlserver") && dbType == 8) {
			dbType = 3;
		}

		// 并反射执行该列的在 pojo 中对应属性的 setter 方法完成赋值
		if (dbType == Types.TINYINT) {
			value = rs.getByte(i);
		} else if (dbType == Types.SMALLINT) {
			value = rs.getShort(i);
		} else if (dbType == Types.INTEGER || dbType == Types.NUMERIC) {
			value = rs.getInt(i);
		} else if (dbType == Types.BIGINT) {
			value = rs.getLong(i);
		} else if (dbType == Types.FLOAT || dbType == Types.REAL) {
			value = rs.getFloat(i);
		} else if (dbType == Types.DOUBLE) {
			value = rs.getDouble(i);
		} else if (dbType == Types.DECIMAL) {
			value = KqdsBigDecimal.round(rs.getBigDecimal(i), 2);
		} else if (dbType == Types.BIT) {
			value = rs.getBoolean(i);
		} else if (dbType == Types.CHAR || dbType == Types.VARCHAR || dbType == Types.LONGVARCHAR) {
			value = rs.getString(i);
		} else if (dbType == Types.CLOB) {
			Clob cl = rs.getClob(i);
			value = YZSQLParamHepler.clobToString(cl);
		} else if (dbType == Types.DATE) { // 继承于 java.util.Date 类
			java.util.Date date = rs.getDate(i);
			value = date;
		} else if (dbType == Types.TIME) { // 继承于 java.util.Date 类
			value = rs.getTime(i);
		} else if (dbType == Types.TIMESTAMP) { // 继承于 java.util.Date 类
			if (rs.getTimestamp(i) != null) {
				value = rs.getTimestamp(i);
			}
		} else if (dbType == Types.BINARY || dbType == Types.VARBINARY || dbType == Types.LONGVARBINARY || dbType == Types.BLOB) {
			value = rs.getBytes(i);
		} else {
			throw new SQLException("数据库中包含不支持的自动映射数据类型：" + dbType);
		}

		return value;
	}

}
