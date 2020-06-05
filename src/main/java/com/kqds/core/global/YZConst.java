package com.kqds.core.global;

/**
 * 系统通用常量定义
 * 
 * @author jpt
 * @version 1.0
 * @date 2007-1-30
 */
public class YZConst {

	/**
	 * 秒
	 */
	public static final long DT_S = 1000;
	/**
	 * 分
	 */
	public static final long DT_MINIT = DT_S * 60;
	/**
	 * 时
	 */
	public static final long DT_H = DT_MINIT * 60;
	/**
	 * 天
	 */
	public static final long DT_D = DT_H * 24;
	/**
	 * 1k
	 */
	public static final int K = 1024;
	/**
	 * 1M
	 */
	public static final int M = K * K;

	/**
	 * 缺省编码
	 */
	public static final String DEFAULT_CODE = "UTF-8";

	/**
	 * 错误返回
	 */
	public static final String RETURN_ERROR = "1";
	/**
	 * 正确返回
	 */
	public static final String RETURN_OK = "0";
	/**
	 * 正确返回
	 */
	public static final String RETURN_MISTAKE = "2";

	/**
	 * 默认输入输出缓冲大小
	 */
	public static final int DEFAULT_IO_BUFF_SIZE = K * 10;

	/**
	 * 数据库管理系统-MSSqlServer
	 */
	public static final String DBMS_SQLSERVER = "sqlserver";
	/**
	 * 数据库管理系统-MySql
	 */
	public static final String DBMS_MYSQL = "mysql";
	/**
	 * 数据库管理系统-Oracle
	 */
	public static final String DBMS_ORACLE = "oracle";

}
