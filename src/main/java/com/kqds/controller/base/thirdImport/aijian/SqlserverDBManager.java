package com.kqds.controller.base.thirdImport.aijian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlserverDBManager {
	// 用户名
	private static String user = "sa";
	// 密码
	private static String password = "1qaz2wsx,,,";
	private static String url = "jdbc:jtds:sqlserver://localhost:1433/aijian";
	private Connection con = null;
	private static SqlserverDBManager dbm = null;

	/**
	 * 私有的构造方法，保证外部不能实例化，只能由DBManager自己能提供自 己的实例，并且只能有一个。
	 * 根据主机、数据库名称、数据库用户名、数据库用户密码取得连接。
	 * 
	 * @param host
	 *            String
	 * @param database
	 *            String
	 * @param user
	 *            String
	 * @param password
	 *            String
	 * @return
	 */
	private SqlserverDBManager(String user, String password) {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("class not found:" + e.getMessage());
		}

		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException a) {
			System.err.println("sql exception:" + a.getMessage());
		}
	}

	/**
	 * 静态工厂方法，来获得一个DBManager实例
	 */
	public static SqlserverDBManager getInstance() {
		if (dbm == null) {
			dbm = new SqlserverDBManager(user, password);
		}
		return dbm;
	}

	/**
	 * 返回取得的连接
	 */
	public Connection getCon() {
		return con;
	}
}
