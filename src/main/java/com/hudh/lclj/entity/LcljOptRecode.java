package com.hudh.lclj.entity;

public class LcljOptRecode
{
  private String id;
  private String orderNumber;
  private String createtime;
  private String creator;
  private String flowLink;
  private String remarks;
  private String variation;
  private String status;
  
  public String getVariation()
  {
    return this.variation;
  }
  
  public void setVariation(String variation)
  {
    this.variation = variation;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getOrderNumber()
  {
    return this.orderNumber;
  }
  
  public void setOrderNumber(String orderNumber)
  {
    this.orderNumber = orderNumber;
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
  
  public String getFlowLink()
  {
    return this.flowLink;
  }
  
  public void setFlowLink(String flowLink)
  {
    this.flowLink = flowLink;
  }
  
  public String getRemarks()
  {
    return this.remarks;
  }
  
  public void setRemarks(String remarks)
  {
    this.remarks = remarks;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
}
