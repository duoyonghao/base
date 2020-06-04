package com.kqds.controller.base.thirdImport.aijian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDBManager {
	// 用户名
	private static String user = "root";
	// 密码
	private static String password = "1qaz2wsx,,,";
	// 主机
	private static String host = "localhost:33006";
	// 数据库名字
	private static String database = "h2";
	private static String url = "";
	private Connection con = null;
	private static MysqlDBManager dbm = null;

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
	private MysqlDBManager(String host, String database, String user, String password) {

		// 显示中文
		url = "jdbc:mysql://" + host + "/" + database + "?useUnicode=true&characterEncoding=latin1&useOldAliasMetadataBehavior=true";

		try {
			Class.forName("com.mysql.jdbc.Driver");
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
	public static MysqlDBManager getInstance() {
		if (dbm == null) {
			dbm = new MysqlDBManager(host, database, user, password);
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
