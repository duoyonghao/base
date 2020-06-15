package com.kqds.entity.base;

public class KqdsLog
{
  private String seqId;
  private Integer logType;
  private String requesttoken;
  private String userCode;
  private String tableName;
  private String createuser;
  private String createtime;
  private String sessiontoken;
  private String requestUrl;
  private String requestParam;
  private String insertObject;
  private String beforeObject;
  private String afterObject;
  private String deleteObject;
  private String updateSql;
  private String deleteSql;
  private String querySql;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public Integer getLogType()
  {
    return this.logType;
  }
  
  public void setLogType(Integer logType)
  {
    this.logType = logType;
  }
  
  public String getRequesttoken()
  {
    return this.requesttoken;
  }
  
  public void setRequesttoken(String requesttoken)
  {
    this.requesttoken = (requesttoken == null ? null : requesttoken.trim());
  }
  
  public String getUserCode()
  {
    return this.userCode;
  }
  
  public void setUserCode(String userCode)
  {
    this.userCode = (userCode == null ? null : userCode.trim());
  }
  
  public String getTableName()
  {
    return this.tableName;
  }
  
  public void setTableName(String tableName)
  {
    this.tableName = (tableName == null ? null : tableName.trim());
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = (createuser == null ? null : createuser.trim());
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = (createtime == null ? null : createtime.trim());
  }
  
  public String getSessiontoken()
  {
    return this.sessiontoken;
  }
  
  public void setSessiontoken(String sessiontoken)
  {
    this.sessiontoken = (sessiontoken == null ? null : sessiontoken.trim());
  }
  
  public String getRequestUrl()
  {
    return this.requestUrl;
  }
  
  public void setRequestUrl(String requestUrl)
  {
    this.requestUrl = (requestUrl == null ? null : requestUrl.trim());
  }
  
  public String getRequestParam()
  {
    return this.requestParam;
  }
  
  public void setRequestParam(String requestParam)
  {
    this.requestParam = (requestParam == null ? null : requestParam.trim());
  }
  
  public String getInsertObject()
  {
    return this.insertObject;
  }
  
  public void setInsertObject(String insertObject)
  {
    this.insertObject = (insertObject == null ? null : insertObject.trim());
  }
  
  public String getBeforeObject()
  {
    return this.beforeObject;
  }
  
  public void setBeforeObject(String beforeObject)
  {
    this.beforeObject = (beforeObject == null ? null : beforeObject.trim());
  }
  
  public String getAfterObject()
  {
    return this.afterObject;
  }
  
  public void setAfterObject(String afterObject)
  {
    this.afterObject = (afterObject == null ? null : afterObject.trim());
  }
  
  public String getDeleteObject()
  {
    return this.deleteObject;
  }
  
  public void setDeleteObject(String deleteObject)
  {
    this.deleteObject = (deleteObject == null ? null : deleteObject.trim());
  }
  
  public String getUpdateSql()
  {
    return this.updateSql;
  }
  
  public void setUpdateSql(String updateSql)
  {
    this.updateSql = (updateSql == null ? null : updateSql.trim());
  }
  
  public String getDeleteSql()
  {
    return this.deleteSql;
  }
  
  public void setDeleteSql(String deleteSql)
  {
    this.deleteSql = (deleteSql == null ? null : deleteSql.trim());
  }
  
  public String getQuerySql()
  {
    return this.querySql;
  }
  
  public void setQuerySql(String querySql)
  {
    this.querySql = (querySql == null ? null : querySql.trim());
  }
}
