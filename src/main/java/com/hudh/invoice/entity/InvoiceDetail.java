package com.hudh.invoice.entity;

import java.math.BigDecimal;

public class InvoiceDetail
{
  private String seqId;
  private String dutyParayraph;
  private String invoiceTime;
  private BigDecimal invoiceValue;
  private String drawer;
  private String taxpayerNumber;
  private String createtime;
  private String createuser;
  private String usercode;
  private String invoiceDetail;
  private String organization;
  private String updatetime;
  private String updateuser;
  private int status;
  private int dishonour;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = seqId;
  }
  
  public String getDutyParayraph()
  {
    return this.dutyParayraph;
  }
  
  public void setDutyParayraph(String dutyParayraph)
  {
    this.dutyParayraph = dutyParayraph;
  }
  
  public String getInvoiceTime()
  {
    return this.invoiceTime;
  }
  
  public void setInvoiceTime(String invoiceTime)
  {
    this.invoiceTime = invoiceTime;
  }
  
  public BigDecimal getInvoiceValue()
  {
    return this.invoiceValue;
  }
  
  public void setInvoiceValue(BigDecimal invoiceValue)
  {
    this.invoiceValue = invoiceValue;
  }
  
  public String getDrawer()
  {
    return this.drawer;
  }
  
  public void setDrawer(String drawer)
  {
    this.drawer = drawer;
  }
  
  public String getTaxpayerNumber()
  {
    return this.taxpayerNumber;
  }
  
  public void setTaxpayerNumber(String taxpayerNumber)
  {
    this.taxpayerNumber = taxpayerNumber;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = createtime;
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser)
  {
    this.createuser = createuser;
  }
  
  public String getUsercode()
  {
    return this.usercode;
  }
  
  public void setUsercode(String usercode)
  {
    this.usercode = usercode;
  }
  
  public String getInvoiceDetail()
  {
    return this.invoiceDetail;
  }
  
  public void setInvoiceDetail(String invoiceDetail)
  {
    this.invoiceDetail = invoiceDetail;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
  public String getUpdatetime()
  {
    return this.updatetime;
  }
  
  public void setUpdatetime(String updatetime)
  {
    this.updatetime = updatetime;
  }
  
  public String getUpdateuser()
  {
    return this.updateuser;
  }
  
  public void setUpdateuser(String updateuser)
  {
    this.updateuser = updateuser;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public int getDishonour()
  {
    return this.dishonour;
  }
  
  public void setDishonour(int dishonour)
  {
    this.dishonour = dishonour;
  }
}
