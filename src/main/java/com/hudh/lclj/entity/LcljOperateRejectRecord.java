package com.hudh.lclj.entity;

import java.io.Serializable;

public class LcljOperateRejectRecord implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String SEQ_ID;
  
  private String createuser;
  
  private String createtime;
  
  private String nodeName;
  
  private String nodeId;
  
  private String organization;
  
  private String orderNumber;
  
  public String getSEQ_ID() {
    return this.SEQ_ID;
  }
  
  public String getOrderNumber() {
    return this.orderNumber;
  }
  
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }
  
  public void setSEQ_ID(String sEQ_ID) {
    this.SEQ_ID = sEQ_ID;
  }
  
  public String getCreateuser() {
    return this.createuser;
  }
  
  public void setCreateuser(String createuser) {
    this.createuser = createuser;
  }
  
  public String getCreatetime() {
    return this.createtime;
  }
  
  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }
  
  public String getNodeName() {
    return this.nodeName;
  }
  
  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }
  
  public String getNodeId() {
    return this.nodeId;
  }
  
  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }
  
  public String getOrganization() {
    return this.organization;
  }
  
  public void setOrganization(String organization) {
    this.organization = organization;
  }
}
