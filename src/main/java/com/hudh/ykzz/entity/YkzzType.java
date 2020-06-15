package com.hudh.ykzz.entity;

public class YkzzType
{
  private String id;
  private String typeName;
  private String parentid;
  private String orderno;
  private String createtime;
  private String creator;
  private String organization;
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getTypeName()
  {
    return this.typeName;
  }
  
  public void setTypeName(String typeName)
  {
    this.typeName = typeName;
  }
  
  public String getParentid()
  {
    return this.parentid;
  }
  
  public void setParentid(String parentid)
  {
    this.parentid = parentid;
  }
  
  public String getOrderno()
  {
    return this.orderno;
  }
  
  public void setOrderno(String orderno)
  {
    this.orderno = orderno;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = createtime;
  }
  
  public String getCreator()
  {
    return this.creator;
  }
  
  public void setCreator(String creator)
  {
    this.creator = creator;
  }
}
