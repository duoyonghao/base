package com.kqds.entity.wx;

public class WXOrder
{
  private String seqId;
  private String openid;
  private String orderdate;
  private String ordertime;
  private String organization;
  private String createtime;
  private Integer orderstatus;
  private String auditor;
  private String auditortime;
  private String auditorremark;
  private String cancelreason;
  private String canceltime;
  private String askitem;
  private String askcontent;
  private String phonenumber;
  private String confirmtime;
  
  public String getSeqId()
  {
    return this.seqId;
  }
  
  public void setSeqId(String seqId)
  {
    this.seqId = (seqId == null ? null : seqId.trim());
  }
  
  public String getOpenid()
  {
    return this.openid;
  }
  
  public void setOpenid(String openid)
  {
    this.openid = (openid == null ? null : openid.trim());
  }
  
  public String getOrderdate()
  {
    return this.orderdate;
  }
  
  public void setOrderdate(String orderdate)
  {
    this.orderdate = (orderdate == null ? null : orderdate.trim());
  }
  
  public String getOrdertime()
  {
    return this.ordertime;
  }
  
  public void setOrdertime(String ordertime)
  {
    this.ordertime = (ordertime == null ? null : ordertime.trim());
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = (createtime == null ? null : createtime.trim());
  }
  
  public Integer getOrderstatus()
  {
    return this.orderstatus;
  }
  
  public void setOrderstatus(Integer orderstatus)
  {
    this.orderstatus = orderstatus;
  }
  
  public String getAuditor()
  {
    return this.auditor;
  }
  
  public void setAuditor(String auditor)
  {
    this.auditor = (auditor == null ? null : auditor.trim());
  }
  
  public String getAuditortime()
  {
    return this.auditortime;
  }
  
  public void setAuditortime(String auditortime)
  {
    this.auditortime = (auditortime == null ? null : auditortime.trim());
  }
  
  public String getAuditorremark()
  {
    return this.auditorremark;
  }
  
  public void setAuditorremark(String auditorremark)
  {
    this.auditorremark = (auditorremark == null ? null : auditorremark.trim());
  }
  
  public String getCancelreason()
  {
    return this.cancelreason;
  }
  
  public void setCancelreason(String cancelreason)
  {
    this.cancelreason = (cancelreason == null ? null : cancelreason.trim());
  }
  
  public String getCanceltime()
  {
    return this.canceltime;
  }
  
  public void setCanceltime(String canceltime)
  {
    this.canceltime = (canceltime == null ? null : canceltime.trim());
  }
  
  public String getAskitem()
  {
    return this.askitem;
  }
  
  public void setAskitem(String askitem)
  {
    this.askitem = (askitem == null ? null : askitem.trim());
  }
  
  public String getAskcontent()
  {
    return this.askcontent;
  }
  
  public void setAskcontent(String askcontent)
  {
    this.askcontent = (askcontent == null ? null : askcontent.trim());
  }
  
  public String getPhonenumber()
  {
    return this.phonenumber;
  }
  
  public void setPhonenumber(String phonenumber)
  {
    this.phonenumber = (phonenumber == null ? null : phonenumber.trim());
  }
  
  public String getConfirmtime()
  {
    return this.confirmtime;
  }
  
  public void setConfirmtime(String confirmtime)
  {
    this.confirmtime = (confirmtime == null ? null : confirmtime.trim());
  }
}
