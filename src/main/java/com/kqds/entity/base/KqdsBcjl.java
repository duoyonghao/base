package com.kqds.entity.base;

public class KqdsBcjl
{
  private String seqId;
  private String createuser;
  private String createtime;
  private String jlname;
  private String deptid;
  private String usercode;
  private String ifshow;
  private String regno;
  private String bcmc;
  private String organization;
  private Integer logtype;
  private String ip;
  private String tablename;
  private String sessiontoken;
  private String requesttoken;
  private String content;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
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
  
  public String getJlname()
  {
    return this.jlname;
  }
  
  public void setJlname(String jlname)
  {
    this.jlname = (jlname == null ? null : jlname.trim());
  }
  
  public String getDeptid()
  {
    return this.deptid;
  }
  
  public void setDeptid(String deptid)
  {
    this.deptid = (deptid == null ? null : deptid.trim());
  }
  
  public String getUsercode()
  {
    return this.usercode;
  }
  
  public void setUsercode(String usercode)
  {
    this.usercode = (usercode == null ? null : usercode.trim());
  }
  
  public String getIfshow()
  {
    return this.ifshow;
  }
  
  public void setIfshow(String ifshow)
  {
    this.ifshow = (ifshow == null ? null : ifshow.trim());
  }
  
  public String getRegno()
  {
    return this.regno;
  }
  
  public void setRegno(String regno)
  {
    this.regno = (regno == null ? null : regno.trim());
  }
  
  public String getBcmc()
  {
    return this.bcmc;
  }
  
  public void setBcmc(String bcmc)
  {
    this.bcmc = (bcmc == null ? null : bcmc.trim());
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public Integer getLogtype()
  {
    return this.logtype;
  }
  
  public void setLogtype(Integer logtype)
  {
    this.logtype = logtype;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = (ip == null ? null : ip.trim());
  }
  
  public String getTablename()
  {
    return this.tablename;
  }
  
  public void setTablename(String tablename)
  {
    this.tablename = (tablename == null ? null : tablename.trim());
  }
  
  public String getSessiontoken()
  {
    return this.sessiontoken;
  }
  
  public void setSessiontoken(String sessiontoken)
  {
    this.sessiontoken = (sessiontoken == null ? null : sessiontoken.trim());
  }
  
  public String getRequesttoken()
  {
    return this.requesttoken;
  }
  
  public void setRequesttoken(String requesttoken)
  {
    this.requesttoken = (requesttoken == null ? null : requesttoken.trim());
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = (content == null ? null : content.trim());
  }
}
