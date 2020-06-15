package com.hudh.ksll.entity;

import java.math.BigDecimal;

public class KsllReplaceMent
{
  private String id;
  private String deptpart;
  private String remark;
  private String creator;
  private Integer status;
  private String createtime;
  private String organization;
  private String sshouse;
  private BigDecimal goodprice;
  private Integer type;
  private String floor;
  
  public String getFloor()
  {
    return this.floor;
  }
  
  public void setFloor(String floor)
  {
    this.floor = floor;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getDeptpart()
  {
    return this.deptpart;
  }
  
  public void setDeptpart(String deptpart)
  {
    this.deptpart = deptpart;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getCreator()
  {
    return this.creator;
  }
  
  public void setCreator(String creator)
  {
    this.creator = creator;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = createtime;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
  public String getSshouse()
  {
    return this.sshouse;
  }
  
  public void setSshouse(String sshouse)
  {
    this.sshouse = sshouse;
  }
  
  public BigDecimal getGoodprice()
  {
    return this.goodprice;
  }
  
  public void setGoodprice(BigDecimal goodprice)
  {
    this.goodprice = goodprice;
  }
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer type)
  {
    this.type = type;
  }
}
