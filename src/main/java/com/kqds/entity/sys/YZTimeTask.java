package com.kqds.entity.sys;

import java.io.Serializable;
import java.util.Date;

public class YZTimeTask
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String seqId;
  private String taskId;
  private String taskDescribe;
  private String cronExpression;
  private String isEffect;
  private String isStart;
  private Date createDate;
  private String createBy;
  private String createName;
  private Date updateDate;
  private String updateBy;
  private String updateName;
  private String className;
  private String runServerIp;
  private String runServer;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = seqId;
  }
  
  public String getTaskId()
  {
    return this.taskId;
  }
  
  public void setTaskId(String taskId)
  {
    this.taskId = taskId;
  }
  
  public String getTaskDescribe()
  {
    return this.taskDescribe;
  }
  
  public void setTaskDescribe(String taskDescribe)
  {
    this.taskDescribe = taskDescribe;
  }
  
  public String getCronExpression()
  {
    return this.cronExpression;
  }
  
  public void setCronExpression(String cronExpression)
  {
    this.cronExpression = cronExpression;
  }
  
  public String getIsEffect()
  {
    return this.isEffect;
  }
  
  public void setIsEffect(String isEffect)
  {
    this.isEffect = isEffect;
  }
  
  public String getIsStart()
  {
    return this.isStart;
  }
  
  public void setIsStart(String isStart)
  {
    this.isStart = isStart;
  }
  
  public Date getCreateDate()
  {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }
  
  public String getCreateBy()
  {
    return this.createBy;
  }
  
  public void setCreateBy(String createBy)
  {
    this.createBy = createBy;
  }
  
  public String getCreateName()
  {
    return this.createName;
  }
  
  public void setCreateName(String createName)
  {
    this.createName = createName;
  }
  
  public Date getUpdateDate()
  {
    return this.updateDate;
  }
  
  public void setUpdateDate(Date updateDate)
  {
    this.updateDate = updateDate;
  }
  
  public String getUpdateBy()
  {
    return this.updateBy;
  }
  
  public void setUpdateBy(String updateBy)
  {
    this.updateBy = updateBy;
  }
  
  public String getUpdateName()
  {
    return this.updateName;
  }
  
  public void setUpdateName(String updateName)
  {
    this.updateName = updateName;
  }
  
  public String getClassName()
  {
    return this.className;
  }
  
  public void setClassName(String className)
  {
    this.className = className;
  }
  
  public String getRunServerIp()
  {
    return this.runServerIp;
  }
  
  public void setRunServerIp(String runServerIp)
  {
    this.runServerIp = runServerIp;
  }
  
  public String getRunServer()
  {
    return this.runServer;
  }
  
  public void setRunServer(String runServer)
  {
    this.runServer = runServer;
  }
}
