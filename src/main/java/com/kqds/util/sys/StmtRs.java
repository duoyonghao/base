package com.kqds.util.sys;

import com.kqds.core.util.db.DBUtil;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StmtRs
{
  private static Logger logger = LoggerFactory.getLogger(StmtRs.class);
  private ResultSet rs;
  private Statement stmt;
  
  public ResultSet getRs()
  {
    return this.rs;
  }
  
  public void setRs(ResultSet rs)
  {
    this.rs = rs;
  }
  
  public Statement getStmt()
  {
    return this.stmt;
  }
  
  public void setStmt(Statement stmt)
  {
    this.stmt = stmt;
  }
  
  public void colse()
  {
    DBUtil.close(this.stmt, this.rs, logger);
  }
}
