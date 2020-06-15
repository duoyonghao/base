package com.kqds.entity.sys;

import java.util.Date;

public class YZPerson
{
  private String seqId;
  private String userId;
  private String userName;
  private String password;
  private String userPriv;
  private String deptId;
  private Integer userNo;
  private String notLogin;
  private Integer isLeave;
  private String createtime;
  private String createuser;
  private String sex;
  private Date lastVisitTime;
  private String myStatus;
  private String recordaccount;
  private String recordpwd;
  private String deptIdOther;
  private String userPrivOther;
  private String organization;
  private String xy_dept;
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
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
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = (userName == null ? null : userName.trim());
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = (password == null ? null : password.trim());
  }
  
  public String getUserPriv()
  {
    return this.userPriv;
  }
  
  public void setUserPriv(String userPriv)
  {
    this.userPriv = (userPriv == null ? null : userPriv.trim());
  }
  
  public String getDeptId()
  {
    return this.deptId;
  }
  
  public void setDeptId(String deptId)
  {
    this.deptId = (deptId == null ? null : deptId.trim());
  }
  
  public Integer getUserNo()
  {
    return this.userNo;
  }
  
  public void setUserNo(Integer userNo)
  {
    this.userNo = userNo;
  }
  
  public String getNotLogin()
  {
    return this.notLogin;
  }
  
  public void setNotLogin(String notLogin)
  {
    this.notLogin = (notLogin == null ? null : notLogin.trim());
  }
  
  public Integer getIsLeave()
  {
    return this.isLeave;
  }
  
  public void setIsLeave(Integer isLeave)
  {
    this.isLeave = isLeave;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = (createtime == null ? null : createtime.trim());
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = (createuser == null ? null : createuser.trim());
  }
  
  public String getSex()
  {
    return this.sex;
  }
  
  public void setSex(String sex)
  {
    this.sex = (sex == null ? null : sex.trim());
  }
  
  public Date getLastVisitTime()
  {
    return this.lastVisitTime;
  }
  
  public void setLastVisitTime(Date lastVisitTime)
  {
    this.lastVisitTime = lastVisitTime;
  }
  
  public String getMyStatus()
  {
    return this.myStatus;
  }
  
  public void setMyStatus(String myStatus)
  {
    this.myStatus = (myStatus == null ? null : myStatus.trim());
  }
  
  public String getRecordaccount()
  {
    return this.recordaccount;
  }
  
  public void setRecordaccount(String recordaccount)
  {
    this.recordaccount = (recordaccount == null ? null : recordaccount.trim());
  }
  
  public String getRecordpwd()
  {
    return this.recordpwd;
  }
  
  public void setRecordpwd(String recordpwd)
  {
    this.recordpwd = (recordpwd == null ? null : recordpwd.trim());
  }
  
  public String getDeptIdOther()
  {
    return this.deptIdOther;
  }
  
  public void setDeptIdOther(String deptIdOther)
  {
    this.deptIdOther = (deptIdOther == null ? null : deptIdOther.trim());
  }
  
  public String getUserPrivOther()
  {
    return this.userPrivOther;
  }
  
  public void setUserPrivOther(String userPrivOther)
  {
    this.userPrivOther = (userPrivOther == null ? null : userPrivOther.trim());
  }
  
  public String toString()
  {
    return 
    



      "YZPerson [seqId=" + this.seqId + ", userId=" + this.userId + ", userName=" + this.userName + ", password=" + this.password + ", userPriv=" + this.userPriv + ", deptId=" + this.deptId + ", userNo=" + this.userNo + ", notLogin=" + this.notLogin + ", isLeave=" + this.isLeave + ", createtime=" + this.createtime + ", createuser=" + this.createuser + ", sex=" + this.sex + ", lastVisitTime=" + this.lastVisitTime + ", myStatus=" + this.myStatus + ", recordaccount=" + this.recordaccount + ", recordpwd=" + this.recordpwd + ", deptIdOther=" + this.deptIdOther + ", userPrivOther=" + this.userPrivOther + ", organization=" + this.organization + "]";
  }
  
  public String getXy_dept()
  {
    return this.xy_dept;
  }
  
  public void setXy_dept(String xy_dept)
  {
    this.xy_dept = xy_dept;
  }
}
