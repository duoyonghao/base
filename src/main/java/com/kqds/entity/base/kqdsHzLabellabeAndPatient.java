package com.kqds.entity.base;

import java.io.Serializable;

public class kqdsHzLabellabeAndPatient implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String seqId;
  
  private String labelOneId;
  
  private String labelOneName;
  
  private String labelTwoId;
  
  private String labelTwoName;
  
  private String labelThreeId;
  
  private String labelThreeName;
  
  private String userSeqId;
  
  private String userId;
  
  private String userName;
  
  private String createUser;
  
  private String createTime;
  
  private String remark;
  
  private int status;
  
  private String opinion;
  
  private String reservedth;
  
  private String reservedf;
  
  private String organization;
  
  public String getSeqId() {
    return this.seqId;
  }
  
  public void setSeqId(String seqId) {
    this.seqId = seqId;
  }
  
  public String getLabelOneName() {
    return this.labelOneName;
  }
  
  public void setLabelOneName(String labelOneName) {
    this.labelOneName = labelOneName;
  }
  
  public String getLabelTwoName() {
    return this.labelTwoName;
  }
  
  public void setLabelTwoName(String labelTwoName) {
    this.labelTwoName = labelTwoName;
  }
  
  public String getLabelThreeName() {
    return this.labelThreeName;
  }
  
  public void setLabelThreeName(String labelThreeName) {
    this.labelThreeName = labelThreeName;
  }
  
  public String getUserSeqId() {
    return this.userSeqId;
  }
  
  public void setUserSeqId(String userSeqId) {
    this.userSeqId = userSeqId;
  }
  
  public String getUserId() {
    return this.userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserName() {
    return this.userName;
  }
  
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  public String getCreateUser() {
    return this.createUser;
  }
  
  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
  
  public String getLabelOneId() {
    return this.labelOneId;
  }
  
  public void setLabelOneId(String labelOneId) {
    this.labelOneId = labelOneId;
  }
  
  public String getLabelTwoId() {
    return this.labelTwoId;
  }
  
  public void setLabelTwoId(String labelTwoId) {
    this.labelTwoId = labelTwoId;
  }
  
  public String getLabelThreeId() {
    return this.labelThreeId;
  }
  
  public void setLabelThreeId(String labelThreeId) {
    this.labelThreeId = labelThreeId;
  }
  
  public String getOpinion() {
    return this.opinion;
  }
  
  public void setOpinion(String opinion) {
    this.opinion = opinion;
  }
  
  public String getReservedth() {
    return this.reservedth;
  }
  
  public void setReservedth(String reservedth) {
    this.reservedth = reservedth;
  }
  
  public String getReservedf() {
    return this.reservedf;
  }
  
  public void setReservedf(String reservedf) {
    this.reservedf = reservedf;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
  }
  
  public String toString() {
    return "kqdsHzLabellabeAndPatient [seqId=" + this.seqId + ", labelOneId=" + this.labelOneId + ", labelOneName=" + 
      this.labelOneName + ", labelTwoId=" + this.labelTwoId + ", labelTwoName=" + this.labelTwoName + ", labelThreeId=" + 
      this.labelThreeId + ", labelThreeName=" + this.labelThreeName + ", userSeqId=" + this.userSeqId + ", userId=" + 
      this.userId + ", userName=" + this.userName + ", createUser=" + this.createUser + ", createTime=" + this.createTime + 
      ", remark=" + this.remark + ", status=" + this.status + ", opinion=" + this.opinion + ", reservedth=" + this.reservedth + 
      ", reservedf=" + this.reservedf + ", organization=" + this.organization + "]";
  }
}
