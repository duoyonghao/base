package com.hudh.tjhf.entity;

import java.io.Serializable;

public class VisitPlanTemplate
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String SEQ_ID;
  private String managarId;
  private String plandays;
  private String visittype;
  private String ismandatory;
  private String isautomatic;
  private String visitstaff;
  private String hfdept;
  private String accepttype;
  private String organization;
  private String remark;
  private String createuser;
  private String createtime;
  private String status;
  private String spare1;
  private String spare2;
  
  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (this.SEQ_ID == null ? 0 : this.SEQ_ID.hashCode());
    result = 31 * result + (this.accepttype == null ? 0 : this.accepttype.hashCode());
    result = 31 * result + (this.createtime == null ? 0 : this.createtime.hashCode());
    result = 31 * result + (this.createuser == null ? 0 : this.createuser.hashCode());
    result = 31 * result + (this.isautomatic == null ? 0 : this.isautomatic.hashCode());
    result = 31 * result + (this.ismandatory == null ? 0 : this.ismandatory.hashCode());
    result = 31 * result + (this.organization == null ? 0 : this.organization.hashCode());
    result = 31 * result + (this.plandays == null ? 0 : this.plandays.hashCode());
    result = 31 * result + (this.remark == null ? 0 : this.remark.hashCode());
    result = 31 * result + (this.spare1 == null ? 0 : this.spare1.hashCode());
    result = 31 * result + (this.spare2 == null ? 0 : this.spare2.hashCode());
    result = 31 * result + (this.visitstaff == null ? 0 : this.visitstaff.hashCode());
    result = 31 * result + (this.visittype == null ? 0 : this.visittype.hashCode());
    return result;
  }
  
  public String getSEQ_ID()
  {
    return this.SEQ_ID;
  }
  
  public void setSEQ_ID(String sEQ_ID)
  {
    this.SEQ_ID = sEQ_ID;
  }
  
  public String getManagarId()
  {
    return this.managarId;
  }
  
  public void setManagarId(String managarId)
  {
    this.managarId = managarId;
  }
  
  public String getPlandays()
  {
    return this.plandays;
  }
  
  public void setPlandays(String plandays)
  {
    this.plandays = plandays;
  }
  
  public String getVisittype()
  {
    return this.visittype;
  }
  
  public void setVisittype(String visittype)
  {
    this.visittype = visittype;
  }
  
  public String getIsmandatory()
  {
    return this.ismandatory;
  }
  
  public void setIsmandatory(String ismandatory)
  {
    this.ismandatory = ismandatory;
  }
  
  public String getIsautomatic()
  {
    return this.isautomatic;
  }
  
  public void setIsautomatic(String isautomatic)
  {
    this.isautomatic = isautomatic;
  }
  
  public String getHfdept()
  {
    return this.hfdept;
  }
  
  public void setHfdept(String hfdept)
  {
    this.hfdept = hfdept;
  }
  
  public String getVisitstaff()
  {
    return this.visitstaff;
  }
  
  public void setVisitstaff(String visitstaff)
  {
    this.visitstaff = visitstaff;
  }
  
  public String getAcceptsype()
  {
    return this.accepttype;
  }
  
  public void setAccepttype(String accepttype)
  {
    this.accepttype = accepttype;
  }
  
  public String getOrganization()
  {
    return this.organization;
  }
  
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getCreateuser()
  {
    return this.createuser;
  }
  
  public void setCreatetuser(String createuser)
  {
    this.createuser = createuser;
  }
  
  public String getCreatetime()
  {
    return this.createtime;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public void setCreatetime(String createtime)
  {
    this.createtime = createtime;
  }
  
  public String getSpare1()
  {
    return this.spare1;
  }
  
  public void setSpare1(String spare1)
  {
    this.spare1 = spare1;
  }
  
  public String getSpare2()
  {
    return this.spare2;
  }
  
  public void setSpare2(String spare2)
  {
    this.spare2 = spare2;
  }
  
  public String toString()
  {
    return 
    


      "VisitPlanTemplate [SEQ_ID=" + this.SEQ_ID + ", managarId=" + this.managarId + ", plandays=" + this.plandays + ", visittype=" + this.visittype + ", ismandatory=" + this.ismandatory + ", isautomatic=" + this.isautomatic + ", visitstaff=" + this.visitstaff + ", accepttype=" + this.accepttype + ", organization=" + this.organization + ", remark=" + this.remark + ", createuser=" + this.createuser + ", createtime=" + this.createtime + ", spare1=" + this.spare1 + ", spare2=" + this.spare2 + "]";
  }
}
