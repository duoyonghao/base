package com.kqds.util.sys;

import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kqds.core.util.db.DBUtil;

/**
 * 存放查询数据库返回的两个对象
 * 
 * @author Administrator
 * 
 */
public class StmtRs {

	private static Logger logger = LoggerFactory.getLogger(StmtRs.class);

	private ResultSet rs;
	private Statement stmt;

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public void colse() {
		DBUtil.close(stmt, rs, logger);
	}

}
