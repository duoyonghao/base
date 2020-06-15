package com.kqds.entity.base;

public class KqdsChufang {
  private String seqId;
  
  private String createuser;
  
  private String createtime;
  
  private String usercode;
  
  private String regno;
  
  private String costno;
  
  private String doctor;
  
  private String nurse;
  
  private String techperson;
  
  private String remark;
  
  private Integer status;
  
  private String organization;
  
  private String recipecode;
  
  public String getRecipecode() {
    return this.recipecode;
  }
  
  public void setRecipecode(String recipecode) {
    this.recipecode = recipecode;
  }
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = (seqId == null) ? null : seqId.trim();
  }
  
  public String getCreateuser() {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser) {
    this.createuser = (createuser == null) ? null : createuser.trim();
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = (createtime == null) ? null : createtime.trim();
  }
  
  public String getUsercode() {
    return this.usercode;
  }
  
  public void setUsercode(String usercode) {
    this.usercode = (usercode == null) ? null : usercode.trim();
  }
  
  public String getRegno() {
    return this.regno;
  }
  
  public void setRegno(String regno) {
    this.regno = (regno == null) ? null : regno.trim();
  }
  
  public String getCostno() {
    return this.costno;
  }
  
  public void setCostno(String costno) {
    this.costno = (costno == null) ? null : costno.trim();
  }
  
  public String getDoctor() {
    return this.doctor;
  }
  
  public void setDoctor(String doctor) {
    this.doctor = (doctor == null) ? null : doctor.trim();
  }
  
  public String getNurse() {
    return this.nurse;
  }
  
  public void setNurse(String nurse) {
    this.nurse = (nurse == null) ? null : nurse.trim();
  }
  
  public String getTechperson() {
    return this.techperson;
  }
  
  public void setTechperson(String techperson) {
    this.techperson = (techperson == null) ? null : techperson.trim();
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = (remark == null) ? null : remark.trim();
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
  }
}
