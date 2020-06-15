package com.kqds.entity.sys;

import java.util.Date;

public class YZLog
{
  private String seqId;
  private String userId;
  private Date time;
  private String ip;
  private String type;
  private String organization;
  private String remark;
  private String userName;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = (userId == null ? null : userId.trim());
  }
  
  public Date getTime()
  {
    return this.time;
  }
  
  public void setTime(Date time)
  {
    this.time = time;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = (ip == null ? null : ip.trim());
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = (type == null ? null : type.trim());
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = (remark == null ? null : remark.trim());
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = (userName == null ? null : userName.trim());
  }
}
