package com.kqds.entity.base;

import java.math.BigDecimal;

public class KqdsRefund
{
  private String seqId;
  private String createuser;
  private String createtime;
  private String usercode;
  private String costno;
  private BigDecimal totalcost;
  private BigDecimal voidmoney;
  private BigDecimal shouldmoney;
  private BigDecimal arrearmoney;
  private BigDecimal totalarrmoney;
  private BigDecimal actualmoney;
  private BigDecimal discountmoney;
  private String doctor;
  private String nurse;
  private String techperson;
  private Integer status;
  private String remark;
  private String shtime;
  private String shuser;
  private String tkuser;
  private String tktime;
  private String username;
  private BigDecimal tkze;
  private String organization;
  
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
  
  public String getUsercode()
  {
    return this.usercode;
  }
  
  public void setUsercode(String usercode)
  {
    this.usercode = (usercode == null ? null : usercode.trim());
  }
  
  public String getCostno()
  {
    return this.costno;
  }
  
  public void setCostno(String costno)
  {
    this.costno = (costno == null ? null : costno.trim());
  }
  
  public BigDecimal getTotalcost()
  {
    return this.totalcost;
  }
  
  public void setTotalcost(BigDecimal totalcost)
  {
    this.totalcost = totalcost;
  }
  
  public BigDecimal getVoidmoney()
  {
    return this.voidmoney;
  }
  
  public void setVoidmoney(BigDecimal voidmoney)
  {
    this.voidmoney = voidmoney;
  }
  
  public BigDecimal getShouldmoney()
  {
    return this.shouldmoney;
  }
  
  public void setShouldmoney(BigDecimal shouldmoney)
  {
    this.shouldmoney = shouldmoney;
  }
  
  public BigDecimal getArrearmoney()
  {
    return this.arrearmoney;
  }
  
  public void setArrearmoney(BigDecimal arrearmoney)
  {
    this.arrearmoney = arrearmoney;
  }
  
  public BigDecimal getTotalarrmoney()
  {
    return this.totalarrmoney;
  }
  
  public void setTotalarrmoney(BigDecimal totalarrmoney)
  {
    this.totalarrmoney = totalarrmoney;
  }
  
  public BigDecimal getActualmoney()
  {
    return this.actualmoney;
  }
  
  public void setActualmoney(BigDecimal actualmoney)
  {
    this.actualmoney = actualmoney;
  }
  
  public BigDecimal getDiscountmoney()
  {
    return this.discountmoney;
  }
  
  public void setDiscountmoney(BigDecimal discountmoney)
  {
    this.discountmoney = discountmoney;
  }
  
  public String getDoctor()
  {
    return this.doctor;
  }
  
  public void setDoctor(String doctor)
  {
    this.doctor = (doctor == null ? null : doctor.trim());
  }
  
  public String getNurse()
  {
    return this.nurse;
  }
  
  public void setNurse(String nurse)
  {
    this.nurse = (nurse == null ? null : nurse.trim());
  }
  
  public String getTechperson()
  {
    return this.techperson;
  }
  
  public void setTechperson(String techperson)
  {
    this.techperson = (techperson == null ? null : techperson.trim());
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = (remark == null ? null : remark.trim());
  }
  
  public String getShtime()
  {
    return this.shtime;
  }
  
  public void setShtime(String shtime)
  {
    this.shtime = (shtime == null ? null : shtime.trim());
  }
  
  public String getShuser()
  {
    return this.shuser;
  }
  
  public void setShuser(String shuser)
  {
    this.shuser = (shuser == null ? null : shuser.trim());
  }
  
  public String getTkuser()
  {
    return this.tkuser;
  }
  
  public void setTkuser(String tkuser)
  {
    this.tkuser = (tkuser == null ? null : tkuser.trim());
  }
  
  public String getTktime()
  {
    return this.tktime;
  }
  
  public void setTktime(String tktime)
  {
    this.tktime = (tktime == null ? null : tktime.trim());
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = (username == null ? null : username.trim());
  }
  
  public BigDecimal getTkze()
  {
    return this.tkze;
  }
  
  public void setTkze(BigDecimal tkze)
  {
    this.tkze = tkze;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = (organization == null ? null : organization.trim());
  }
}
