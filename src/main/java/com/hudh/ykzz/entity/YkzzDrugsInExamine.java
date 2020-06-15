package com.hudh.ykzz.entity;

public class YkzzDrugsInExamine
{
  private String id;
  private String drugsInId;
  private String packing;
  private String certificate;
  private String report;
  private String checkUserId;
  private String checkDate;
  private String remark;
  private String result;
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
  
  public String getDrugsInId()
  {
    return this.drugsInId;
  }
  
  public void setDrugsInId(String drugsInId)
  {
    this.drugsInId = drugsInId;
  }
  
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
