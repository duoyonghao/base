package com.kqds.entity.base;

public class KqdsJhVideo
{
  private String id;
  private String url;
  private String createtime;
  private String operator;
  private int del;
  private String createuser;
  private String filename;
  private String organization;
  private String burningTime;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = createtime;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String operator)
  {
    this.operator = operator;
  }
  
  public int getDel()
  {
    return this.del;
  }
  
  public void setDel(int del)
  {
    this.del = del;
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = createuser;
  }
  
  public String getFilename()
  {
    return this.filename;
  }
  
  public void setFilename(String filename)
  {
    this.filename = filename;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
  public String getBurningTime()
  {
    return this.burningTime;
  }
  
  public void setBurningTime(String burningTime)
  {
    this.burningTime = burningTime;
  }
}
