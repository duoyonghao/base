package com.kqds.entity.base;

public class KqdsPaiban {
  private String seqId;
  
  private String personid;
  
  private String startdate;
  
  private String enddate;
  
  private String orderstatus;
  
  private String remark;
  
  private String createtime;
  
  private String createuser;
  
  private String deptid;
  
  private String organization;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getPersonid() {
    return this.personid;
  }
  
  public void setPersonid(String personid) {
    this.personid = (personid == null) ? null : personid.trim();
  }
  
  public String getStartdate() {
    return this.startdate;
  }
  
  public void setStartdate(String startdate) {
    this.startdate = (startdate == null) ? null : startdate.trim();
  }
  
  public String getEnddate() {
    return this.enddate;
  }
  
  public void setEnddate(String enddate) {
    this.enddate = (enddate == null) ? null : enddate.trim();
  }
  
  public String getOrderstatus() {
    return this.orderstatus;
  }
  
  public void setOrderstatus(String orderstatus) {
    this.orderstatus = (orderstatus == null) ? null : orderstatus.trim();
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = (remark == null) ? null : remark.trim();
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = (createtime == null) ? null : createtime.trim();
  }
  
  public String getCreateuser() {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser) {
    this.createuser = (createuser == null) ? null : createuser.trim();
  }
  
  public String getDeptid() {
    return this.deptid;
  }
  
  public void setDeptid(String deptid) {
    this.deptid = (deptid == null) ? null : deptid.trim();
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
}
