package com.kqds.entity.base;

import java.io.Serializable;

public class KqdsLabel implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String seqId;
  
  private String leveLabel;
  
  private String createTime;
  
  private String createUser;
  
  private String updateTime;
  
  private String parentId;
  
  private String remark;
  
  private String parentName;
  
  private String status;
  
  private String reservedt;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }
  
  public String getLeveLabel() {
    return this.leveLabel;
  }
  
  public void setLeveLabel(String leveLabel) {
    this.leveLabel = leveLabel;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public String getCreateUser() {
    return this.createUser;
  }
  
  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getUpdateTime() {
    return this.updateTime;
  }
  
  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }
  
  public String getParentId() {
    return this.parentId;
  }
  
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getParentName() {
    return this.parentName;
  }
  
  public void setParentName(String parentName) {
    this.parentName = parentName;
  }
  
  public String getReservedt() {
    return this.reservedt;
  }
  
  public void setReservedt(String reservedt) {
    this.reservedt = reservedt;
  }
  
  public String toString() {
    return "KqdsLabel [seqId=" + this.seqId + ", leveLabel=" + this.leveLabel + ", createTime=" + this.createTime + ", createUser=" + 
      this.createUser + ", updateTime=" + this.updateTime + ", parentId=" + this.parentId + ", remark=" + this.remark + 
      ", parentName=" + this.parentName + ", status=" + this.status + ", reservedt=" + this.reservedt + "]";
  }
}
