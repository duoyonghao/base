package com.hudh.lclj.entity;

import java.util.List;

public class LcljOperate
{
  private String name;
  private String isComplate;
  private List<LcljOperateDetail> detail;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getIsComplate()
  {
    return this.isComplate;
  }
  
  public void setIsComplate(String isComplate)
  {
    this.isComplate = isComplate;
  }
  
  public List<LcljOperateDetail> getDetail()
  {
    return this.detail;
  }
  
  public void setDetail(List<LcljOperateDetail> detail)
  {
    this.detail = detail;
  }
}
