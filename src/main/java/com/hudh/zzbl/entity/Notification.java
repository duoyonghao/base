package com.hudh.zzbl.entity;

public class Notification {
  String SEQ_ID;
  
  String LcljId;
  
  String LcljNum;
  
  String usercode;
  
  String username;
  
  String content;
  
  String createtime;
  
  String doctortime;
  
  String patienttime;
  
  String whether;
  
  String createuser;
  
  String organization;
  
  public String getCreateuser() {
    return this.createuser;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
  }
  
  public void setCreateuser(String createuser) {
    this.createuser = createuser;
  }
  
  public String getWhether() {
    return this.whether;
  }
  
  public void setWhether(String whether) {
    this.whether = whether;
  }
  
  public String getDoctortime() {
    return this.doctortime;
  }
  
  public void setDoctortime(String doctortime) {
    this.doctortime = doctortime;
  }
  
  public String getPatienttime() {
    return this.patienttime;
  }
  
  public void setPatienttime(String patienttime) {
    this.patienttime = patienttime;
  }
  
  public String getSEQ_ID() {
    return this.SEQ_ID;
  }
  
  public void setSEQ_ID(String sEQ_ID) {
    this.SEQ_ID = sEQ_ID;
  }
  
  public String getLcljId() {
    return this.LcljId;
  }
  
  public void setLcljId(String lcljId) {
    this.LcljId = lcljId;
  }
  
  public String getLcljNum() {
    return this.LcljNum;
  }
  
  public void setLcljNum(String lcljNum) {
    this.LcljNum = lcljNum;
  }
  
  public String getUsercode() {
    return this.usercode;
  }
  
  public void setUsercode(String usercode) {
    this.usercode = usercode;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
}
