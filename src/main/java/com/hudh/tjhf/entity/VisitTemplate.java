package com.hudh.tjhf.entity;

import java.io.Serializable;

public class VisitTemplate implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String seqId;
  
  private String planName;
  
  private String hfdept;
  
  private String explanation;
  
  private String remark;
  
  private String creartuser;
  
  private String creartTime;
  
  private String organization;
  
  private String status;
  
  private String spare2;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }
  
  public String getPlanName() {
    return this.planName;
  }
  
  public String getHfdept() {
    return this.hfdept;
  }
  
  public void setHfdept(String hfdept) {
    this.hfdept = hfdept;
  }
  
  public void setPlanName(String planName) {
    this.planName = planName;
  }
  
  public String getExplanation() {
    return this.explanation;
  }
  
  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getCreartuser() {
    return this.creartuser;
  }
  
  public void setCreartuser(String creartuser) {
    this.creartuser = creartuser;
  }
  
  public String getCreartTime() {
    return this.creartTime;
  }
  
  public void setCreartTime(String creartTime) {
    this.creartTime = creartTime;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
  }
  
  public String getSpare2() {
    return this.spare2;
  }
  
  public void setSpare2(String spare2) {
    this.spare2 = spare2;
  }
  
  public static long getSerialversionuid() {
    return 1L;
  }
}
