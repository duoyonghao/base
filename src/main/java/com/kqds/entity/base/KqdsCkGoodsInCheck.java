package com.kqds.entity.base;

public class KqdsCkGoodsInCheck
{
  private String SEQ_ID;
  private String goodsinid;
  private String checkUserId;
  private String checkDate;
  private String remark;
  private String result;
  private String organization;
  private String packing;
  private String certificate;
  private String report;
  
  public String getPacking()
  {
    return this.packing;
  }
  
  public void setPacking(String packing)
  {
    this.packing = packing;
  }
  
  public String getCertificate()
  {
    return this.certificate;
  }
  
  public void setCertificate(String certificate)
  {
    this.certificate = certificate;
  }
  
  public String getReport()
  {
    return this.report;
  }
  
  public void setReport(String report)
  {
    this.report = report;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
  public String getSEQ_ID()
  {
    return this.SEQ_ID;
  }
  
  public void setSEQ_ID(String sEQ_ID)
  {
    this.SEQ_ID = sEQ_ID;
  }
  
  public String getGoodsinid()
  {
    return this.goodsinid;
  }
  
  public void setGoodsinid(String goodsinid)
  {
    this.goodsinid = goodsinid;
  }
  
  public String getCheckUserId()
  {
    return this.checkUserId;
  }
  
  public void setCheckUserId(String checkUserId)
  {
    this.checkUserId = checkUserId;
  }
  
  public String getCheckDate()
  {
    return this.checkDate;
  }
  
  public void setCheckDate(String checkDate)
  {
    this.checkDate = checkDate;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getResult()
  {
    return this.result;
  }
  
  public void setResult(String result)
  {
    this.result = result;
  }
}
