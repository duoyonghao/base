package com.kqds.entity.base;

public class KqdsMedicalrecord {
  private String seqId;
  
  private String createuser;
  
  private String createtime;
  
  private String usercode;
  
  private String regno;
  
  private Integer mtype;
  
  private String blfl;
  
  private String bc;
  
  private Integer status;
  
  private Integer isprint;
  
  private Integer usertype;
  
  private String organization;
  
  private String doctor;
  
  private String nurse;
  
  private String assistant;
  
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
  
  public Integer getMtype() {
    return this.mtype;
  }
  
  public void setMtype(Integer mtype) {
    this.mtype = mtype;
  }
  
  public String getBlfl() {
    return this.blfl;
  }
  
  public void setBlfl(String blfl) {
    this.blfl = (blfl == null) ? null : blfl.trim();
  }
  
  public String getBc() {
    return this.bc;
  }
  
  public void setBc(String bc) {
    this.bc = (bc == null) ? null : bc.trim();
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Integer getIsprint() {
    return this.isprint;
  }
  
  public void setIsprint(Integer isprint) {
    this.isprint = isprint;
  }
  
  public Integer getUsertype() {
    return this.usertype;
  }
  
  public void setUsertype(Integer usertype) {
    this.usertype = usertype;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = (organization == null) ? null : organization.trim();
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
  
  public String getAssistant() {
    return this.assistant;
  }
  
  public void setAssistant(String assistant) {
    this.assistant = (assistant == null) ? null : assistant.trim();
  }
}
